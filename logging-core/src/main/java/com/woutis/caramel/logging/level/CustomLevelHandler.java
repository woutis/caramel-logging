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

package com.woutis.caramel.logging.level;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.Marker;

/**
 * Custom Levels Logging Handler
 *
 * @author Kweny
 * @since 0.0.1
 */
public interface CustomLevelHandler {

    boolean isEnabled(Logger logger, CustomLevel level, Marker marker);

    void log(Logger logger, CustomLevel level, Marker marker, Throwable thrown, String message, Object... arguments);

    Class<? extends ILoggerFactory> factoryClass();

}
