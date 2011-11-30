package edu.brown.seasr.expandedcomponent;


import edu.brown.seasr.Converter;
import org.w3c.dom.Document;

import java.util.Map;

/**
 * User: mdellabitta
 * Date: 2011-08-24
 * Time: 7:07 PM
 */
public class ExpandedConverter extends Converter {

/*
    the text with all abbreviations expanded but in other
    respects diplomatic
*/
    
    public String convert(String xml) throws Exception {
        read(xml);

        //the text with all abbreviations expanded
        utils.choose(doc, "expan");

        //but in other respects diplomatic
        removeNonAuthorNotes();
        easyChoices();
        highCertaintyChoices();
        defaultWitnessApps();
        removeSuppliedAndSubst();

        return utils.docToString(doc);
    }
}
