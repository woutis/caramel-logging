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

package caramel.logging.logback.level;

import caramel.logging.level.CaramelLevel;
import ch.qos.logback.classic.Level;

import java.lang.reflect.Constructor;

/**
 * Defines the set of levels recognized by Logback according to {@link CaramelLevel}.
 *
 * @author Kweny
 * @since 0.0.1
 */
public class LogbackLevel {

    public static final Level OFF; // Integer.MAX_VALUE

    public static final Level DOOM; // 70000

    public static final Level FATAL; // 60000

    public static final Level SEVERE; // 50000

    public static final Level ERROR; // 40000

    public static final Level RISK; // 35000

    public static final Level WARN; // 30000

    public static final Level NOTICE; // 25000

    public static final Level INFO; // 20000

    public static final Level DIAG; // 15000

    public static final Level DEBUG; // 10000

    public static final Level DETAIL; // 7500

    public static final Level TRACE; // 5000

    public static final Level VERBOSE; // 2500

    public static final Level ALL; // Integer.MIN_VALUE

    static {
        try {
            Constructor<?> constructor = Level.class.getDeclaredConstructor(int.class, String.class);
            constructor.setAccessible(true);

            OFF     = Level.OFF;
            DOOM    = (Level) constructor.newInstance(70000, "DOOM");
            FATAL   = (Level) constructor.newInstance(60000, "FATAL");
            SEVERE  = (Level) constructor.newInstance(50000, "SEVERE");
            ERROR   = Level.ERROR;
            RISK    = (Level) constructor.newInstance(35000, "RISK");
            WARN    = Level.WARN;
            NOTICE  = (Level) constructor.newInstance(25000, "NOTICE");
            INFO    = Level.INFO;
            DIAG    = (Level) constructor.newInstance(15000, "DIAG");
            DEBUG   = Level.DEBUG;
            DETAIL  = (Level) constructor.newInstance(7500, "DETAIL");
            TRACE   = Level.TRACE;
            VERBOSE = (Level) constructor.newInstance(2500, "VERBOSE");
            ALL     = Level.ALL;

            constructor.setAccessible(false);
        } catch (Exception ex) {
            throw new IllegalStateException("Error initializing LogbackLevel.", ex);
        }
    }

    public static Level of(CaramelLevel caramelLevel) {
        if (caramelLevel != null) {
            switch (caramelLevel) {
                case OFF:
                    return LogbackLevel.OFF;
                case DOOM:
                    return LogbackLevel.DOOM;
                case FATAL:
                    return LogbackLevel.FATAL;
                case SEVERE:
                    return LogbackLevel.SEVERE;
                case ERROR:
                    return LogbackLevel.ERROR;
                case RISK:
                    return LogbackLevel.RISK;
                case WARN:
                    return LogbackLevel.WARN;
                case NOTICE:
                    return LogbackLevel.NOTICE;
                case INFO:
                    return LogbackLevel.INFO;
                case DIAG:
                    return LogbackLevel.DIAG;
                case DEBUG:
                    return LogbackLevel.DEBUG;
                case DETAIL:
                    return LogbackLevel.DETAIL;
                case TRACE:
                    return LogbackLevel.TRACE;
                case VERBOSE:
                    return LogbackLevel.VERBOSE;
                case ALL:
                    return LogbackLevel.ALL;
            }
        }
        return LogbackLevel.INFO;
    }

}
