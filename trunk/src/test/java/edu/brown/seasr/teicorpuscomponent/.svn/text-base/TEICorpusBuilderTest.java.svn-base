package edu.brown.seasr.teicorpuscomponent;

import edu.brown.seasr.SEASRTestUtils;
import org.jdom.Document;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;
import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * User: mdellabitta
 * Date: 2010-12-30
 * Time: 9:35 AM
 */
public class TEICorpusBuilderTest {

    SAXBuilder saxBuilder = new SAXBuilder();
    TEICorpusBuilder b = new TEICorpusBuilder();
    SEASRTestUtils utils;
    XPath teiCorpusXPath;
    Namespace brownNS = Namespace.getNamespace("brown", "http://www.wwp.brown.edu/ns/textbase/storage/1.0");

    @Before
    public void setup() throws Exception {
        utils = new SEASRTestUtils();
        teiCorpusXPath = XPath.newInstance("/teiCorpus");
    }

    @Test
    public void testGoodDocument() throws Exception {
        String sourceDocString = utils.loadFromResources("davies.arraignment.xml");
        String outputDocString = b.buildCorpusDoc(new String[] {sourceDocString});

        System.out.println(outputDocString);

        Document doc = saxBuilder.build(new StringReader(outputDocString));

        assertTrue(testSingleSelect(doc, teiCorpusXPath));
        assertTrue(testSingleSelect(doc, buildBrownXPath("//brown:teiHeader[@xml:id='TR00016.hdr']")));
    }

    @Test
    public void testMultipleGoodDocuments() throws Exception {
        String source1 = utils.loadFromResources("davies.arraignment.xml");
        String source2 = utils.loadFromResources("elizabeth.goldendewes2.xml");
        String source3 = utils.loadFromResources("fell.thismajor.xml");

        String outputDocString = b.buildCorpusDoc(new String[] {source1, source2, source3});
        Document doc = saxBuilder.build(new StringReader(outputDocString));

        assertTrue(testSingleSelect(doc, teiCorpusXPath));
        assertTrue(testSingleSelect(doc, buildBrownXPath("//brown:teiHeader[@xml:id='TR00016.hdr']")));
        assertTrue(testSingleSelect(doc, buildBrownXPath("//brown:teiHeader[@xml:id='TR00423.hdr']")));
        assertTrue(testSingleSelect(doc, buildBrownXPath("//brown:teiHeader[@xml:id='TR00378.hdr']")));
    }

    @Test(expected=Exception.class)
    public void testNotXML() throws Exception {
        String badSource = utils.loadFromResources("notxml.xml");
        b.buildCorpusDoc(new String[] {badSource});
    }

    private XPath buildBrownXPath(String xPathString) throws Exception {
        XPath xPath = XPath.newInstance(xPathString);
        xPath.addNamespace(brownNS);
        return xPath;
    }

    private boolean testSingleSelect(Document doc, XPath xpath) throws Exception {
        List nodes = xpath.selectNodes(doc);
        return nodes.size() == 1;
    }
}

