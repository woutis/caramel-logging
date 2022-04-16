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

package com.woutis.caramel.logging.log4j.level;

import com.woutis.caramel.logging.level.CustomLevel;
import com.woutis.caramel.logging.level.CustomLevelHandler;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.slf4j.Log4jLoggerFactory;
import org.slf4j.Logger;
import org.slf4j.Marker;

/**
 * @author Kweny
 * @since 0.0.1
 */
public class Log4jLevelHandler implements CustomLevelHandler {

    @Override
    public boolean isEnabled(Logger logger, CustomLevel level, Marker marker) {
        org.apache.logging.log4j.Logger rLogger = LogManager.getLogger(logger.getName());
        Level rLevel = Level.forName(level.name(), level.value());
        if (marker != null) {
            return rLogger.isEnabled(rLevel, MarkerManager.getMarker(marker.getName()));
        } else {
            return rLogger.isEnabled(rLevel);
        }
    }

    @Override
    public void log(Logger logger, CustomLevel level, Marker marker, Throwable thrown, String message, Object... arguments) {
        org.apache.logging.log4j.Logger rLogger = LogManager.getLogger(logger.getName());
        Level rLevel = Level.forName(level.name(), level.value());
        if (rLogger.isEnabled(rLevel)) {
            if (thrown != null) {
                if (marker != null) {
                    rLogger.log(rLevel, MarkerManager.getMarker(marker.getName()), rLogger.getMessageFactory().newMessage(message, arguments), thrown);
                } else {
                    rLogger.log(rLevel, rLogger.getMessageFactory().newMessage(message, arguments), thrown);
                }
            } else {
                if (marker != null) {
                    rLogger.log(rLevel, MarkerManager.getMarker(marker.getName()), message, arguments);
                } else {
                    rLogger.log(rLevel, message, arguments);
                }
            }
        }
    }

    @Override
    public Class<Log4jLoggerFactory> factoryClass() {
        return Log4jLoggerFactory.class;
    }

}
