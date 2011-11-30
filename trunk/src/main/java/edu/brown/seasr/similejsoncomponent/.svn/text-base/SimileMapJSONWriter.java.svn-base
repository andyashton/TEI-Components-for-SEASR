package edu.brown.seasr.similejsoncomponent;


import edu.brown.seasr.ComponentXMLUtils;
import edu.brown.seasr.geocodingcomponent.Geocoder;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * User: mdellabitta
 * Date: 2011-06-09
 * Time: 6:45 PM
 */
public class SimileMapJSONWriter {

    private ComponentXMLUtils xmlUtils = new ComponentXMLUtils();
    private Geocoder geocoder = new Geocoder();

    public void processTEI(String xml, String color, String path) throws Exception {
        Document doc = xmlUtils.stringToDocument(xml);
        doc.normalize();
        Map<String, String> namespaces = xmlUtils.scrapeNamespaceDeclarations(doc);
        String teiPrefix = xmlUtils.getTEIPrefix(doc);
        String title = xmlUtils.runXPathGetSingleStringResult(String.format("//%1$s:TEI/%1$s:teiHeader/%1$s:fileDesc/%1$s:titleStmt/%1$s:title/text()", teiPrefix), doc, namespaces);

        NodeList nodes = xmlUtils.runXPathNodeListResult(String.format("//%1$s:placeName", teiPrefix), doc, namespaces);
        List<MapEntryBean> beans = new ArrayList<MapEntryBean>(nodes.getLength());

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            String relativePlaceXPath = String.format(".//%1$s:offset | .//%1$s:measure", teiPrefix);
            boolean relative = xmlUtils.runXPathBooleanResult(relativePlaceXPath, node, namespaces);
            if (relative) continue; //relative places are out of scope
            String passage = node.getParentNode().getTextContent();

            NodeList children = node.getChildNodes();
            if (children.getLength() == 0) continue; //empty
            if (children.getLength() == 1 && children.item(0).getNodeType() == Node.TEXT_NODE) {
                String placename = children.item(0).getNodeValue();
                String latlong = geocoder.geocode(placename);
                MapEntryBean bean = new MapEntryBean(placename, color, latlong, title, passage, "TEI", UUID.randomUUID().toString());
                beans.add(bean);
                continue;
            }
            String joinChildTextNodesXPath = "normalize-space( string-join( .//text(), ' ') )";
            String joinedChildText = xmlUtils.runXPathGetSingleStringResult(joinChildTextNodesXPath, node, namespaces);
            if (joinedChildText != null && !"".equals(joinedChildText)) {
                String latlong = geocoder.geocode(joinedChildText);
                MapEntryBean bean = new MapEntryBean(joinedChildText, color, latlong, title, passage, "TEI", UUID.randomUUID().toString());
                beans.add(bean);
            }
        }

        JSONArray jsonArray = null;

        if (new File(path).exists()) {
            Reader reader = new FileReader(path);
            String jsonText = IOUtils.toString(reader);
            reader.close();
            jsonArray = JSONArray.fromObject(jsonText);

        } else {
            jsonArray = new JSONArray();
        }

        for (MapEntryBean bean: beans) {
            JSONObject o = JSONObject.fromObject(bean);
            jsonArray.add(o);
        }

        Writer writer = new FileWriter(path, false);
        jsonArray.write(writer);
        writer.close();
    }

    public class MapEntryBean {

        private String matchingText;
        private String color;
        private String latlng;
        private String label;
        private String passage;
        private String type;
        private String id;

        private MapEntryBean(String matchingText, String color, String latlng, String label, String passage, String type, String id) {
            this.matchingText = matchingText;
            this.color = color;
            this.latlng = latlng;
            this.label = label;
            this.passage = passage;
            this.type = type;
            this.id = id;
        }

        public String getMatchingText() {
            return matchingText;
        }

        public void setMatchingText(String matchingText) {
            this.matchingText = matchingText;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getLatlng() {
            return latlng;
        }

        public void setLatlng(String latlng) {
            this.latlng = latlng;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getPassage() {
            return passage;
        }

        public void setPassage(String passage) {
            this.passage = passage;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

}
