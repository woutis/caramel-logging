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

package caramel.logging.level;

/**
 * Levels used for identifying the severity of an event.
 *
 * @author Kweny
 * @since 0.0.1
 */
public enum CaramelLevel {
    /** 没有的 */
    OFF,

    /** 末日的 */
    DOOM,

    /** 灾难的 */
    FATAL,

    /** 严峻的 */
    SEVERE,

    /** 错误的 */
    ERROR,

    /** 风险的 */
    RISK,

    /** 警告的 */
    WARN,

    /** 提示的 */
    NOTICE,

    /** 信息的 */
    INFO,

    /** 诊断的 */
    DIAG,

    /** 调试的 */
    DEBUG,

    /** 详细的 */
    DETAIL,

    /** 追溯的 */
    TRACE,

    /** 冗长的 */
    VERBOSE,

    /** 所有的 */
    ALL,
}
