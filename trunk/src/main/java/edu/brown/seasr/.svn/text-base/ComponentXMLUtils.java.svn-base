package edu.brown.seasr;

import net.sf.saxon.xpath.XPathFactoryImpl;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * User: mdellabitta
 * Date: 2011-02-15
 * Time: 10:01 AM
 */
public class ComponentXMLUtils {

    private TransformerFactory tf;
    private DocumentBuilderFactory dbf;
    private XPathFactory xpf;

    private XPathFactory getXPathFactory() throws Exception {
//        Class.forName("net.sf.saxon.xpath.XPathFactoryImpl");
//        System.setProperty("javax.xml.xpath.XPathFactory:" + NamespaceConstant.OBJECT_MODEL_SAXON, "net.sf.saxon.xpath.XPathFactoryImpl");

        if (xpf == null) {
            xpf = new XPathFactoryImpl();
        }

        return xpf;
    }

    private DocumentBuilderFactory getDocumentBuilderFactory() throws Exception {

        if (dbf == null) {
            dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            dbf.setValidating(false);
            dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
            dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        }

        return dbf;
    }

    private TransformerFactory getTransformerFactory() {
        if (tf == null) {
            tf = TransformerFactory.newInstance();
        }

        return tf;
    }

    public String docToString(Document doc) throws Exception {
        DOMSource domSource = new DOMSource(doc);
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);

        Transformer transformer = getTransformerFactory().newTransformer();
        transformer.transform(domSource, result);

        return writer.toString();
    }

    public Document stringToDocument(String xml) throws Exception {
        DocumentBuilderFactory dbf = getDocumentBuilderFactory();
        DocumentBuilder db = dbf.newDocumentBuilder();
        return db.parse(new InputSource(new StringReader(xml)));
    }

    public String stripTags(Node rootNode) throws Exception {

        NodeList nodes = getNodeListResult("//text()", rootNode, new HashMap<String, String>());

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            sb.append(node.getTextContent());
        }

        return sb.toString();
    }

    public NodeList runXPathNodeListResult(String xPathString, Node node, Map<String, String> namespaces) throws Exception {
        return getNodeListResult(xPathString, node, namespaces);
    }

    public boolean runXPathBooleanResult(String xPathString, Node node, Map<String, String> namespaces) throws Exception {
        NodeList nodes = getNodeListResult(xPathString, node, namespaces);

        return nodes != null && nodes.getLength() > 0;
    }

    public String runXPathStringResult(String xPathString, Node node, Map<String, String> namespaces) throws Exception {

        NodeList results = getNodeListResult(xPathString, node, namespaces);

        if (results.getLength() == 0) {
            return null;
        }

        Document returnDoc = getDocumentBuilderFactory().newDocumentBuilder().newDocument();

        if (results.getLength() == 1) {
            Node newNode = returnDoc.importNode(results.item(0), true);
            Element root;
            switch (results.item(0).getNodeType()) {
                case Node.TEXT_NODE:
                    root = returnDoc.createElement("results");
                    returnDoc.appendChild(root);
                    root.appendChild(newNode);

                    return docToString(returnDoc);

                case Node.ELEMENT_NODE:
                    returnDoc.appendChild(newNode);

                    return docToString(returnDoc);

                case Node.ATTRIBUTE_NODE:
                    root = returnDoc.createElement("results");
                    returnDoc.appendChild(root);
                    root.setAttributeNodeNS((Attr) newNode);

                    return docToString(returnDoc);
            }
        }

        Element root = returnDoc.createElement("results");
        returnDoc.appendChild(root);

        for (int i = 0; i < results.getLength(); i++) {

            Node newNode = returnDoc.importNode(results.item(i), true);

            switch (newNode.getNodeType()) {
                case Node.ELEMENT_NODE:
                    root.appendChild(newNode);
                    break;
                case Node.ATTRIBUTE_NODE:
                    root.setAttributeNodeNS((Attr) newNode);
                    break;
                case Node.TEXT_NODE:
                    Element text = returnDoc.createElement("text");
                    root.appendChild(text);
                    text.appendChild(newNode);
                    break;

                default:
                    throw new UnsupportedOperationException("Not implemented: Node type " + newNode.getNodeType());
            }
        }

        return docToString(returnDoc);
    }

    private NodeList getNodeListResult(String xPathString, Node contextNode, Map<String, String> namespaces) throws Exception {
        XPath xPath = getXPathFactory().newXPath();
        NamespaceContext nc = getNamespaceContext(namespaces);
        xPath.setNamespaceContext(nc);
        return (NodeList) xPath.evaluate(xPathString, contextNode, XPathConstants.NODESET);
    }

//    private NodeList getNodeListResult(String xPathString, Node contextNode, Map<String, String> namespaces) throws Exception {
//        DOMSource domSource = new DOMSource(contextNode);
//        XPathEvaluator xPathEvaluator = new XPathEvaluator();
//        NamespaceContext nc = getNamespaceContext(namespaces);
//        xPathEvaluator.setNamespaceContext(nc);
//        xPathEvaluator.setSource(domSource);
//
//
//        return (NodeList) xPathEvaluator.evaluate(xPathString, contextNode, XPathConstants.NODESET);
//    }

    public String runXPathGetSingleStringResult(String xPathString, Node contextNode, Map<String, String> namespaces) throws Exception {
        XPath xPath = getXPathFactory().newXPath();
        NamespaceContext nc = getNamespaceContext(namespaces);
        xPath.setNamespaceContext(nc);
        return (String) xPath.evaluate(xPathString, contextNode, XPathConstants.STRING);
    }


//    public String runXPathGetSingleStringResult(String xPathString, Node contextNode, Map<String, String> namespaces) throws Exception {
//        DOMSource domSource = new DOMSource(contextNode);
//        XPathEvaluator xPathEvaluator = new XPathEvaluator();
//        xPathEvaluator.setSource(domSource);
//        return (String) xPathEvaluator.evaluate(xPathString, contextNode, XPathConstants.STRING);
//    }

    public Map<String, String> scrapeNamespaceDeclarations(Node node) throws Exception {
        Map<String, String> nsMap = new HashMap<String, String>();
        XMLInputFactory f = XMLInputFactory.newInstance();
        f.setProperty(XMLInputFactory.IS_VALIDATING, false);
        f.setProperty(XMLInputFactory.SUPPORT_DTD, false);
        XMLStreamReader r = f.createXMLStreamReader(new DOMSource(node));
        while (r.hasNext()) {
            if (XMLStreamConstants.START_ELEMENT == r.next()) {
                for (int i = 0; i < r.getNamespaceCount(); i++) {
                    String prefix = r.getNamespacePrefix(i);
                    String uri = r.getNamespaceURI(i);
                    if (prefix == null || "".equals(prefix)) {
                        prefix = "_default"; //otherwise there's no way to specify this in xpath
                    }
                    nsMap.put(prefix, uri);
                }
            }
        }

        return nsMap;
    }

    public String getTEIPrefix(Document doc) throws Exception {
        Element root = doc.getDocumentElement();
        if (root.getNodeName().contains("TEI")) {
            String prefix = root.getPrefix();
            if (null == prefix) return "_default";
            return prefix;
        }

        throw new Exception("TEI namespace prefix not found.");
    }

    public NamespaceContext getNamespaceContext(final Map<String, String> nsMap) {
        nsMap.put("xml", "http://www.w3.org/XML/1998/namespace");
        return new NamespaceContext() {

            public String getNamespaceURI(String prefix) {
                return nsMap.get(prefix);
            }

            public String getPrefix(String uri) {
                throw new UnsupportedOperationException("Not implemented.");
            }

            public Iterator getPrefixes(String uri) {
                throw new UnsupportedOperationException("Not implemented.");
            }
        };
    }

    public Document xPathElementFindAndReplace(Document doc, String findXPath, String replaceXPath) throws Exception {
        Map<String, String> namespaces = scrapeNamespaceDeclarations(doc);
        return xPathElementFindAndReplace(doc, findXPath, replaceXPath, namespaces);
    }

    public Document xPathElementFindAndReplace(Document doc, String findXPath, String replaceXPath, Map<String, String> namespaces) throws Exception {
        NodeList results = getNodeListResult(findXPath, doc, namespaces);

        if (results == null || results.getLength() == 0) {
            return doc;
        }

        for (int i = 0; i < results.getLength(); i++) {
            Node node = results.item(i);
            node.normalize();
            Node parent = node.getParentNode();

            if (node.getNodeType() != Node.ELEMENT_NODE) {
                throw new Exception("Find XPath must select element nodes only.");
            }

            if (replaceXPath == null) {
                parent.removeChild(node);
                continue;
            }

            NodeList replacements = getNodeListResult(replaceXPath, node, namespaces);

            if (replacements == null || replacements.getLength() == 0) {
                continue;
                //throw new Exception("Unable to find replacement node for selected node.");
            }

            for (int j = 0; j < replacements.getLength(); j++) {
                Node replacement = replacements.item(0);

                switch (replacement.getNodeType()) {
                    case Node.ELEMENT_NODE:
                    case Node.TEXT_NODE:
                        break;
                    default:
                        throw new Exception("Replacement node is incorrect type.");
                }

                parent.insertBefore(replacement, node);
            }

            parent.removeChild(node);
        }

        return doc;
    }

    public Document remove(Document doc, String xpath, Map<String, String> namespaces) throws Exception {

        NodeList results = getNodeListResult(xpath, doc, namespaces);

        for (int i = 0; i < results.getLength(); i++) {
            Node node = results.item(i);
            Node parent = node.getParentNode();
            parent.removeChild(node);
        }

        return doc;
    }

    public Document choose(Document doc, String choiceTag) throws Exception {
        Map<String, String> namespaces = scrapeNamespaceDeclarations(doc);
        String teiPrefix = getTEIPrefix(doc);
        String findXPath = String.format("//%1$s:choice[%1$s:%2$s]", teiPrefix, choiceTag);
        String replaceXPath = String.format("%1$s:%2$s/node()", teiPrefix, choiceTag);
        Document result = xPathElementFindAndReplace(doc, findXPath, replaceXPath, namespaces);
        return remove(result, findXPath, namespaces);
    }
}
