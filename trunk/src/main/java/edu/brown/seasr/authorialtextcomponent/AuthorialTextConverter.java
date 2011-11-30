package edu.brown.seasr.authorialtextcomponent;

import edu.brown.seasr.Converter;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.List;

/**
 * User: mdellabitta
 * Date: 2011-08-24
 * Time: 7:05 PM
 */
public class AuthorialTextConverter extends Converter {

    /*
        the text including only the words for which
        the author is directly responsible
     */

    public String convert(String docString) throws Exception {

        read(docString);

        //--no apparatus or notes
        //--suppress <fw>
        utils.remove(doc, String.format("//%1$s:app | //%1$s:note | //%1$s:fw", teiPrefix), namespaces);

        //--no <add> or <del> by any hand other than the author
        List<String> authors = findAuthors();
        String findXPath = String.format("//%1$s:add | //%1$s:del", teiPrefix);
        NodeList nodes = utils.runXPathNodeListResult(findXPath, doc, namespaces);

        for (int i = 0; i < nodes.getLength(); i++) {
            Element element = (Element) nodes.item(i);
            if (!element.hasAttribute("resp") || !authors.contains(element.getAttribute("resp"))) {
                element.getParentNode().removeChild(element);
            }
        }

        //TODO specs said remove printer, but I don't see it in the tei guidelines
        utils.remove(doc, String.format("//%1$s:publisher | //%1$s:editor", teiPrefix), namespaces);

        return utils.docToString(doc);
    }
}
