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

package caramel.logging.async;

import caramel.logging.level.CustomLevel;
import caramel.logging.level.CustomLevelHandler;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.event.Level;

import java.util.Map;

/**
 * @author Kweny
 * @since 0.0.1
 */
public class AsyncDelivery {

    public static AsyncDelivery create() {
        return new AsyncDelivery();
    }

    public static AsyncDelivery create(Logger logger, Level level) {
        return new AsyncDelivery().logger(logger).level(level);
    }

    public static AsyncDelivery trace(Logger logger) {
        return create(logger, Level.TRACE);
    }

    public static AsyncDelivery debug(Logger logger) {
        return create(logger, Level.DEBUG);
    }

    public static AsyncDelivery info(Logger logger) {
        return create(logger, Level.INFO);
    }

    public static AsyncDelivery warn(Logger logger) {
        return create(logger, Level.WARN);
    }

    public static AsyncDelivery error(Logger logger) {
        return create(logger, Level.ERROR);
    }

    public static AsyncDelivery create(Logger logger, CustomLevelHandler customLevelHandler, CustomLevel customLevel) {
        return new AsyncDelivery().logger(logger).customLevelHandler(customLevelHandler).customLevel(customLevel);
    }

    private Logger logger;
    private Level level;
    private CustomLevelHandler customLevelHandler;
    private CustomLevel customLevel;
    private Marker marker;
    private Throwable thrown;
    private String message;
    private Object[] arguments;
    private Map<String, ?> context;
    private StackTraceElement[] sourceStackTrace;
    private Thread sourceThread;

    // ----- setter ----- beginning
    public AsyncDelivery logger(Logger logger) {
        this.logger = logger;
        return this;
    }

    public AsyncDelivery level(Level level) {
        this.level = level;
        return this;
    }

    public AsyncDelivery customLevelHandler(CustomLevelHandler customLevelHandler) {
        this.customLevelHandler = customLevelHandler;
        return this;
    }

    public AsyncDelivery customLevel(CustomLevel customLevel) {
        this.customLevel = customLevel;
        return this;
    }

    public AsyncDelivery marker(Marker marker) {
        this.marker = marker;
        return this;
    }

    public AsyncDelivery thrown(Throwable thrown) {
        this.thrown = thrown;
        return this;
    }

    public AsyncDelivery message(String message) {
        this.message = message;
        return this;
    }

    public AsyncDelivery arguments(Object... arguments) {
        this.arguments = arguments;
        return this;
    }

    public AsyncDelivery context(Map<String, ?> content) {
        this.context = content;
        return this;
    }

    public AsyncDelivery sourceStackTrace(StackTraceElement[] sourceStackTrace) {
        this.sourceStackTrace = sourceStackTrace;
        return this;
    }

    public AsyncDelivery sourceThread(Thread sourceThread) {
        this.sourceThread = sourceThread;
        return this;
    }
    // ----- setter ----- ending

    // ----- getter ----- beginning
    public Logger logger() {
        return logger;
    }

    public Level level() {
        return level;
    }

    public CustomLevelHandler customLevelHandler() {
        return this.customLevelHandler;
    }

    public CustomLevel customLevel() {
        return this.customLevel;
    }

    public Marker marker() {
        return marker;
    }

    public Throwable thrown() {
        return thrown;
    }

    public String message() {
        return message;
    }

    public Object[] arguments() {
        return arguments;
    }

    public Map<String, ?> context() {
        return context;
    }

    public StackTraceElement[] sourceStackTrace() {
        return sourceStackTrace;
    }

    public Thread sourceThread() {
        return this.sourceThread;
    }
    // ----- getter ----- ending
}
