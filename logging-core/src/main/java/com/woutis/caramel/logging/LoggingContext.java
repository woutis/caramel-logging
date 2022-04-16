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

package com.woutis.caramel.logging;

import java.util.*;
import java.util.stream.Collectors;

/**
 * logging context
 *
 * @author Kweny
 * @since 0.0.1
 */
public class LoggingContext {

    private static final ThreadLocal<LoggingContext> local = ThreadLocal.withInitial(LoggingContext::new);

    // ----- static ----- beginning
    private static LoggingContext payload() {
        return local.get();
    }

    public static void release() {
        clear();
        _sourceStackTrace(null);
        _sourceThread(null);
        local.remove();
    }

    public static boolean emptily() {
        return payload().isEmpty();
    }

    public static void set(String key, Object value) {
        payload().setEntry(key, value);
    }

    public static void set(Map<String, ?> data) {
        payload().setEntries(data);
    }

    public static Object get(String key) {
        return payload().getEntry(key);
    }

    public static List<?> get(Collection<String> keys) {
        return payload().getEntries(keys);
    }

    public static Map<String, ?> all() {
        return payload().allEntries();
    }

    public static Object remove(String key) {
        return payload().removeEntry(key);
    }

    public static List<?> remove(Collection<String> keys) {
        return payload().removeEntries(keys);
    }

    public static void clear() {
        payload().clearAll();
    }

    /** 异步日志时复制上下文数据到日志子线程，避免外部清理上下文时导致日志输出丢失 */
    public static Map<String, ?> replica() {
        return payload().replicaPayload();
    }

    /** 仅用于异步日志内部传递原始日志源堆栈，禁止外部调用 */
    public static void _sourceStackTrace(StackTraceElement[] source) {
        payload().setSourceStackTrace(source);
    }

    /** 仅用于异步日志内部传递原始日志源堆栈，禁止外部调用 */
    public static StackTraceElement[] _sourceStackTrace() {
        return payload().getSourceStackTrace();
    }

    /** 仅用于异步日志内部传递源线程信息，禁止外部调用 */
    public static void _sourceThread(Thread thread) {
        payload().setSourceThread(thread);
    }

    /** 仅用于异步日志内部传递源线程信息，禁止外部调用 */
    public static Thread _sourceThread() {
        return payload().getSourceThread();
    }
    // ----- static ----- ending

    private final Map<String, Object> payload;
    private StackTraceElement[] sourceStackTrace;
    private Thread sourceThread;

    private LoggingContext() {
        this.payload = new HashMap<>();
    }

    public boolean isEmpty() {
        return this.payload.isEmpty();
    }

    private void setEntry(String key, Object value) {
        this.payload.put(key, value);
    }

    private void setEntries(Map<String, ?> data) {
        if (data != null && data.size() > 0) {
            this.payload.putAll(data);
        }
    }

    private Object getEntry(String key) {
        return this.payload.get(key);
    }

    private List<?> getEntries(Collection<String> keys) {
        if (keys == null || keys.size() == 0) {
            return new ArrayList<>();
        }
        return keys.stream().distinct().map(this::getEntry).collect(Collectors.toList());
    }

    private Map<String, ?> allEntries() {
        return Collections.unmodifiableMap(this.payload);
    }

    private Object removeEntry(String key) {
        return this.payload.remove(key);
    }

    private List<?> removeEntries(Collection<String> keys) {
        if (keys == null || keys.size() == 0) {
            return new ArrayList<>();
        }
        return keys.stream().distinct().map(this::removeEntry).collect(Collectors.toList());
    }

    private void clearAll() {
        this.payload.clear();
    }

    private Map<String, ?> replicaPayload() {
        return new HashMap<>(this.payload);
    }

    private void setSourceStackTrace(StackTraceElement[] sourceStackTrace) {
        this.sourceStackTrace = sourceStackTrace;
    }

    private StackTraceElement[] getSourceStackTrace() {
        return this.sourceStackTrace;
    }

    private void setSourceThread(Thread sourceThread) {
        this.sourceThread = sourceThread;
    }

    private Thread getSourceThread() {
        return this.sourceThread;
    }
}
