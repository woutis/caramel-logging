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

package com.woutis.caramel.logging.logback.level;

import com.woutis.caramel.logging.level.CustomLevel;
import com.woutis.caramel.logging.level.CustomLevelHandler;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.Marker;

/**
 * @author Kweny
 * @since 0.0.1
 */
public class LogbackLevelHandler implements CustomLevelHandler {

    @Override
    public boolean isEnabled(Logger logger, CustomLevel level, Marker marker) {
        return false;
    }

    @Override
    public void log(Logger logger, CustomLevel level, Marker marker, Throwable thrown, String message, Object... arguments) {

    }

    @Override
    public Class<? extends ILoggerFactory> factoryClass() {
        return LoggerContext.class;
    }

}
