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

package caramel.logging.api;

import caramel.logging.api.level.CustomLevel;
import caramel.logging.api.level.CustomLevelHandler;
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
    protected CustomLevelHandler levelHandler;

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
                this.levelHandler = handler;
                break;
            }
        }
    }

    public String getName() {
        return this.logger.getName();
    }

    public boolean isEnabled(CustomLevel level, Marker marker) {
        return this.levelHandler.isEnabled(this.logger, level, marker);
    }

    public boolean isEnabled(CustomLevel level) {
        return this.levelHandler.isEnabled(this.logger, level, this.defaultMarker);
    }

    public abstract void log(CustomLevel level, Marker marker, Throwable thrown, String message, Object... arguments);

    public abstract void log(CustomLevel level, Marker marker, String message, Object... arguments);

    public abstract void log(CustomLevel level, Throwable thrown, String message, Object... arguments);

    public abstract void log(CustomLevel level, String message, Object... arguments);

    public abstract void log(CustomLevel level, String message);

    // ----- doom ----- beginning
    // TODO-Kweny doom  SPI -> find real logger by name
//    public abstract void doom();
    // ----- doom ----- ending

    // ----- fatal ----- beginning
    // TODO-Kweny fatal  SPI -> find real logger by name
    // ----- fatal ----- ending

    // ----- severe ----- beginning
    // TODO-Kweny severe  SPI -> find real logger by name
    // ----- severe ----- ending

    // ----- error ----- beginning
    // ----- error ----- ending

    // ----- risk ----- beginning
    // TODO-Kweny risk  SPI -> find real logger by name
    // ----- risk ----- ending

    // ----- warn ----- beginning
    // ----- warn ----- ending

    // ----- prompt ----- beginning
    // TODO-Kweny prompt  SPI -> find real logger by name
    // ----- prompt ----- ending

    // ----- info ----- beginning
    // ----- info ----- ending

    // ----- diag ----- beginning
    // TODO-Kweny diag  SPI -> find real logger by name
    // ----- diag ----- ending

    // ----- debug ----- beginning
    // ----- debug ----- ending

    // ----- detail ----- beginning
    // TODO-Kweny detail  SPI -> find real logger by name
    // ----- detail ----- ending

    // ----- trace ----- beginning
    // ----- trace ----- ending

    // ----- verbose ----- beginning
    // TODO-Kweny verbose  SPI -> find real logger by name
    // ----- verbose ----- ending

}
