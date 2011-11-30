package edu.brown.seasr.xmluriloadercomponent;

import org.apache.commons.io.IOUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.ProcessingInstruction;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;

/**
 * User: mdellabitta
 * Date: 2010-12-29
 * Time: 8:52 AM
 */
public class URIXMLLoader {

@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
    public String getDocString(String location) throws Exception {
        final URL url = new URI(location).toURL();
        SAXBuilder b = new SAXBuilder(false);

        //turns off external DTD loading
        b.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

        InputStream is = null;
        Document doc = null;

        try {
            is = url.openStream();
            doc = b.build(is);
            
        } finally {
            IOUtils.closeQuietly(is);
        }
        
        Element root = doc.getRootElement();
        root.addContent(new ProcessingInstruction("brown-seasr", new HashMap() {{put("location", url.toString());}}));
        XMLOutputter out = new XMLOutputter(Format.getCompactFormat());
        return out.outputString(doc);
    }
}
