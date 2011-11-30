package edu.brown.seasr.speechextractorcomponent;

import edu.brown.seasr.Converter;

/**
 * Created by IntelliJ IDEA.
 * User: mdellabitta
 * Date: 2011-10-10
 * Time: 3:24 PM
 */
public class SpeechConverter extends Converter {

    @Override
    public String convert(String xml) throws Exception {
        read(xml);
        String xPathString = String.format("//%1$s:sp | //%1$s:q", teiPrefix);
        return utils.runXPathStringResult(xPathString, doc, namespaces);
    }
}
