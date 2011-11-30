package edu.brown.seasr.xpathfiltercomponent;

import edu.brown.seasr.SEASRTestUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * User: mdellabitta
 * Date: 2011-03-10
 * Time: 8:12 AM
 */
public class XPathFilterTest {

    private XPathFilter xPathFilter;
    private SEASRTestUtils utils;

    @Before
    public void setUp() throws Exception {
        xPathFilter = new XPathFilter();
        utils = new SEASRTestUtils();
    }

    @Test
    public void testBrownRSS() throws Exception {
        String xml = utils.loadFromResources("brown-rss.xml");

        truePaths(xml, "/rss");
    }

    @Test
    public void testStrawmanFail() throws Exception {
        truePaths("<rss>himom</rss>", "/rss");
    }

    @Test
    public void testDocWithNamespaces() throws Exception {
        String xml = utils.loadFromResources("bacon.sermons.tei.xml");

        truePaths(xml,
                "/_default:TEI",
                "/_default:TEI/_default:teiHeader[@xml:id='TR00484.hdr']",
                "/_default:TEI/_default:teiHeader[@xml:id='TR00484.hdr']/_default:fileDesc/_default:titleStmt/_default:title[@type='main']"
        );

        falsePaths(xml, "/TEI", "/addressBook");
    }

    @Test
    public void testFilterSimpleDoc() throws Exception {

        String xml = utils.loadFromResources("address-book.xml");

        truePaths(xml,
                "//addressBook",
                "/addressBook",
                "/addressBook/card/name",
                "//text()",
                "//email[text() = 'fb@example.net']");

        falsePaths(xml, "//foo", "/foo", "//card[@hi]");
    }

    void truePaths(String xml, String... xPaths) throws Exception {
        for (String xpath : xPaths) {
            assertTrue(xpath + " didn't match.", xPathFilter.hasXPath(xpath, xml));
        }
    }

    void falsePaths(String xml, String... xPaths) throws Exception {
        for (String xpath : xPaths) {
            assertFalse(xpath + " matched.", xPathFilter.hasXPath(xpath, xml));
        }
    }
}
