package edu.brown.seasr.teicorpuscomponent;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import java.io.StringReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;

/**
 * User: mdellabitta
 * Date: 2010-12-30
 * Time: 9:11 AM
 */
public class TEICorpusBuilder {

    public String buildCorpusDoc(String[] teiDocs) throws Exception {
        
        SAXBuilder b = new SAXBuilder();
        Document doc = new Document();
        Element teiCorpusElement = new Element("teiCorpus");
        doc.addContent(teiCorpusElement);

        Element teiHeader = newEle("teiHeader", teiCorpusElement);
        Element fileDesc = newEle("fileDesc", teiHeader);

        Element titleStmt = newEle("titleStmt", fileDesc);
        Element title = newEle("title", titleStmt);
        title.setAttribute("type", "main");
        title.setText("Automatically generated aggregate document");

        Element publicationStmt = newEle("publicationStmt", fileDesc);
        Element publisher = newEle("publisher", publicationStmt);
        publisher.setText("Brown University TEIDocGlobComponent for SEASR");
        Element pubPlace = newEle("pubPlace", publicationStmt);
        pubPlace.setText("Meandre Infrastructure");
        Element date = newEle("date", publicationStmt);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") {{ setTimeZone(TimeZone.getTimeZone("GMT")); }};
        date.setAttribute("when", dateFormat.format(new Date()));

        for (String documentString : teiDocs) {
                Document newDoc = b.build(new StringReader(documentString));
                teiCorpusElement.addContent(newDoc.getRootElement().detach());
        }

        XMLOutputter out = new XMLOutputter();
        StringWriter sw = new StringWriter();
        out.output(doc, sw);

        return sw.toString();
    }

    private Element newEle(String name, Element parent) {
        Element e = new Element(name);
        parent.addContent(e);
        return e;
    }        
}
