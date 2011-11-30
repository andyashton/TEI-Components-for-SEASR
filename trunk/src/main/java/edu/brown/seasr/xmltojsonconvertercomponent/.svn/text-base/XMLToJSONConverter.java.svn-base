package edu.brown.seasr.xmltojsonconvertercomponent;

import net.sf.json.JSON;
import net.sf.json.xml.XMLSerializer;

/**
 * User: mdellabitta
 * Date: 2011-02-17
 * Time: 9:17 AM
 */
public class XMLToJSONConverter {

    public String convert(String xml) {

        XMLSerializer xmlSerializer = new XMLSerializer();
        JSON json = xmlSerializer.read(xml);
        return json.toString();
    }
}
