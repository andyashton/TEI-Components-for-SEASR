package edu.brown.seasr.xpathcomponent;

import edu.brown.seasr.SEASRTestUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * User: mdellabitta
 * Date: 2011-02-25
 * Time: 8:48 AM
 */
public class XPathProcessorTest {

    SEASRTestUtils utils;
    XPathProcessor p;

    @Before
    public void setUp() throws Exception {
        utils = new SEASRTestUtils();
        p = new XPathProcessor();
    }

    private String run(String file, String xPath) throws Exception {
        String xml = utils.loadFromResources(file);
        return p.executeXPath(xml, xPath);
    }

    @Test
    public void simpleStringTest() throws Exception {
        String result = run("address-book.xml", "/addressBook/card[1]/name/text()");
        utils.checkXPaths(result, null, "/results[text() = 'John Smith']");
    }

    @Test
    public void simpleNodeTest() throws Exception {
        String result = run("address-book.xml", "/addressBook/card[1]");
        utils.checkXPaths(result, null, "/card/name[text() = 'John Smith']");
    }

    @Test
    public void testFailingXPath() throws Exception {
        String result = run("address-book.xml", "/foo");
        assertTrue(result == null);
    }
}
