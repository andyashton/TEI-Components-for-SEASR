package edu.brown.seasr.tagstripper;

import edu.brown.seasr.ComponentXMLUtils;

/**
 * User: mdellabitta
 * Date: 2011-04-06
 * Time: 6:15 PM
 */
public class TagStripper {

    private ComponentXMLUtils utils = new ComponentXMLUtils();

    public String stripTags(String xml) throws Exception {
        return utils.stripTags(utils.stringToDocument(xml)).replaceAll("\\s+", " ").trim();
    }
}
