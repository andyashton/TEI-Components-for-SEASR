package edu.brown.seasr.linesplittercomponent;

import java.util.Arrays;
import java.util.List;

/**
 * User: mdellabitta
 * Date: 2011-03-07
 * Time: 8:13 AM
 */
public class LineSplitter {

    private String delimiter;

    public LineSplitter(String delimiter) {
        this.delimiter = delimiter;
    }

    public String[] split(String inString) {
        return inString.split(delimiter);
    }
}
