package edu.brown.seasr.lettersextractorcomponent;

import edu.brown.seasr.Converter;
import org.meandre.annotations.Component;

/**
 * Created by IntelliJ IDEA.
 * User: mdellabitta
 * Date: 2011-10-10
 * Time: 4:30 PM
 */
public class LettersConverter extends Converter {

    //<div type="prefatory">
    @Override
    public String convert(String xml) throws Exception {
        read(xml);
        String xPathString = String.format("%1$s:div[type='prefatory']", teiPrefix);
        return utils.runXPathStringResult(xPathString, doc, namespaces);
    }
}
