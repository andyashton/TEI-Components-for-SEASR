package edu.brown.seasr.xpathfiltercomponent;

import edu.brown.seasr.ComponentXMLUtils;
import org.w3c.dom.Document;

import java.util.Map;

/**
 * User: mdellabitta
 * Date: 2011-03-10
 * Time: 8:22 AM
 */
public class XPathFilter {

    private ComponentXMLUtils utils = new ComponentXMLUtils();

    public boolean hasXPath(String xPath, String xml) throws Exception {
        Document doc = utils.stringToDocument(xml);
        Map<String, String> namespaces = utils.scrapeNamespaceDeclarations(doc);
        return utils.runXPathBooleanResult(xPath, doc, namespaces);
    }
}
