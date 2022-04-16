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

package eagle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Kweny
 * @since 0.0.1
 */
@SpringBootApplication
public class EagleApplication {

    public static void main(String[] args) {
//        Logger logger = LoggerFactory.getLogger(EagleApplication.class);
//        System.out.println(logger.getClass());
//        System.out.println(LoggerFactory.getILoggerFactory());
//        System.out.println(LoggerFactory.getILoggerFactory().getClass().getName());
//        logger.info("testtt");

//        CaramelLogger cLogger = CaramelLogger.getLogger(EagleApplication.class);
//        cLogger.doom();

        SpringApplication.run(EagleApplication.class, args);
    }

}
