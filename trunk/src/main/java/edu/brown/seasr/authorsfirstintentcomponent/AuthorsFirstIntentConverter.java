package edu.brown.seasr.authorsfirstintentcomponent;

import edu.brown.seasr.Converter;

/**
 * User: mdellabitta
 * Date: 2011-08-24
 * Time: 6:35 PM
 */
public class AuthorsFirstIntentConverter extends Converter {

    /*
        the document as originally written, without authorial revisions:
        --as above, except reverse the treatment of <add> and <del>
    */

    public String convert(String xml) throws Exception {
        read(xml);
        utils.choose(doc, "del");

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
