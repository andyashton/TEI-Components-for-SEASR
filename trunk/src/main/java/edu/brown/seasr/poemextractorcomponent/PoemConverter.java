package edu.brown.seasr.poemextractorcomponent;

import edu.brown.seasr.Converter;

/**
 * Created by IntelliJ IDEA.
 * User: mdellabitta
 * Date: 2011-10-10
 * Time: 3:36 PM
 */
public class PoemConverter extends Converter {

    private static final String[] types = {"poem", "couplet", "tercet", "quatrain", "quintain", "sextet",
            "septet", "octave", "nonet", "decastich"};

    @Override
    public String convert(String xml) throws Exception {
        read(xml);

        StringBuilder sb = new StringBuilder();
        //Extract verse included in a longer text.  Select only:
        //Any div or lg element with one of the following @type attribute:
        // poem, couplet, tercet, quatrain, quintain (poetry), sextet, septet, octave, nonet, decastich

        for (String type: types) {
            sb.append("//%1$s:div[@type = '");
            sb.append(type);
            sb.append("'] | ");
            sb.append("//%1$s:lg[@type = '");
            sb.append(type);
            sb.append("'] | ");
        }



        //Any lg[@type=”stanza”] if not already within one of the first two items.
        sb.append("//%1$s:lg[@type='stanza' and not [ancestor::%1$s:div or ancestor::%1$s:lg]]");
        //Any milestone[@type=”poem”] if not already within one of the first two items.
        sb.append(" | //%1$s:milestone[@type='poem' and not [ancestor::%1$s:div or ancestor::%1$s:lg]]");
        String xpath = String.format(sb.toString(), teiPrefix);
        return utils.runXPathStringResult(xpath, doc, namespaces);
    }
}
