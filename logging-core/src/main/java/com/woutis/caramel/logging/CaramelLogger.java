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

import com.woutis.caramel.logging.level.CustomLevel;
import com.woutis.caramel.logging.level.CustomLevelHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

/**
 * abstract logger wrapper
 *
 * @author Kweny
 * @since 0.0.1
 */
public abstract class CaramelLogger {

    // ----- static utilities ----- beginning
    private static final ConcurrentHashMap<String, SyncLogger> SYNC = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, AsyncLogger> ASYNC = new ConcurrentHashMap<>();

    /**
     * 返回一个指定名称的 同步 logger。
     *
     * @param name logger 名称
     * @return logger
     * @throws NullPointerException 若 {@code name} 为 {@code null}
     */
    public static SyncLogger getLogger(final String name) {
        return SYNC.computeIfAbsent(cacheKey(name, null), key -> new SyncLogger(name));
    }

    /**
     * 返回一个以指定 class 命名的 同步 logger。
     *
     * @param clazz logger 将以该 class 命名
     * @return logger
     * @throws NullPointerException 若 {@code clazz} 为 {@code null}
     */
    public static SyncLogger getLogger(final Class<?> clazz) {
        return SYNC.computeIfAbsent(cacheKey(clazz, null), key -> new SyncLogger(clazz));
    }

    /**
     * 返回一个指定名称的 同步 logger，并为该 logger 指定一个默认的 {@link Marker}。
     *
     * @param name logger 名称
     * @param defaultMarker 默认 {@link Marker}
     * @return logger
     */
    public static SyncLogger getLogger(final String name, final Marker defaultMarker) {
        return SYNC.computeIfAbsent(cacheKey(name, defaultMarker), key -> new SyncLogger(name, defaultMarker));
    }

    /**
     * 返回一个以指定 class 命名的 同步 logger，并为该 logger 指定一个默认的 {@link Marker}。
     *
     * @param clazz logger 将以该 class 命名
     * @param defaultMarker 默认 {@link Marker}
     * @return logger
     */
    public static SyncLogger getLogger(final Class<?> clazz, final Marker defaultMarker) {
        return SYNC.computeIfAbsent(cacheKey(clazz, defaultMarker), key -> new SyncLogger(clazz, defaultMarker));
    }

    /**
     * 返回一个指定名称的 异步 logger。
     *
     * @param name logger 名称
     * @return logger
     * @throws NullPointerException 若 {@code name} 为 {@code null}
     */
    public static AsyncLogger getAsyncLogger(final String name) {
        return ASYNC.computeIfAbsent(cacheKey(name, null), key -> new AsyncLogger(name));
    }

    /**
     * 返回一个以指定 class 命名的 异步 logger。
     *
     * @param clazz logger 将以该 class 命名
     * @return logger
     * @throws NullPointerException 若 {@code clazz} 为 {@code null}
     */
    public static AsyncLogger getAsyncLogger(final Class<?> clazz) {
        return ASYNC.computeIfAbsent(cacheKey(clazz, null), key -> new AsyncLogger(clazz));
    }

    /**
     * 返回一个指定名称的 异步 logger，并为该 logger 指定一个默认的 {@link Marker}。
     *
     * @param name logger 名称
     * @param defaultMarker 默认 {@link Marker}
     * @return logger
     */
    public static AsyncLogger getAsyncLogger(final String name, final Marker defaultMarker) {
        return ASYNC.computeIfAbsent(cacheKey(name, defaultMarker), key -> new AsyncLogger(name, defaultMarker));
    }

    /**
     * 返回一个以指定 class 命名的 异步 logger，并为该 logger 指定一个默认的 {@link Marker}。
     *
     * @param clazz logger 将以该 class 命名
     * @param defaultMarker 默认 {@link Marker}
     * @return logger
     */
    public static AsyncLogger getAsyncLogger(final Class<?> clazz, final Marker defaultMarker) {
        return ASYNC.computeIfAbsent(cacheKey(clazz, defaultMarker), key -> new AsyncLogger(clazz, defaultMarker));
    }

    private static String cacheKey(Object main, Marker marker) {
        String key = "";
        if (main == null) {
            key += "null";
        } else if (main instanceof String) {
            key += main;
        } else if (main instanceof Class) {
            key += ((Class<?>) main).getName() + "@" + Integer.toHexString(System.identityHashCode(main));
        } else {
            key += main.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(main));
        }
        if (marker != null) {
            key += "-" + marker.getName();
        }
        return key;
    }
    // ----- static utilities ----- ending

    protected final Logger logger;
    protected final Marker defaultMarker;
    protected CustomLevelHandler customLevelHandler;

    protected CaramelLogger(String name) {
        this(name, null);
    }

    protected CaramelLogger(Class<?> clazz) {
        this(clazz, null);
    }

    protected CaramelLogger(String name, Marker defaultMarker) {
        this.resolveLevelHandler();
        this.logger = LoggerFactory.getLogger(name);
        this.defaultMarker = defaultMarker;
    }

    protected CaramelLogger(Class<?> clazz, Marker defaultMarker) {
        this.resolveLevelHandler();
        this.logger = LoggerFactory.getLogger(clazz);
        this.defaultMarker = defaultMarker;
    }

    protected void resolveLevelHandler() {
        ServiceLoader<CustomLevelHandler> handlers = ServiceLoader.load(CustomLevelHandler.class);
        for (CustomLevelHandler handler : handlers) {
            if (handler.factoryClass() == LoggerFactory.getILoggerFactory().getClass()) {
                this.customLevelHandler = handler;
                break;
            }
        }
    }

    public String getName() {
        return this.logger.getName();
    }

    public boolean isEnabled(CustomLevel level, Marker marker) {
        return this.customLevelHandler.isEnabled(this.logger, level, marker);
    }

    public boolean isEnabled(CustomLevel level) {
        return this.customLevelHandler.isEnabled(this.logger, level, this.defaultMarker);
    }

    // ----- log ----- beginning
    public abstract void log(CustomLevel level, Marker marker, Throwable thrown, String message, Object... arguments);

    public abstract void log(CustomLevel level, Marker marker, String message, Object... arguments);

    public abstract void log(CustomLevel level, Throwable thrown, String message, Object... arguments);

    public abstract void log(CustomLevel level, String message, Object... arguments);
    // ----- log ----- ending

    // ----- doom ----- beginning
    // ----- doom ----- ending

    // ----- fatal ----- beginning
    // ----- fatal ----- ending

    // ----- severe ----- beginning
    // ----- severe ----- ending

    // ----- error ----- beginning
    public boolean isErrorEnabled(Marker marker) {
        return logger.isErrorEnabled(marker);
    }

    public boolean isErrorEnabled() {
        return this.defaultMarker != null ? isErrorEnabled(this.defaultMarker) : logger.isErrorEnabled();
    }

    public abstract void error(Marker marker, Throwable thrown, String message, Object... arguments);

    public abstract void error(Marker marker, String message, Object... arguments);

    public abstract void error(Throwable thrown, String message, Object... arguments);

    public abstract void error(String message, Object... arguments);
    // ----- error ----- ending

    // ----- risk ----- beginning
    // ----- risk ----- ending

    // ----- warn ----- beginning
    public boolean isWarnEnabled(Marker marker) {
        return logger.isWarnEnabled(marker);
    }

    public boolean isWarnEnabled() {
        return this.defaultMarker != null ? isWarnEnabled(this.defaultMarker) : logger.isWarnEnabled();
    }

    public abstract void warn(Marker marker, Throwable thrown, String message, Object... arguments);

    public abstract void warn(Marker marker, String message, Object... arguments);

    public abstract void warn(Throwable thrown, String message, Object... arguments);

    public abstract void warn(String message, Object... arguments);
    // ----- warn ----- ending

    // ----- prompt ----- beginning
    // ----- prompt ----- ending

    // ----- info ----- beginning
    public boolean isInfoEnabled(Marker marker) {
        return logger.isInfoEnabled(marker);
    }

    public boolean isInfoEnabled() {
        return this.defaultMarker != null ? isInfoEnabled(this.defaultMarker) : logger.isInfoEnabled();
    }

    public abstract void info(Marker marker, Throwable thrown, String message, Object... arguments);

    public abstract void info(Marker marker, String message, Object... arguments);

    public abstract void info(Throwable thrown, String message, Object... arguments);

    public abstract void info(String message, Object... arguments);
    // ----- info ----- ending

    // ----- diag ----- beginning
    // ----- diag ----- ending

    // ----- debug ----- beginning
    public boolean isDebugEnabled(Marker marker) {
        return logger.isDebugEnabled(marker);
    }

    public boolean isDebugEnabled() {
        return this.defaultMarker != null ? isDebugEnabled(this.defaultMarker) : logger.isDebugEnabled();
    }

    public abstract void debug(Marker marker, Throwable thrown, String message, Object... arguments);

    public abstract void debug(Marker marker, String message, Object... arguments);

    public abstract void debug(Throwable thrown, String message, Object... arguments);

    public abstract void debug(String message, Object... arguments);
    // ----- debug ----- ending

    // ----- detail ----- beginning
    // ----- detail ----- ending

    // ----- trace ----- beginning
    public boolean isTraceEnabled(Marker marker) {
        return logger.isTraceEnabled(marker);
    }

    public boolean isTraceEnabled() {
        return this.defaultMarker != null ? isTraceEnabled(this.defaultMarker) : logger.isTraceEnabled();
    }

    public abstract void trace(Marker marker, Throwable thrown, String message, Object... arguments);

    public abstract void trace(Marker marker, String message, Object... arguments);

    public abstract void trace(Throwable thrown, String message, Object... arguments);

    public abstract void trace(String message, Object... arguments);
    // ----- trace ----- ending

    // ----- verbose ----- beginning
    // ----- verbose ----- ending

}
