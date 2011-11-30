package edu.brown.seasr.authoriallyrevisedcomponent;

import edu.brown.seasr.Converter;
import org.w3c.dom.Document;

import java.util.Map;

public class AuthoriallyRevisedConverter extends Converter {
    
/*
the document representing the author's final intentions

*/

    public String convert(String xml) throws Exception {
        read(xml);
                
        //include the outcome of <add> and <del>
        //(that is, include the content of <add> and suppress the content of <del>)
        //and of <subst>
        String findXPath = String.format("//%1$s:del", teiPrefix);
        utils.remove(doc, findXPath, namespaces);

        findXPath = String.format("//%1$s:add | //%1$s:subst", teiPrefix);
        String replaceXPath = "./*";

        utils.xPathElementFindAndReplace(doc, findXPath, replaceXPath, namespaces);

        //--otherwise as above
        easyChoices();
        removeSuppliedAndSubst();
        highCertaintyChoices();

        return utils.docToString(doc);
    }
}
