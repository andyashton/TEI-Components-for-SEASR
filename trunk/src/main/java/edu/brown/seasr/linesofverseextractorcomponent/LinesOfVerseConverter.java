package edu.brown.seasr.linesofverseextractorcomponent;

import edu.brown.seasr.Converter;
import org.meandre.annotations.Component;

/**
 * Created by IntelliJ IDEA.
 * User: mdellabitta
 * Date: 2011-10-10
 * Time: 4:39 PM
 */


public class LinesOfVerseConverter extends Converter {

    //Extract lines of verse of any type:
    //Any <l> element

    @Override
    public String convert(String xml) throws Exception {
        read(xml);
        String xPathString = String.format("//%1$s:l", teiPrefix);
        return utils.runXPathStringResult(xPathString, doc, namespaces);
    }
}
