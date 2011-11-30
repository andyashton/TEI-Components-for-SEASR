package edu.brown.seasr.diplomaticcomponent;

import edu.brown.seasr.Converter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.List;
import java.util.Map;

/**
 * User: mdellabitta
 * Date: 2011-06-10
 * Time: 6:18 PM
 */
public class DiplomaticConverter extends Converter {

    /*
the document as it appears on the page without
revision or editorial intervention of any kind. Features include:
--no <note> by anyone except the author
--within <choice>, use content of <sic>, <abbr>, <orig> (suppress
<corr>, <expan>, <reg>). Where <unclear> or <seg> appear in <choice>,
choose the highest-certainty value
--choose children of <app> based on witness identification
--suppress <supplied>
     */


    public String convert(String xml) throws Exception {
        read(xml);

        //--no <note> by anyone except the author
        removeNonAuthorNotes();
        //--within <choice>, use content of <sic>, <abbr>, <orig> (suppress <corr>, <expan>, <reg>).
        easyChoices();
        // Where <unclear> or <seg> appear in <choice>, choose the highest-certainty value
        highCertaintyChoices();
        //--choose children of <app> based on witness identification
        defaultWitnessApps();
        //--suppress <supplied>
        removeSuppliedAndSubst();

        return utils.docToString(doc);
    }

}
