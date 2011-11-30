package edu.brown.seasr.xmlincluderesolvercomponent;

import edu.brown.seasr.ComponentXMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.ProcessingInstruction;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;

import static javax.xml.xpath.XPathConstants.*;

import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.StringReader;
import java.net.URI;

/**
 * User: mdellabitta
 * Date: 2010-12-17
 * Time: 9:27 AM
 */
public class Resolver {

    private XPathExpression brownSeasrPIXPE;

    private DocumentBuilder unawareDB;
    private DocumentBuilder awareDB;

    public Resolver() {

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            unawareDB = dbf.newDocumentBuilder();
            dbf.setXIncludeAware(true);
            awareDB = dbf.newDocumentBuilder();

            XPathFactory factory = XPathFactory.newInstance();
            XPath xPath = factory.newXPath();
            brownSeasrPIXPE = xPath.compile("//processing-instruction('brown-seasr')");

        } catch (Exception e) {
            throw new RuntimeException("Unable to initialize Resolver.", e);
        }
    }

    public String resolve(String docString) throws Exception {
        //render the brown-seasr processing instruction into an xml:base tag on the root node
        Document doc = unawareDB.parse(new InputSource(new StringReader(docString)));
        ProcessingInstruction piNode = (ProcessingInstruction) brownSeasrPIXPE.evaluate(doc, NODE);
        String locationData = piNode.getData();
        String location = locationData.split("[\"|']")[1];
        URI locationURI = new URI(location);

        Element root = doc.getDocumentElement();
        root.setAttribute("xml:base", locationURI.resolve("./").toString());

        ComponentXMLUtils utils = new ComponentXMLUtils();
        docString = utils.docToString(doc);

        //Now run that doc through a xml include-aware document builder
        doc = awareDB.parse(new InputSource(new StringReader(docString)));

        //output it as a string.
        return utils.docToString(doc);
    }
}
