package edu.brown.seasr.dedicationextractorcomponent;

import edu.brown.seasr.Converter;

/**
 * Created by IntelliJ IDEA.
 * User: mdellabitta
 * Date: 2011-10-10
 * Time: 4:05 PM
 */
public class DedicationConverter extends Converter {

    @Override
    public String convert(String xml) throws Exception {

        read(xml);
        String xPathString = String.format("//%1$s:div[type='ded']", teiPrefix);
        return utils.runXPathStringResult(xPathString, doc, namespaces);
    }
}
