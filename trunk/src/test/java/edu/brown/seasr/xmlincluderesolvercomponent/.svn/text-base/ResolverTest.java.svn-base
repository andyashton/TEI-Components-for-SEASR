package edu.brown.seasr.xmlincluderesolvercomponent;

import edu.brown.seasr.SEASRTestUtils;
import edu.brown.seasr.xmluriloadercomponent.URIXMLLoader;
import org.jdom.Document;
import org.jdom.input.SAXBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.StringReader;
import java.util.HashMap;

/**
 * User: mdellabitta
 * Date: 2010-12-29
 * Time: 8:49 AM
 */
public class ResolverTest {

    Resolver r;
    URIXMLLoader loader;
    SEASRTestUtils utils = new SEASRTestUtils();

    @Before
    public void setup() throws Exception {
        utils = new SEASRTestUtils();
        loader = new URIXMLLoader();
        r = new Resolver();
    }

    @Test
    public void testSimpleResolve() throws Exception {
        File simpleDocFile = new File(utils.getResourcesDir(), "simple-doc.xml");
        Document doc = load(simpleDocFile);
        String resolvedXML = utils.documentToString(doc);
        utils.checkXPaths(
                resolvedXML,
                new HashMap<String, String>() {{
                    put("xml", "http://www.w3.org/XML/1998/namespace");
                    put("tei", "http://www.wwp.brown.edu/ns/textbase/storage/1.0");
                }},
                "/root[@xml:base = '" + simpleDocFile.getParentFile().toURI().toString() + "']",
                "//tei:teiHeader[@xml:id = 'TR00423.hdr']"
        );
    }

    private Document load(File file) throws Exception {
        String docString = loader.getDocString(file.toURI().toString());
        docString = r.resolve(docString);
        SAXBuilder b = new SAXBuilder();
        return b.build(new StringReader(docString));
    }
}
