package edu.brown.seasr.tagstripper;

import edu.brown.seasr.SEASRTestUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

/**
 * User: mdellabitta
 * Date: 2011-04-06
 * Time: 6:17 PM
 */
public class TagStripperTest {

    SEASRTestUtils utils;
    TagStripper stripper;

    @Before
    public void setUp() throws Exception {
        utils = new SEASRTestUtils();
        stripper = new TagStripper();
    }

    @Test
    public void testSimple() throws Exception {
        String result = stripper.stripTags(utils.loadFromResources("address-book.xml"));
        System.out.println(result);
        assertFalse(result.contains("<"));
        assertFalse(result.contains(">"));
        assertFalse(result.contains("addressBook"));
    }

    @Test
    public void testWithBadDTD() throws Exception {
        String xml = utils.loadFromResources("baddtd/taylor.essays.xml");
        String result = stripper.stripTags(xml);
        System.out.println(result);
        //just have to make sure this doesn't throw, no asserts needed.
    }
}
