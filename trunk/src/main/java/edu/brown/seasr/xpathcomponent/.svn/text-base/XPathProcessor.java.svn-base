package edu.brown.seasr.xpathcomponent;

import edu.brown.seasr.ComponentXMLUtils;
import org.w3c.dom.Document;

import java.util.Map;

/**
 * User: mdellabitta
 * Date: 2011-02-24
 * Time: 8:33 AM
 */
public class XPathProcessor {

    private ComponentXMLUtils utils = new ComponentXMLUtils();

    public String executeXPath(String xml, String xPath) throws Exception {
        Document doc = utils.stringToDocument(xml);
        Map<String, String> namespaces = utils.scrapeNamespaceDeclarations(doc);
        return utils.runXPathStringResult(xPath, doc, namespaces);
    }
}
