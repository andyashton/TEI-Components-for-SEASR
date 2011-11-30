package edu.brown.seasr.modernizecomponent;

import edu.brown.seasr.Converter;
import org.w3c.dom.Document;

import java.util.Map;


/**
 * User: mdellabitta
 * Date: 2011-08-24
 * Time: 8:17 AM
 */
public class ModernizedConverter extends Converter {

    /*
        the text with modernized spelling (provided through <reg>)
    */

    public String convert(String xml) throws Exception {
        read(xml);
        utils.choose(doc, "reg");

        return utils.docToString(doc);
    }
}
