package edu.brown.seasr.xpathfindandreplacecomponent;

import edu.brown.seasr.ComponentXMLUtils;

/**
 * User: mdellabitta
 * Date: 2011-04-08
 * Time: 5:10 PM
 */
public class XPathFindAndReplace {

    public String process(String xml, String findXPath, String replaceXPath) throws Exception {
        ComponentXMLUtils utils = new ComponentXMLUtils();
        
        return utils.docToString(utils.xPathElementFindAndReplace(utils.stringToDocument(xml), findXPath, replaceXPath));
    }
}
