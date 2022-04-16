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

package caramel.logging.api.async;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import caramel.logging.api.CaramelLogger;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author Kweny
 * @since 0.0.1
 */
public class AsyncSystem {

    private static final CaramelLogger logger = CaramelLogger.getLogger(AsyncSystem.class);

    private static final AsyncSystem INSTANCE = new AsyncSystem();

    public static AsyncSystem instance() {
        return INSTANCE;
    }

    static {
        AsyncSystem.instance().initialize();
    }

    private volatile ActorSystem actorSystem;
    private volatile ActorRef actor;

    private AsyncSystem() {}

    private void initialize() {
        Thread initThread = new Thread(() -> {
            initializeSystem();
            initializeActor();
        });
        initThread.setName("caramel-async-logging-init");
        initThread.start();
    }

    private void initializeSystem() {
        String configContent = resolveAkkaConfig();
        logger.info("[Caramel Logging] Initialize the AsyncSystem with the following config:\n{}", configContent);
        Config config = ConfigFactory.parseString(configContent);
        this.actorSystem = ActorSystem.create("CaramelAsyncLoggingSystem", config);
    }

    private void initializeActor() {
        this.actor = this.actorSystem.actorOf(AsyncActor.props(), "CaramelAsyncLoggingActor");
    }

    private String resolveAkkaConfig() {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(AsyncSystem.class.getResourceAsStream("/async-system.conf"))))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
        } catch (Exception e) {
            logger.error(e, "AsyncSystem config file failed to load.");
        }

        int parallelismMin = parseIntValue(System.getProperty("caramel.logging.async.parallelism-min"), 8);
        int parallelismMax = parseIntValue(System.getProperty("caramel.logging.async.parallelism-max"), 64);
        double parallelismFactor = parseDoubleValue(System.getProperty("caramel.logging.async.parallelism-factor"), 1.0);

        return builder.toString()
                .replace("${parallelism-min}", String.valueOf(parallelismMin))
                .replace("${parallelism-max}", String.valueOf(parallelismMax))
                .replace("${parallelism-factor}", String.valueOf(parallelismFactor));
    }

    public ActorRef actor() {
        while (this.actor == null) {
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (Exception ignored) {}
        }
        return this.actor;
    }

    static int parseIntValue(String value, int defaultValue) {
        return (int) parseDoubleValue(value, defaultValue);
    }

    static double parseDoubleValue(String value, double defaultValue) {
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            return defaultValue;
        }
    }

}
