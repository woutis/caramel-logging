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

package caramel.logging.api.message;

/**
 * @author Kweny
 * @since 0.0.1
 */
public class CaramelMessageFactory {

    private static final String INDENT = "    "; // 缩进，4个空格

    private final String pattern;
    private final String[] arguments;
    private transient Object[] objectArguments;
    private transient int indentLevel;
    private transient String formattedMessage;

    public CaramelMessageFactory(final String pattern, final Object... args) {
        this.pattern = pattern;
        this.objectArguments = args;
        this.arguments = _InternalParameterizeTextFormatAssist.objectsToStrings(args);
    }

    public CaramelMessageFactory(final int indentLevel, final String pattern, final Object... args) {
        this.indentLevel = indentLevel;
        this.pattern = pattern;
        this.objectArguments = args;
        this.arguments = _InternalParameterizeTextFormatAssist.objectsToStrings(args);
    }

    public CaramelMessageFactory(final String pattern, final String... args) {
        this.pattern = pattern;
        this.arguments = args;
    }

    public CaramelMessageFactory(final int indentLevel, final String pattern, final String... args) {
        this.indentLevel = indentLevel;
        this.pattern = pattern;
        this.arguments = args;
    }

    public String getPattern() {
        return pattern;
    }

    public Object[] getArguments() {
        return objectArguments != null ? objectArguments : arguments;
    }

    public int getIndentLevel() {
        return indentLevel;
    }

    public String getFormattedMessage() {
        if (formattedMessage == null) {
            formattedMessage = _InternalParameterizeTextFormatAssist.formatMessage(pattern, arguments);
            if (indentLevel > 0) {
                StringBuilder result = new StringBuilder();
                for (int i = 0; i < indentLevel; i++) {
                    result.append(INDENT);
                }
                formattedMessage = result.append(formattedMessage).toString();
            }
        }
        return formattedMessage;
    }

    public static String format(final String pattern, final Object... args) {
        String[] arguments = _InternalParameterizeTextFormatAssist.objectsToStrings(args);
        return _InternalParameterizeTextFormatAssist.formatStringArgs(pattern, arguments);
    }

    public static String format(final int indentLevel, final String pattern, final Object... args) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < indentLevel; i++) {
            result.append(INDENT);
        }
        return result.append(format(pattern, args)).toString();
    }

}
