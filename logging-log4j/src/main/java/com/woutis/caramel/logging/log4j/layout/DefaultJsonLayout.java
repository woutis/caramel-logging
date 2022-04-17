/*
 * Copyright 2018-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.woutis.caramel.logging.log4j.layout;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.woutis.caramel.logging.AsyncLogger;
import com.woutis.caramel.logging.CaramelLogger;
import com.woutis.caramel.logging.LoggingContext;
import com.woutis.caramel.logging.SyncLogger;
import com.woutis.caramel.logging.async.AsyncActor;
import com.woutis.caramel.logging.level.CustomLevelHandler;
import com.woutis.caramel.logging.util.Utility;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.AbstractStringLayout;

import java.lang.reflect.Modifier;
import java.nio.charset.Charset;
import java.util.*;

/**
 * json layout for Log4j
 *
 * @author Kweny
 * @since 0.0.1
 */
@Plugin(name = "DefaultJsonLayout", category = Node.CATEGORY, elementType = Layout.ELEMENT_TYPE, printObject = true)
public class DefaultJsonLayout extends AbstractStringLayout {

    @PluginFactory
    public static DefaultJsonLayout createLayout(@PluginAttribute(value = "charset", defaultString = "UTF-8") final Charset charset,
                                                 @PluginAttribute(value = "withContext", defaultBoolean = true) final boolean withContext,
                                                 @PluginAttribute(value = "withSource") final boolean withSource,
                                                 @PluginAttribute(value = "withThrown") final boolean withThrown,
                                                 @PluginAttribute(value = "withThread") final boolean withThread,
                                                 @PluginAttribute(value = "pretty") final boolean pretty) {
        return new DefaultJsonLayout(charset, withSource, withThrown, withThread, withContext, pretty);
    }

    private static final Gson GSON = new GsonBuilder()
            .excludeFieldsWithModifiers(Modifier.STATIC, Modifier.TRANSIENT)
            .registerTypeAdapter(Class.class, (JsonSerializer<Class<?>>) (src, typeOfSrc, context) -> new JsonPrimitive(src.getName()))
            .create();

    private static final Gson GSON_PRETTY = new GsonBuilder()
            .excludeFieldsWithModifiers(Modifier.STATIC, Modifier.TRANSIENT)
            .registerTypeAdapter(Class.class, (JsonSerializer<Class<?>>) (src, typeOfSrc, context) -> new JsonPrimitive(src.getName()))
            .setPrettyPrinting()
            .create();

    private static final String EOL = System.lineSeparator();

    private final boolean withSource;
    private final boolean withThrown;
    private final boolean withContext;
    private final boolean withThread;
    private final Gson gson;

    protected DefaultJsonLayout(Charset charset, boolean withSource, boolean withThrown, boolean withThread, boolean withContext, boolean pretty) {
        super(charset);
        this.withSource = withSource;
        this.withThrown = withThrown;
        this.withThread = withThread;
        this.withContext = withContext;
        this.gson = pretty ? GSON_PRETTY : GSON;
    }

    @Override
    public String toSerializable(LogEvent event) {
        Map<String, Object> content = buildContent(event);

        String line = null;
        Map<String, String> errorLines = new HashMap<>();

        try {
            line = gson.toJson(content);
        } catch (Exception ex) {
            errorLines.put("gson", String.format("Error formatting log using Gson: %s - %s", ex.getClass().getName(), ex.getMessage()));
        }

        if (line == null) {
            StringBuilder lineBuilder = new StringBuilder();
            lineBuilder.append("{");
            if (Utility.isNotEmpty(content)) {
                content.forEach((key, value) -> lineBuilder.append("\"").append(key).append("\":\"").append(String.valueOf(value).replace("\"", "\\\"")).append("\","));
                lineBuilder.deleteCharAt(lineBuilder.length() - 1);
            }
            if (Utility.isNotEmpty(errorLines)) {
                lineBuilder.append("\",errors\":{");
                errorLines.forEach((key, value) -> lineBuilder.append("\"").append(key).append("\":\"").append(value.replace("\"", "\\\"")).append("\","));
                lineBuilder.deleteCharAt(lineBuilder.length() - 1).append("}");
            }
            lineBuilder.append("}");
            line = lineBuilder.toString();
        }

        return line + EOL;
    }

    private Map<String, Object> buildContent(LogEvent event) {
        Map<String, Object> content = new HashMap<>();

        content.put("loggerName", event.getLoggerName());
        content.put("loggerFqcn", event.getLoggerFqcn());
        content.put("timestamp", event.getTimeMillis());
        content.put("level", event.getLevel().name());

        if (event.getMarker() != null) {
            content.put("marker", event.getMarker().getName());
        }
        if (event.getMessage() != null) {
            content.put("message", event.getMessage().getFormattedMessage());
        }

        if (this.withContext) {
            Map<String, ?> localPayload = LoggingContext.all();
            if(Utility.isNotEmpty(localPayload)) {
                content.putAll(localPayload);
            }
        }

        if (this.withThread) {
            Thread thread = LoggingContext._sourceThread();
            if (thread != null) {
                content.put("threadId", thread.getId());
                content.put("threadName", thread.getName());
                content.put("threadPriority", thread.getPriority());
                content.put("threadGroup", thread.getThreadGroup().getName());
            } else {
                content.put("threadId", event.getThreadId());
                content.put("threadName", event.getThreadName());
                content.put("threadPriority", event.getThreadPriority());
                content.put("threadGroup", Thread.currentThread().getThreadGroup().getName());
            }
        }

        if (this.withSource) {
            content.put("source", resolveSource(event));
        }

        if (this.withThrown) {
            content.put("thrown", resolveThrown(event.getThrown()));
        }

        return content;
    }

    private StackTraceElement resolveSource(LogEvent event) {
        StackTraceElement source = event.getSource();
        if (source != null && isWrapper(source.getClassName())) {
            StackTraceElement[] stackTrace = LoggingContext._sourceStackTrace() != null ? LoggingContext._sourceStackTrace() : new Throwable().getStackTrace();
            StackTraceElement last = null;
            for (int i = stackTrace.length - 1; i > 0; i--) {
                final String className = stackTrace[i].getClassName();
                if (isWrapper(className)) {
                    return last;
                }
                last = stackTrace[i];
            }
        }
        return source;
    }

    private static Map<String, Object> resolveThrown(Throwable t) {
        if (t == null) {
            return null;
        }

        List<String> names = new ArrayList<>();
        List<String> messages = new ArrayList<>();
        List<StackTraceElement> stackTrace = new ArrayList<>();
        recurseThrownCause(names, messages, stackTrace, t);

        Map<String, Object> thrown = new HashMap<>();
        thrown.put("names", names);
        thrown.put("messages", messages);
        thrown.put("stackTrace", stackTrace);

        return thrown;
    }

    private static final Class<?>[] WRAPPER_CLASSES = {
            SyncLogger.class,
            AsyncLogger.class,
            CaramelLogger.class,
            AsyncActor.class,
            CustomLevelHandler.class
    };

    private boolean isWrapper(String classname) {
        if (Utility.isBlank(classname)) {
            return false;
        }
        Class<?> clazz = null;
        try {
            clazz = Class.forName(classname);
        } catch (Exception ignored) {}
        for (Class<?> wrapperClass : WRAPPER_CLASSES) {
            if (clazz != null) {
                if (wrapperClass.isAssignableFrom(clazz)) {
                    return true;
                }
            } else {
                if (Utility.equals(wrapperClass.getName(), classname)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void recurseThrownCause(List<String> names, List<String> messages, List<StackTraceElement> trace, Throwable t) {
        names.add(t.getClass().getName());
        messages.add(t.getMessage());
        StackTraceElement[] elements = t.getStackTrace();
        if (Utility.isNotEmpty(elements)) {
            trace.clear();
            trace.addAll(Arrays.asList(elements));
        }
        if (t.getCause() != null) {
            recurseThrownCause(names, messages, trace, t.getCause());
        }
    }

}
