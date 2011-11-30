package edu.brown.seasr.placenameextractorcomponent;

import edu.brown.seasr.ComponentXMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.*;

/**
 * User: mdellabitta
 * Date: 2011-05-23
 * Time: 8:25 AM
 */
public class PlaceNameExtractor {

    private ComponentXMLUtils xmlUtils = new ComponentXMLUtils();

    public Set<String> extractPlaceNames(String xml) throws Exception {

        Document doc = xmlUtils.stringToDocument(xml);

        Map<String, String> namespaces = xmlUtils.scrapeNamespaceDeclarations(doc);

        String teiPrefix = xmlUtils.getTEIPrefix(doc);
        String findXPath = String.format("//%1$s:placeName", teiPrefix);

        NodeList nodes = xmlUtils.runXPathNodeListResult(findXPath, doc, namespaces);
        Set<String> results = new HashSet<String>(nodes.getLength());
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            String relativePlaceXPath = String.format(".//%1$s:offset | .//%1$s:measure", teiPrefix);
            boolean relative = xmlUtils.runXPathBooleanResult(relativePlaceXPath, node, namespaces);
            if (relative) continue; //relative places are out of scope
            node.normalize();
            NodeList children = node.getChildNodes();
            if (children.getLength() == 0) continue; //empty
            if (children.getLength() == 1 && children.item(0).getNodeType() == Node.TEXT_NODE) {
                results.add(children.item(0).getTextContent());
                continue;
            }
            String joinChildTextNodesXPath = "normalize-space(string-join(.//text(), ' '))";
            String joinedChildText = xmlUtils.runXPathGetSingleStringResult(joinChildTextNodesXPath, node, namespaces);
            if (joinedChildText != null && !"".equals(joinedChildText)) {
                results.add(joinedChildText);
            }
        }

        return results;
    }
}
