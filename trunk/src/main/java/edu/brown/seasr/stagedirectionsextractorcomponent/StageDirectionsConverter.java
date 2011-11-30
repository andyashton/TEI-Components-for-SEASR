package edu.brown.seasr.stagedirectionsextractorcomponent;

import edu.brown.seasr.Converter;

/**
 * Created by IntelliJ IDEA.
 * User: mdellabitta
 * Date: 2011-10-10
 * Time: 4:35 PM
 */
public class StageDirectionsConverter extends Converter {

    //Extract stage directions/dramatic features <stage>
    @Override
    public String convert(String xml) throws Exception {
        read(xml);
        String xPathString = String.format("//%1$s:stage", teiPrefix);
        return utils.runXPathStringResult(xPathString, doc, namespaces);
    }
}
