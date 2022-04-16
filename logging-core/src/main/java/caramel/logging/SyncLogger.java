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

package caramel.logging;

import caramel.logging.level.CustomLevel;
import caramel.logging.message.CaramelMessageFactory;
import org.slf4j.Marker;

/**
 * logger wrapper
 *
 * @author Kweny
 * @since 0.0.1
 */
public class SyncLogger extends CaramelLogger {

    protected SyncLogger(String name) {
        super(name);
    }

    protected SyncLogger(Class<?> clazz) {
        super(clazz);
    }

    protected SyncLogger(String name, Marker defaultMarker) {
        super(name, defaultMarker);
    }

    protected SyncLogger(Class<?> clazz, Marker defaultMarker) {
        super(clazz, defaultMarker);
    }

    // ----- log ----- beginning
    @Override
    public void log(CustomLevel level, Marker marker, Throwable thrown, String message, Object... arguments) {
        if (marker == null) {
            marker = this.defaultMarker;
        }
        this.customLevelHandler.log(this.logger, level, marker, thrown, message, arguments);
    }

    @Override
    public void log(CustomLevel level, Marker marker, String message, Object... arguments) {
        this.log(level, marker, null, message, arguments);
    }

    @Override
    public void log(CustomLevel level, Throwable thrown, String message, Object... arguments) {
        this.log(level, null, thrown, message, arguments);
    }

    @Override
    public void log(CustomLevel level, String message, Object... arguments) {
        this.log(level, null, null, message, arguments);
    }
    // ----- log ----- ending

    // ----- doom ----- beginning
    // ----- doom ----- ending

    // ----- fatal ----- beginning
    // ----- fatal ----- ending

    // ----- severe ----- beginning
    // ----- severe ----- ending

    // ----- error ----- beginning
    public void error(Marker marker, Throwable thrown, String message, Object... arguments) {
        if (isErrorEnabled(marker)) {
            String msg = CaramelMessageFactory.format(message, arguments);
            logger.error(marker, msg, thrown);
        }
    }

    public void error(Marker marker, String message, Object... arguments) {
        logger.error(marker, message, arguments);
    }

    public void error(Throwable thrown, String message, Object... arguments) {
        if (defaultMarker != null) {
            error(defaultMarker, thrown, message, arguments);
        } else {
            if (isErrorEnabled()) {
                String msg = CaramelMessageFactory.format(message, arguments);
                logger.error(msg, thrown);
            }
        }
    }

    public void error(String message, Object... arguments) {
        if (defaultMarker != null) {
            error(defaultMarker, message, arguments);
        } else {
            logger.error(message, arguments);
        }
    }
    // ----- error ----- ending

    // ----- risk ----- beginning
    // ----- risk ----- ending

    // ----- warn ----- beginning
    public void warn(Marker marker, Throwable thrown, String message, Object... arguments) {
        if (isWarnEnabled(marker)) {
            String msg = CaramelMessageFactory.format(message, arguments);
            logger.warn(marker, msg, thrown);
        }
    }

    public void warn(Marker marker, String message, Object... arguments) {
        logger.warn(marker, message, arguments);
    }

    public void warn(Throwable thrown, String message, Object... arguments) {
        if (defaultMarker != null) {
            warn(defaultMarker, thrown, message, arguments);
        } else {
            if (isWarnEnabled()) {
                String msg = CaramelMessageFactory.format(message, arguments);
                logger.warn(msg, thrown);
            }
        }
    }
    public void warn(String message, Object... arguments) {
        if (defaultMarker != null) {
            warn(defaultMarker, message, arguments);
        } else {
            logger.warn(message, arguments);
        }
    }
    // ----- warn ----- ending

    // ----- prompt ----- beginning
    // ----- prompt ----- ending

    // ----- info ----- beginning
    public void info(Marker marker, Throwable thrown, String message, Object... arguments) {
        if (isInfoEnabled(marker)) {
            String msg = CaramelMessageFactory.format(message, arguments);
            logger.info(marker, msg, thrown);
        }
    }

    public void info(Marker marker, String message, Object... arguments) {
        logger.info(marker, message, arguments);
    }

    public void info(Throwable thrown, String message, Object... arguments) {
        if (defaultMarker != null) {
            info(defaultMarker, thrown, message, arguments);
        } else {
            if (isInfoEnabled()) {
                String msg = CaramelMessageFactory.format(message, arguments);
                logger.info(msg, thrown);
            }
        }
    }
    public void info(String message, Object... arguments) {
        if (defaultMarker != null) {
            info(defaultMarker, message, arguments);
        } else {
            logger.info(message, arguments);
        }
    }
    // ----- info ----- ending

    // ----- diag ----- beginning
    // ----- diag ----- ending

    // ----- debug ----- beginning
    public void debug(Marker marker, Throwable thrown, String message, Object... arguments) {
        if (isDebugEnabled(marker)) {
            String msg = CaramelMessageFactory.format(message, arguments);
            logger.debug(marker, msg, thrown);
        }
    }

    public void debug(Marker marker, String message, Object... arguments) {
        logger.debug(marker, message, arguments);
    }

    public void debug(Throwable thrown, String message, Object... arguments) {
        if (defaultMarker != null) {
            debug(defaultMarker, thrown, message, arguments);
        } else {
            if (isDebugEnabled()) {
                String msg = CaramelMessageFactory.format(message, arguments);
                logger.debug( msg, thrown);
            }
        }
    }
    public void debug(String message, Object... arguments) {
        if (defaultMarker != null) {
            debug(defaultMarker, message, arguments);
        } else {
            logger.debug(message, arguments);
        }
    }
    // ----- debug ----- ending

    // ----- detail ----- beginning
    // ----- detail ----- ending

    // ----- trace ----- beginning
    public void trace(Marker marker, Throwable thrown, String message, Object... arguments) {
        if (isTraceEnabled(marker)) {
            String msg = CaramelMessageFactory.format(message, arguments);
            logger.trace(marker, msg, thrown);
        }
    }

    public void trace(Marker marker, String message, Object... arguments) {
        logger.trace(marker, message, arguments);
    }

    public void trace(Throwable thrown, String message, Object... arguments) {
        if (this.defaultMarker != null) {
            trace(defaultMarker, thrown, message, arguments);
        } else {
            if (isTraceEnabled()) {
                String msg = CaramelMessageFactory.format(message, arguments);
                logger.trace(msg, thrown);
            }
        }
    }
    public void trace(String message, Object... arguments) {
        if (defaultMarker != null) {
            trace(defaultMarker, message, arguments);
        } else {
            logger.trace(message, arguments);
        }
    }
    // ----- trace ----- ending

    // ----- verbose ----- beginning
    // ----- verbose ----- ending

}
