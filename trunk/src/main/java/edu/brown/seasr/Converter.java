package edu.brown.seasr;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User: mdellabitta
 * Date: 2011-08-25
 * Time: 8:16 AM
 */
public abstract class Converter {

    protected ComponentXMLUtils utils = new ComponentXMLUtils();

    protected Document doc;
    protected String teiPrefix;
    protected Map<String, String> namespaces;

    protected void read(String docString) throws Exception {
        doc = utils.stringToDocument(docString);
        doc.normalize();
        teiPrefix = utils.getTEIPrefix(doc);
        namespaces = utils.scrapeNamespaceDeclarations(doc);
    }

    public abstract String convert(String doc) throws Exception;

    protected void removeSuppliedAndSubst() throws Exception {
        //--Remove <supplied> and <subst> elements.
        utils.xPathElementFindAndReplace(doc, String.format("//%1$s:supplied | //%1$s:subst", teiPrefix), null, namespaces);
    }

    protected void defaultWitnessApps() throws Exception {
        //todo
    }

    protected void highCertaintyChoices() throws Exception {
        //Where <unclear> or <seg> appear in <choice>, choose the highest-certainty value if it has a @cert attribute.
        //probably could do this with xpath if i were guaranteed 2.0 and I had more time.
        String findXPath = String.format("//%1$s:choice[%1$s:unclear[@cert]] | //%1$s:choice[%1$s:seg[@cert]]", teiPrefix);
        NodeList unclearChoices = utils.runXPathNodeListResult(findXPath, doc, namespaces);

        for (int i = 0; i < unclearChoices.getLength(); i++) {
            Element choice = (Element) unclearChoices.item(i);
            int maxCert = 0;
            NodeList unclearOptions = choice.getChildNodes();
            //find the maxcert
            for (int j = 0; j < unclearOptions.getLength(); j++) {
                if (unclearOptions.item(j).getNodeType() != Node.ELEMENT_NODE) continue;
                Element unclearOption = (Element) unclearOptions.item(j);
                int cert = Integer.parseInt(unclearOption.getAttribute("cert"));
                maxCert = maxCert > cert ? maxCert : cert;
            }
            //do the substitution

            findXPath = String.format("./%1$s:unclear[@cert = '%2$s'] | ./%1$s:seg[@cert = '%2$s']", teiPrefix, "" + maxCert);
            NodeList replacements = utils.runXPathNodeListResult(findXPath, choice, namespaces);
            Node parent = choice.getParentNode();
            parent.insertBefore(replacements.item(0), choice);
            parent.removeChild(choice);
        }
    }

    protected void easyChoices() throws Exception {
        //within <choice>, choose the content of <sic>, <abbr>, <orig> (suppress   <corr>, <expan>, <reg>).
        utils.choose(doc, "sic");
        utils.choose(doc, "abbr");
        utils.choose(doc, "orig");
    }

    protected List<String> findAuthors() throws Exception {
        String findXPath = String.format("//%1$s:author/%1$s:persName | //%1$s:author/%1$s:name", teiPrefix);
        NodeList authorNodes = utils.runXPathNodeListResult(findXPath, doc, namespaces);
        List<String> results = new ArrayList<String>(authorNodes.getLength());
        for (int i = 0; i < authorNodes.getLength(); i++) {
            Element authorElement = (Element) authorNodes.item(i);
            results.add(authorElement.getTextContent());
        }

        return results;
    }

    protected void removeNonAuthorNotes() throws Exception {
        List<String> authors = findAuthors();
        String findXPath = String.format("//%1$s:note", teiPrefix);
        NodeList noteNodes = utils.runXPathNodeListResult(findXPath, doc, namespaces);
        for (int i = 0; i < noteNodes.getLength(); i++) {
            Element note = (Element) noteNodes.item(i);
            String author = note.getAttribute("resp");
            if (!authors.contains(author)) {
                note.getParentNode().removeChild(note);
            }
        }
    }
}
