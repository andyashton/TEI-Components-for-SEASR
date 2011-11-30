package edu.brown.seasr.placenameextractorcomponent;

import edu.brown.seasr.ComponentXMLUtils;
import edu.brown.seasr.SEASRTestUtils;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;

import javax.xml.bind.SchemaOutputResolver;
import java.util.Set;

import static org.junit.Assert.assertTrue;

/**
 * User: mdellabitta
 * Date: 2011-05-24
 * Time: 8:44 AM
 */
public class PlaceNameExtractorTest {

    PlaceNameExtractor pne;
    SEASRTestUtils testUtils;
    ComponentXMLUtils xmlUtils;

    @Before
    public void setUp() throws Exception {
        pne = new PlaceNameExtractor();
        testUtils = new SEASRTestUtils();
        xmlUtils = new ComponentXMLUtils();
    }

    @Test
    public void testExtract() throws Exception {
        String xml = testUtils.loadFromResources("bacon.sermons.tei.xml");
        Set<String> placeNames = pne.extractPlaceNames(xml);

        assertTrue(placeNames.contains("hea u en"));
        assertTrue(placeNames.contains("Sena"));
        assertTrue(placeNames.size() == 2);
    }

    @Test
    public void testRelative() throws Exception {
        String xml = testUtils.loadFromResources("relative.placename.tei.xml");
        Set<String> placeNames = pne.extractPlaceNames(xml);
        assertTrue(placeNames.size() == 0);
    }

    @Test
    public void test() throws Exception {
        String xml = testUtils.loadFromResources("augustine.virgin.tei.xml");

        Set<String> placeNames = pne.extractPlaceNames(xml);
        for (String name: placeNames) {
            System.out.println(name);
        }


    }
}
