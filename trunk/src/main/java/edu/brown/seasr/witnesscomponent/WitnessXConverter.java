package edu.brown.seasr.witnesscomponent;


import edu.brown.seasr.Converter;

/**
 * User: mdellabitta
 * Date: 2011-08-24
 * Time: 6:40 PM
 */
public class WitnessXConverter extends Converter {

    /*
        the document as represented in a specific witness
        --in <app>, use readings identified with a specific witness
     */

    private String witness;

    public WitnessXConverter(String witness) {
        this.witness = witness;
    }

    public String convert(String xml) throws Exception {
        read(xml);
        //--in <app>, use readings identified with a specific witness
        String findXPath = String.format("//%1$s:app[%1$s:rdg[@wit]]", teiPrefix);
        String replaceXPath = String.format("./%1$s:rdg[@wit = '%2$s']/*", teiPrefix, witness);
        utils.xPathElementFindAndReplace(doc, findXPath, replaceXPath, namespaces);

        
        return utils.docToString(doc);
    }
}
