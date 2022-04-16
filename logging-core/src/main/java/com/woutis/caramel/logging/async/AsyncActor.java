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

package com.woutis.caramel.logging.async;

import akka.actor.AbstractActor;
import akka.actor.Props;
import com.woutis.caramel.logging.LoggingContext;
import com.woutis.caramel.logging.level.CustomLevelHandler;
import com.woutis.caramel.logging.message.CaramelMessageFactory;
import org.slf4j.Logger;

/**
 * @author Kweny
 * @since 0.0.1
 */
public class AsyncActor extends AbstractActor {

    public static Props props() {
        return Props.create(AsyncActor.class);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(AsyncDelivery.class, this::receipt)
                .matchAny(this::unhandled)
                .build();
    }

    private void receipt(AsyncDelivery delivery) {
        LoggingContext._sourceStackTrace(delivery.sourceStackTrace());
        LoggingContext._sourceThread(delivery.sourceThread());
        LoggingContext.set(delivery.context());
        if (delivery.customLevel() != null) {
            log(delivery);
        } else {
            switch (delivery.level()) {
                case TRACE:
                    trace(delivery);
                    break;
                case DEBUG:
                    debug(delivery);
                    break;
                case INFO:
                    info(delivery);
                    break;
                case WARN:
                    warn(delivery);
                    break;
                case ERROR:
                    error(delivery);
                    break;
                default:
                    break;
            }
        }
        LoggingContext.release();
    }

    private void log(AsyncDelivery delivery) {
        CustomLevelHandler handler = delivery.customLevelHandler();
        handler.log(delivery.logger(), delivery.customLevel(), delivery.marker(), delivery.thrown(), delivery.message(), delivery.arguments());
    }

    private void error(AsyncDelivery delivery) {
        Logger logger = delivery.logger();
        if (delivery.marker() == null) {
            if (logger.isErrorEnabled()) {
                if (delivery.thrown() == null) {
                    logger.error(delivery.message(), delivery.arguments());
                } else {
                    String msg = CaramelMessageFactory.format(delivery.message(), delivery.arguments());
                    logger.error(msg, delivery.thrown());
                }
            }
        } else {
            if (logger.isErrorEnabled(delivery.marker())) {
                if (delivery.thrown() == null) {
                    logger.error(delivery.marker(), delivery.message(), delivery.arguments());
                } else {
                    String msg = CaramelMessageFactory.format(delivery.message(), delivery.arguments());
                    logger.error(delivery.marker(), msg, delivery.thrown());
                }
            }
        }
    }

    private void warn(AsyncDelivery delivery) {
        Logger logger = delivery.logger();
        if (delivery.marker() == null) {
            if (logger.isWarnEnabled()) {
                if (delivery.thrown() == null) {
                    logger.warn(delivery.message(), delivery.arguments());
                } else {
                    String msg = CaramelMessageFactory.format(delivery.message(), delivery.arguments());
                    logger.warn(msg, delivery.thrown());
                }
            }
        } else {
            if (logger.isWarnEnabled(delivery.marker())) {
                if (delivery.thrown() == null) {
                    logger.warn(delivery.marker(), delivery.message(), delivery.arguments());
                } else {
                    String msg = CaramelMessageFactory.format(delivery.message(), delivery.arguments());
                    logger.warn(delivery.marker(), msg, delivery.thrown());
                }
            }
        }
    }

    private void info(AsyncDelivery delivery) {
        Logger logger = delivery.logger();
        if (delivery.marker() == null) {
            if (logger.isInfoEnabled()) {
                if (delivery.thrown() == null) {
                    logger.info(delivery.message(), delivery.arguments());
                } else {
                    String msg = CaramelMessageFactory.format(delivery.message(), delivery.arguments());
                    logger.info(msg, delivery.thrown());
                }
            }
        } else {
            if (logger.isInfoEnabled(delivery.marker())) {
                if (delivery.thrown() == null) {
                    logger.info(delivery.marker(), delivery.message(), delivery.arguments());
                } else {
                    String msg = CaramelMessageFactory.format(delivery.message(), delivery.arguments());
                    logger.info(delivery.marker(), msg, delivery.thrown());
                }
            }
        }
    }

    private void debug(AsyncDelivery delivery) {
        Logger logger = delivery.logger();
        if (delivery.marker() == null) {
            if (logger.isDebugEnabled()) {
                if (delivery.thrown() == null) {
                    logger.debug(delivery.message(), delivery.arguments());
                } else {
                    String msg = CaramelMessageFactory.format(delivery.message(), delivery.arguments());
                    logger.debug(msg, delivery.thrown());
                }
            }
        } else {
            if (logger.isDebugEnabled(delivery.marker())) {
                if (delivery.thrown() == null) {
                    logger.debug(delivery.marker(), delivery.message(), delivery.arguments());
                } else {
                    String msg = CaramelMessageFactory.format(delivery.message(), delivery.arguments());
                    logger.debug(delivery.marker(), msg, delivery.thrown());
                }
            }
        }
    }

    private void trace(AsyncDelivery delivery) {
        Logger logger = delivery.logger();
        if (delivery.marker() == null) {
            if (logger.isTraceEnabled()) {
                if (delivery.thrown() == null) {
                    logger.trace(delivery.message(), delivery.arguments());
                } else {
                    String msg = CaramelMessageFactory.format(delivery.message(), delivery.arguments());
                    logger.trace(msg, delivery.thrown());
                }
            }
        } else {
            if (logger.isTraceEnabled(delivery.marker())) {
                if (delivery.thrown() == null) {
                    logger.trace(delivery.marker(), delivery.message(), delivery.arguments());
                } else {
                    String msg = CaramelMessageFactory.format(delivery.message(), delivery.arguments());
                    logger.trace(delivery.marker(), msg, delivery.thrown());
                }
            }
        }
    }
}
