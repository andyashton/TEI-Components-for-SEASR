package edu.brown.seasr;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;

import javax.xml.namespace.NamespaceContext;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * User: mdellabitta
 * Date: 2011-02-28
 * Time: 8:32 AM
 */
public class ComponentXMLUtilsTest {

    ComponentXMLUtils cu = null;
    SEASRTestUtils u = null;

    @Before
    public void setUp() throws Exception {
        cu = new ComponentXMLUtils();
        u = new SEASRTestUtils();
    }

    @Test
    public void testGetTEIPrefix() throws Exception {
        String docString = u.loadFromResources("bacon.sermons.tei.xml");
        Document doc = cu.stringToDocument(docString);
        String prefix = cu.getTEIPrefix(doc);
    }

    @Test
    public void roundTripXML() throws Exception {
        String docString = u.loadFromResources("bacon.sermons.tei.xml");
        Document doc = cu.stringToDocument(docString);
        docString = cu.docToString(doc);

        Map<String, String> namespaces = new HashMap<String, String>() {{
            put("tei", "http://www.wwp.brown.edu/ns/textbase/storage/1.0");
        }};
        
        u.checkXPaths(docString, namespaces, "//tei:teiHeader[@xml:id = 'TR00484.hdr']");
    }

    @Test
    public void testRunSimpleXPath() throws Exception {
        Map<String, String> noNamespaces = new HashMap<String, String>();

        String docString = u.loadFromResources("address-book.xml");
        Document doc = cu.stringToDocument(docString);
        Map<String,String> namespaces = cu.scrapeNamespaceDeclarations(doc);
        String result = cu.runXPathStringResult("//foo", doc, namespaces);
        assertNull(result);

        //noinspection UnusedAssignment
        result = cu.runXPathStringResult("/addressBook/card[1]", doc, namespaces);

        result = cu.runXPathStringResult("/addressBook", doc, namespaces);
        u.checkXPaths(result, noNamespaces, "/addressBook", "//card[1]", "/addressBook/card/name", "//email[1]");

        result = cu.runXPathStringResult("//name", doc, namespaces);
        u.checkXPaths(result, noNamespaces, "/results/name[1]", "//text() ='Fred Blogggs'");

        result = cu.runXPathStringResult("/addressBook/card/name", doc, namespaces);
        u.checkXPaths(result, noNamespaces, "/results/name", "//text() = 'John Smith'");

        result = cu.runXPathStringResult("//name/text()", doc, namespaces);
        u.checkXPaths(result, noNamespaces, "/results/text[1]/text()");
    }

    @Test
    public void testRunComplexXPath() throws Exception {
        String docString = u.loadFromResources("bacon.sermons.tei.xml");
        Document doc = cu.stringToDocument(docString);
        Map<String, String> namespaces = cu.scrapeNamespaceDeclarations(doc);
        String result = cu.runXPathStringResult("/_default:TEI/_default:teiHeader/@xml:id", doc, namespaces);
        namespaces.put("xml", "http://www.w3.org/XML/1998/namespace");
        u.checkXPaths(result, namespaces, "//@xml:id");
    }

    @Test
    public void testScrapeNamespaces() throws Exception {
        String docString = u.loadFromResources("bacon.sermons.tei.xml");
        Document doc = cu.stringToDocument(docString);
        Map<String, String> namespaces = cu.scrapeNamespaceDeclarations(doc);

        for (String prefix: Arrays.asList("xi", "_default")) {
            assertNotNull("Missing prefix: " + prefix, namespaces.get(prefix));
        }
    }

    @Test
    public void testGetNamespaceContext() throws Exception {
        Map<String, String> namespaces = new HashMap<String, String>() {{
            put("google", "http://www.google.com/fakenamespaces#");
            put("amazon", "http://www.amazon.com/fakenamespaces#");
            put("brown", "http://www.brown.edu/fakenamespaces#");
            put("_default", "http://www.reddit.com/fakenamespaces#");
        }};

        NamespaceContext nc = cu.getNamespaceContext(namespaces);
        
        for (Map.Entry<String, String> entry: namespaces.entrySet()) {
            assertEquals(entry.getValue(), nc.getNamespaceURI(entry.getKey()));
        }
    }

    @Test
    public void testXPathFindAndReplace() throws Exception {
        String xml = u.loadFromResources("address-book.xml");
        Document doc = cu.stringToDocument(xml);
        Document result = cu.xPathElementFindAndReplace(doc, "//card", "name");
        u.checkXPaths(cu.docToString(result), new HashMap<String, String>(), "/addressBook", "/addressBook/name", "/addressBook/name[text() = 'John Smith']");
    }

    @Test
    public void choose() throws Exception {
        String xml = u.loadFromResources("bacon.sermons.tei.xml");
        Document doc = cu.stringToDocument(xml);
        Document resultDoc = cu.choose(doc, "expan");
        resultDoc = cu.choose(resultDoc, "sic");
        String result = cu.docToString(resultDoc);
        assertTrue("expan not resolved.", result.contains("experience"));
        assertFalse("sic not removed.", result.contains("<sic"));
        assertFalse("expan not removed.", result.contains("<expan"));

    }
}
