package edu.brown.seasr.externallyrevisedcomponent;

import edu.brown.seasr.Converter;
import org.w3c.dom.NodeList;

/**
 * User: mdellabitta
 * Date: 2011-08-24
 * Time: 7:06 PM
 */
public class ExternallyRevisedConverter extends Converter {

    private String revisorName;

    public ExternallyRevisedConverter(String revisorName) {
        this.revisorName = revisorName;
    }

    /*
       the text as revised by some external hand
       --observe <add> and <del> only when by the external hand
    */

    public String convert(String xml) throws Exception {
        read(xml);

        //--observe <add> and <del> only when by the external hand
        String findXPath = String.format("//%1$s:add[@hand != '%2$s'] | //%1$s:del[@hand != '%2$s']", teiPrefix, revisorName);
        utils.remove(doc, findXPath, namespaces);

        findXPath = String.format("//%1$s:add | //%1$s:del", teiPrefix);
        utils.xPathElementFindAndReplace(doc, findXPath, "./*", namespaces);

        return utils.docToString(doc);
    }
}
