package edu.brown.seasr.normalizecomponent;

import edu.brown.seasr.Converter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Map;

/**
 * User: mdellabitta
 * Date: 2011-06-15
 * Time: 8:19 AM
 */
public class NormalizedConverter extends Converter {
    /*
    the document as it appeared on the page, but edited by an editor. Features include:
    --within <choice>, use content of <corr>, <expan>, <reg> (suppress
        <sic>, <abbr>, <orig>). Still choose high-certainty <unclear> and <seg>.
    --give content of all revision elements (still not sure what to do
        with <subst>) and include content of <supplied>
    --otherwise as above
     */

    public String convert(String xml) throws Exception {
        read(xml);

        //--within <choice>, use content of <corr>, <expan>, <reg> (suppress <sic>, <abbr>, <orig>).
        easyChoices();

        //Still choose high-certainty <unclear> and <seg>.
        highCertaintyChoices();

        //--In a <subst> element, choose the value of the <add> element and not the <del> element.
        substituteAdd();

        //--Include <supplied> elements
        supplySupplied();

        return utils.docToString(doc);
    }

    void supplySupplied() throws Exception {
        String findXPath = String.format("//%1$s:supplied", teiPrefix);
        String replaceXPath = ".";
        utils.xPathElementFindAndReplace(doc, findXPath, replaceXPath, namespaces);
    }

    void substituteAdd() throws Exception {
        String findXPath = String.format("//%1$s:subst", teiPrefix);
        String replaceXPath = String.format("./%1$s:add", teiPrefix);
        utils.xPathElementFindAndReplace(doc, findXPath, replaceXPath, namespaces);

    }

    void easyChoices(Document doc) throws Exception {
        //within <choice>, choose the content of <sic>, <abbr>, <orig> (suppress   <corr>, <expan>, <reg>).
        utils.choose(doc, "corr");
        utils.choose(doc, "expan");
        utils.choose(doc, "reg");
    }

}
