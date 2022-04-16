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

import akka.actor.ActorRef;
import caramel.logging.async.AsyncDelivery;
import caramel.logging.async.AsyncSystem;
import caramel.logging.level.CustomLevel;
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

    private void doDeliver(AsyncDelivery delivery) {
        delivery = delivery
                .context(LoggingContext.replica())
                .sourceStackTrace(new Throwable().getStackTrace())
                .sourceThread(Thread.currentThread());
        AsyncSystem.instance().actor().tell(delivery, ActorRef.noSender());
    }

    private Marker resolveMarker(Marker marker) {
        return marker != null ? marker : this.defaultMarker;
    }

    // ----- log ----- beginning
    @Override
    public void log(CustomLevel level, Marker marker, Throwable thrown, String message, Object... arguments) {
        doDeliver(
                AsyncDelivery.create(this.logger, this.customLevelHandler, level)
                        .marker(resolveMarker(marker))
                        .thrown(thrown)
                        .message(message).arguments(arguments)
        );
    }

    @Override
    public void log(CustomLevel level, Marker marker, String message, Object... arguments) {
        doDeliver(
                AsyncDelivery.create(this.logger, this.customLevelHandler, level)
                        .marker(resolveMarker(marker))
                        .message(message).arguments(arguments)
        );
    }

    @Override
    public void log(CustomLevel level, Throwable thrown, String message, Object... arguments) {
        doDeliver(
                AsyncDelivery.create(this.logger, this.customLevelHandler, level)
                        .marker(this.defaultMarker)
                        .thrown(thrown)
                        .message(message).arguments(arguments)
        );
    }

    @Override
    public void log(CustomLevel level, String message, Object... arguments) {
        doDeliver(
                AsyncDelivery.create(this.logger, this.customLevelHandler, level)
                        .marker(this.defaultMarker)
                        .message(message).arguments(arguments)
        );
    }
    // ----- log ----- ending

    // ----- doom ----- beginning
    // ----- doom ----- ending

    // ----- fatal ----- beginning
    // ----- fatal ----- ending

    // ----- severe ----- beginning
    // ----- severe ----- ending

    // ----- error ----- beginning
    public void error(Marker marker, Throwable thrown, String pattern, Object... arguments) {
        doDeliver(
                AsyncDelivery.error(this.logger)
                        .marker(resolveMarker(marker))
                        .thrown(thrown)
                        .message(pattern).arguments(arguments)
        );
    }

    public void error(Marker marker, String pattern, Object... arguments) {
        doDeliver(
                AsyncDelivery.error(this.logger)
                        .marker(resolveMarker(marker))
                        .message(pattern).arguments(arguments)
        );
    }

    public void error(Throwable thrown, String pattern, Object... arguments) {
        doDeliver(
                AsyncDelivery.error(this.logger)
                        .marker(this.defaultMarker)
                        .thrown(thrown)
                        .message(pattern).arguments(arguments)
        );
    }

    public void error(String pattern, Object... arguments) {
        doDeliver(
                AsyncDelivery.error(this.logger)
                        .marker(this.defaultMarker)
                        .message(pattern).arguments(arguments)
        );
    }
    // ----- error ----- ending

    // ----- risk ----- beginning
    // ----- risk ----- ending

    // ----- warn ----- beginning
    public void warn(Marker marker, Throwable thrown, String pattern, Object... arguments) {
        doDeliver(
                AsyncDelivery.warn(this.logger)
                        .marker(resolveMarker(marker))
                        .thrown(thrown)
                        .message(pattern).arguments(arguments)
        );
    }

    public void warn(Marker marker, String pattern, Object... arguments) {
        doDeliver(
                AsyncDelivery.warn(this.logger)
                        .marker(resolveMarker(marker))
                        .message(pattern).arguments(arguments)
        );
    }

    public void warn(Throwable thrown, String pattern, Object... arguments) {
        doDeliver(
                AsyncDelivery.warn(this.logger)
                        .marker(this.defaultMarker)
                        .thrown(thrown)
                        .message(pattern).arguments(arguments)
        );
    }

    public void warn(String pattern, Object... arguments) {
        doDeliver(
                AsyncDelivery.warn(this.logger)
                        .marker(this.defaultMarker)
                        .message(pattern).arguments(arguments)
        );
    }
    // ----- warn ----- ending

    // ----- prompt ----- beginning
    // ----- prompt ----- ending

    // ----- info ----- beginning
    public void info(Marker marker, Throwable thrown, String pattern, Object... arguments) {
        doDeliver(
                AsyncDelivery.info(this.logger)
                        .marker(resolveMarker(marker))
                        .thrown(thrown)
                        .message(pattern).arguments(arguments)
        );
    }

    public void info(Marker marker, String pattern, Object... arguments) {
        doDeliver(
                AsyncDelivery.info(this.logger)
                        .marker(resolveMarker(marker))
                        .message(pattern).arguments(arguments)
        );
    }

    public void info(Throwable thrown, String pattern, Object... arguments) {
        doDeliver(
                AsyncDelivery.info(this.logger)
                        .marker(this.defaultMarker)
                        .thrown(thrown)
                        .message(pattern).arguments(arguments)
        );
    }

    public void info(String pattern, Object... arguments) {
        doDeliver(
                AsyncDelivery.info(this.logger)
                        .marker(this.defaultMarker)
                        .message(pattern).arguments(arguments)
        );
    }
    // ----- info ----- ending

    // ----- diag ----- beginning
    // ----- diag ----- ending

    // ----- debug ----- beginning
    public void debug(Marker marker, Throwable thrown, String pattern, Object... arguments) {
        doDeliver(
                AsyncDelivery.debug(this.logger)
                        .marker(resolveMarker(marker))
                        .thrown(thrown)
                        .message(pattern).arguments(arguments)
        );
    }

    public void debug(Marker marker, String pattern, Object... arguments) {
        doDeliver(
                AsyncDelivery.debug(this.logger)
                        .marker(resolveMarker(marker))
                        .message(pattern).arguments(arguments)
        );
    }

    public void debug(Throwable thrown, String pattern, Object... arguments) {
        doDeliver(
                AsyncDelivery.debug(this.logger)
                        .marker(this.defaultMarker)
                        .thrown(thrown)
                        .message(pattern).arguments(arguments)
        );
    }

    public void debug(String pattern, Object... arguments) {
        doDeliver(
                AsyncDelivery.debug(this.logger)
                        .marker(this.defaultMarker)
                        .message(pattern).arguments(arguments)
        );
    }
    // ----- debug ----- ending

    // ----- detail ----- beginning
    // ----- detail ----- ending

    // ----- trace ----- beginning
    public void trace(Marker marker, Throwable thrown, String pattern, Object... arguments) {
        doDeliver(
                AsyncDelivery.trace(this.logger)
                        .marker(resolveMarker(marker))
                        .thrown(thrown)
                        .message(pattern).arguments(arguments)
        );
    }

    public void trace(Marker marker, String pattern, Object... arguments) {
        doDeliver(
                AsyncDelivery.trace(this.logger)
                        .marker(resolveMarker(marker))
                        .message(pattern).arguments(arguments)
        );
    }

    public void trace(Throwable thrown, String pattern, Object... arguments) {
        doDeliver(
                AsyncDelivery.trace(this.logger)
                        .marker(this.defaultMarker)
                        .thrown(thrown)
                        .message(pattern).arguments(arguments)
        );
    }

    public void trace(String pattern, Object... arguments) {
        doDeliver(
                AsyncDelivery.trace(this.logger)
                        .marker(this.defaultMarker)
                        .message(pattern).arguments(arguments)
        );
    }
    // ----- trace ----- ending

    // ----- verbose ----- beginning
    // ----- verbose ----- ending

}
