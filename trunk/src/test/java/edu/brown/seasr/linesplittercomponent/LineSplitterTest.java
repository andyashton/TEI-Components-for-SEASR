package edu.brown.seasr.linesplittercomponent;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

/**
 * User: mdellabitta
 * Date: 2011-03-07
 * Time: 8:47 AM
 */
public class LineSplitterTest {

    @Test
    public void test() {
        String[][] data = new String[][]{
                new String[]{"how", "now", "brown", "cow"},
                new String[]{"eat", "at", "joe's"},
                new String[]{"who", "put", "the", "bomp", "in", "the", "bomp", "she-bomp"}
        };

        String[] delimiters = new String[]{",", "\n", "bob"};

        for (String delimiter : delimiters) {
            LineSplitter ls = new LineSplitter(delimiter);

            for (String[] testStrings : data) {
                String testString = join(delimiter, testStrings);
                String[] results = ls.split(testString);
                assertTrue(Arrays.deepEquals(testStrings, results));
            }
        }
    }

    private String join(String delimiter, String... args) {

        StringBuilder sb = new StringBuilder();

        for (String arg : args) {
            sb.append(arg);
            sb.append(delimiter);
        }

        sb.delete(sb.lastIndexOf(delimiter), sb.length());

        return sb.toString();
    }
}
