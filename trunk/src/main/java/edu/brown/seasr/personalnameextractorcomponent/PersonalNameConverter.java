package edu.brown.seasr.personalnameextractorcomponent;

import edu.brown.seasr.Converter;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashSet;

/**
 * Created by IntelliJ IDEA.
 * User: mdellabitta
 * Date: 2011-10-10
 * Time: 3:09 PM
 */
public class PersonalNameConverter extends Converter {

    public String convert(String xml) throws Exception {
        read(xml);
        String xPathString = String.format("//%1$s:persName", teiPrefix);
        return utils.runXPathStringResult(xPathString, doc, namespaces);
    }
}
