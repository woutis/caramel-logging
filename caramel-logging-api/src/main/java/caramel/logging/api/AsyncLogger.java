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
import org.slf4j.Marker;

/**
 * async logger wrapper
 *
 * @author Kweny
 * @since 0.0.1
 */
public class AsyncLogger extends CaramelLogger {

    protected AsyncLogger(String name) {
        super(name);
    }

    protected AsyncLogger(Class<?> clazz) {
        super(clazz);
    }

    protected AsyncLogger(String name, Marker defaultMarker) {
        super(name, defaultMarker);
    }

    protected AsyncLogger(Class<?> clazz, Marker defaultMarker) {
        super(clazz, defaultMarker);
    }

    @Override
    public void log(CustomLevel level, Marker marker, Throwable thrown, String message, Object... arguments) {

    }

    @Override
    public void log(CustomLevel level, Marker marker, String message, Object... arguments) {

    }

    @Override
    public void log(CustomLevel level, Throwable thrown, String message, Object... arguments) {

    }

    @Override
    public void log(CustomLevel level, String message, Object... arguments) {

    }

    @Override
    public void log(CustomLevel level, String message) {

    }

}