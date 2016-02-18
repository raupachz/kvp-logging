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

import org.testng.annotations.Test;
import static org.testng.Assert.*;
import org.testng.annotations.DataProvider;

@Test
public class KVPTest {
    
    public void test() {
        String actual = KVP.of("cpu.time", "200ms", 
                               "cpu.load", "0.34", 
                               "disk", 0.3);
        
        String expected = "cpu.time=200ms, cpu.load=0.34, disk=0.3";
        assertEquals(actual, expected);
    }
    
    public void test_odd_varargs() {
        String[] args = { "cpu.load" };
        
        String actual = KVP.of("cpu.time", "200ms", args);
        String expected = "cpu.time=200ms, cpu.load=null";
        assertEquals(actual, expected);
    }
    
    public void test_of_s_n() {
        String actual = KVP.of("cpu.time", "200ms");
        String expected = "cpu.time=200ms";
        assertEquals(actual, expected);
    }
    
    public void test_of_2null() {
        String actual = KVP.of(null, null);
        String expected = "null=null";
        assertEquals(actual, expected);
    }
    
    public void test_of_3null() {
        String actual = KVP.of(null, null, null);
        String expected = "null=null";
        assertEquals(actual, expected);
    }

    @DataProvider(name = "datasetValues")
    public Object[][] createDatasetValues() {
        return new Object[][]{
            {"",  ""},
            {"key", "key"},
            {"0", "0"},
            {"a",  "a"},
            {"_", "_"},
            {".", "."},
            {"$", "$"},
            {"@", "@"},
            {"\\", "\"\\\""},
            {"https://foo.example.com", "\"https://foo.example.com\""},
            {"\"https://foo.example.com\"", "\"https://foo.example.com\""},
            {"100ms",  "100ms"},
            {"100 ms", "\"100 ms\""},
            {"100\\ ms", "100\\ ms"},
            {"Valentine's Day", "\"Valentine's Day\""},
            {"\"", "\"\"\""},
            {"\"\"\"", "\"\"\""},
            {"\"\"\"\"", "\"\"\"\""},
            {"$\"", "\"$\"\""},
            {"\"$", "\"\"$\""},
            {"key: \"value\" ", "\"key: \"value\" \""}, 
            {"key\\:\\ \\\"value\\\"", "key\\:\\ \\\"value\\\""}, 
        };
    }
    
    @Test(dataProvider = "datasetValues", enabled = true)
    public void test_escape(String plain, String expected) {
        int offset = 0;
        int length = plain.length();
        
        StringBuilder sb = new StringBuilder(plain);
        KVP.escape(sb, offset, length);
        String actual = sb.toString();
        
        System.out.println(String.format("%1$25s -> %2$s", plain, expected));
        
        assertEquals(actual, expected);
    }

}
