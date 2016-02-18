/*
 * The MIT License
 *
 * Copyright 2016 Björn Raupach <raupach@me.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package me.raupach.kvp;

import static java.lang.Character.*;

/**
 * KEY
 * <ul>
 * <li>the allowed list of characters is any Alphanumeric plus &quot;_&quot;
 * &quot;.&quot; &quot;$&quot; &quot;&#64;&quot;</li>
 * <li>must have at least one non-numeric character from the allowed list of
 * characters</li>
 * <li>a &quot; or ' either side of a key will result in anything between the
 * quotations marks being interpreted as the KEY, e.g. &quot;key[1]&quot; will
 * give a KEY of key[1]</li>
 * <ul>
 *
 * @author Björn Raupach <raupach@me.com>
 */
public class KVP {

    public static String of(String key, String value, String... args) {
        // Log messages are in general longer than 16 characters
        StringBuilder sb = new StringBuilder(64);

        sb.append(key).append('=').append(value);

        return sb.toString();
    }

    static void escape(StringBuilder sb, final int offset, final int length) {
        if (length == 0) {
            return;
        } else if (length == 1) {
            char c = sb.charAt(0);
            if (!(isLetter(c)
                    || isDigit(c)
                    || isUnderscore(c)
                    || isDot(c)
                    || isDollar(c)
                    || isAtSign(c))) {
                sb.insert(offset, '\"');
                sb.insert(length + 1, '\"');
            }
        } else {
            char c = sb.charAt(0);
            char d = sb.charAt(length - 1);

            if (isDQuot(c) && isDQuot(d) || isQuot(c) && isQuot(d)) {
                return;
            }

            for (int i = offset; i < length; i++) {
                c = sb.charAt(i);
                if (isBackslash(c) && length > 1) {
                    i++;
                } else if (!(isLetter(c)
                        || isDigit(c)
                        || isUnderscore(c)
                        || isDot(c)
                        || isDollar(c)
                        || isAtSign(c))) {
                    sb.insert(offset, '\"');
                    sb.insert(length + 1, '\"');
                    return;
                }
            }
        }
    }

    static boolean isQuot(char c) {
        return c == '\'';
    }

    static boolean isDQuot(char c) {
        return c == '"';
    }

    static boolean isBackslash(char c) {
        return c == '\\';
    }

    static boolean isUnderscore(char c) {
        return c == '_';
    }

    static boolean isDot(char c) {
        return c == '.';
    }

    static boolean isDollar(char c) {
        return c == '$';
    }

    static boolean isAtSign(char c) {
        return c == '@';
    }

}
