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

package caramel.logging.log4j.level;

import caramel.logging.api.level.CaramelLevel;
import org.apache.logging.log4j.Level;

/**
 * Defines the set of levels recognized by Log4j according to {@link CaramelLevel}.
 *
 * @author Kweny
 * @since 0.0.1
 */
public class Log4jLevel {

    public static final Level OFF = Level.OFF; // 0

    public static final Level DOOM = Level.forName("DOOM", 50);

    public static final Level FATAL = Level.FATAL; // 100

    public static final Level SEVERE = Level.forName("SEVERE", 150);

    public static final Level ERROR = Level.ERROR; // 200

    public static final Level RISK = Level.forName("RISK", 250);

    public static final Level WARN = Level.WARN; // 300

    public static final Level NOTICE = Level.forName("NOTICE", 350);

    public static final Level INFO = Level.INFO; // 400

    public static final Level DIAG = Level.forName("DIAG", 450);

    public static final Level DEBUG = Level.DEBUG; // 500

    public static final Level DETAIL = Level.forName("DETAIL", 550);

    public static final Level TRACE = Level.TRACE; // 600

    public static final Level VERBOSE = Level.forName("VERBOSE", 650);

    public static final Level ALL = Level.ALL; // Integer.MAX_VALUE

    public static Level of(CaramelLevel caramelLevel) {
        if (caramelLevel != null) {
            switch (caramelLevel) {
                case OFF:
                    return Log4jLevel.OFF;
                case DOOM:
                    return Log4jLevel.DOOM;
                case FATAL:
                    return Log4jLevel.FATAL;
                case SEVERE:
                    return Log4jLevel.SEVERE;
                case ERROR:
                    return Log4jLevel.ERROR;
                case RISK:
                    return Log4jLevel.RISK;
                case WARN:
                    return Log4jLevel.WARN;
                case NOTICE:
                    return Log4jLevel.NOTICE;
                case INFO:
                    return Log4jLevel.INFO;
                case DIAG:
                    return Log4jLevel.DIAG;
                case DEBUG:
                    return Log4jLevel.DEBUG;
                case DETAIL:
                    return Log4jLevel.DETAIL;
                case TRACE:
                    return Log4jLevel.TRACE;
                case VERBOSE:
                    return Log4jLevel.VERBOSE;
                case ALL:
                    return Log4jLevel.ALL;
            }
        }
        return Log4jLevel.INFO;
    }

}
