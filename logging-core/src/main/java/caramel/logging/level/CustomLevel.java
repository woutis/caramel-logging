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

import caramel.logging.CaramelLogger;

/**
 * Custom level interface
 *
 * <p>Log4j native levels and some custom examples,
 * used for identifying the severity of an event.
 * Levels are organized from most specific to least:
 * <table>
 *     <tr><th>        </th> <th>Name   </th> <th>Value  </th> <th>Description</th></tr>
 *     <tr><th>Native  </th> <th>OFF    </th> <th>0      </th> <th>No events will be logged.</th></tr>
 *     <tr><th>Custom  </th> <th>DOOM   </th> <th>50     </th> <th>Custom example for reference: Doomsday.</th></tr>
 *     <tr><th>Native  </th> <th>FATAL  </th> <th>100    </th> <th>A fatal event that will prevent the application from continuing.</th></tr>
 *     <tr><th>Custom  </th> <th>SEVERE </th> <th>150    </th> <th>Custom example for reference: Serious error.</th></tr>
 *     <tr><th>Native  </th> <th>ERROR  </th> <th>200    </th> <th>An error in the application, possibly recoverable.</th></tr>
 *     <tr><th>Custom  </th> <th>RISK   </th> <th>250    </th> <th>Custom example for reference: Risky.</th></tr>
 *     <tr><th>Native  </th> <th>WARN   </th> <th>300    </th> <th>An event that might possible lead to an error.</th></tr>
 *     <tr><th>Custom  </th> <th>NOTICE </th> <th>350    </th> <th>Custom example for reference: Notice.</th></tr>
 *     <tr><th>Native  </th> <th>INFO   </th> <th>400    </th> <th>An event for informational purposes.</th></tr>
 *     <tr><th>Custom  </th> <th>DIAG   </th> <th>450    </th> <th>Custom example for reference: Diagnose.</th></tr>
 *     <tr><th>Native  </th> <th>DEBUG  </th> <th>500    </th> <th>A general debugging event.</th></tr>
 *     <tr><th>Custom  </th> <th>DETAIL </th> <th>550    </th> <th>Custom example for reference: Details.</th></tr>
 *     <tr><th>Native  </th> <th>TRACE  </th> <th>600    </th> <th>A fine-grained debug message, typically capturing the flow through the application.</th></tr>
 *     <tr><th>Custom  </th> <th>VERBOSE</th> <th>650    </th> <th>Custom example for reference: Verbose.</th></tr>
 *     <tr><th>Native  </th> <th>ALL    </th> <th>MaxInt</th> <th>All events should be logged.</th></tr>
 * </table>
 *
 * <p>Usage:
 * <ol>
 *     <li>
 *         Implement the {@link CustomLevel} interface to customize some levels like RISK, NOTICE, DIAG, etc. such as:
 *         <pre>
 *             public enum MyLevel implement CustomLevel {
 *                 RISK(250),
 *                 NOTICE(350),
 *                 DIAG(450),;
 *
 *                 private final int value;
 *
 *                 TestCustomLevel(int value) {
 *                     this.value = value;
 *                 }
 *
 *                 &#64;Override
 *                 public int value() {
 *                     return this.value;
 *                 }
 *             }
 *         </pre>
 *     </li>
 *     <li>
 *         Add the above custom levels to the Log4j2 configuration file, such as:
 *         <pre>
 *             &lt;?xml version="1.0" encoding="UTF-8"?&gt;
 *             &lt;Configuration status="WARN"&gt;
 *                 &lt;Properties&gt;
 *                     ...
 *                 &lt;/Properties&gt;
 *
 *                 &lt!-- Define custom levels before using them for filtering below. --&gt;
 *                 &lt;CustomLevels&gt;
 *                     &lt;CustomLevel name="RISK" intLevel="250" /&gt;
 *                     &lt;CustomLevel name="NOTICE" intLevel="350" /&gt;
 *                     &lt;CustomLevel name="DIAG" intLevel="450" /&gt;
 *                 &lt;/CustomLevels&gt;
 *
 *                 &lt;Appenders&gt;
 *                     ...
 *                 &lt;/Appenders&gt;
 *
 *                 &lt;Loggers&gt;
 *                     ...
 *                 &lt;/Loggers&gt;
 *             &lt;/Configuration&gt;
 *         </pre>
 *     </li>
 *     <li>
 *         Printing custom level logs using the {@link CaramelLogger} API, such as:
 *         <pre>
 *             CaramelLogger logger = CaramelLogger.getLogger("");
 *             logger.log(MyLevel.DIAG, "some messages");
 *         </pre>
 *     </li>
 * </ol>
 *
 * @author Kweny
 * @since 0.0.1
 */
public interface CustomLevel {

    String name();

    int value();

}
