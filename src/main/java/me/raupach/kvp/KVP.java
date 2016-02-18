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

public final class KVP {
    
    private KVP() {}

    public static String of(String key, Object value) {
        return new StringBuilder()
                .append(key)
                .append('=')
                .append(String.valueOf(value))
                .toString();
    }

    public static String of(String key, Object value, String... args) {
        StringBuilder sb = new StringBuilder(64);

        sb.append(key)
                .append('=')
                .append(String.valueOf(value));

        if (args != null) {
            int n = args.length;
            if ((args.length & 1) == 1) {
                n -= 1;
            }
            for (int i = 0; i < n; i += 2) {
                sb.append(',')
                        .append(' ')
                        .append(args[i])
                        .append('=')
                        .append(args[i + 1]);
            }
            if ((args.length & 1) == 1) {
                sb.append(',')
                        .append(' ')
                        .append(args[n])
                        .append('=')
                        .append("null");
            }
        }

        return sb.toString();
    }

    static void escape(StringBuilder sb, final int offset, final int length) {
        switch (length) {
            case 0:
                break;
            case 1: {
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
                break;
            }
            default: {
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
                break;
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
