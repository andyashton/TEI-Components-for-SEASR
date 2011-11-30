package edu.brown.seasr.diplomaticcomponent;

import edu.brown.seasr.SEASRTestUtils;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * User: mdellabitta
 * Date: 2011-06-14
 * Time: 9:20 AM
 */
public class DiplomatTest {

    @Test
    public void test() throws Exception {
        SEASRTestUtils utils = new SEASRTestUtils();
        String undiplomatic = utils.loadFromResources("undiplomatic.xml");
        DiplomaticConverter diplomat = new DiplomaticConverter();
        String diplomatic = diplomat.convert(undiplomatic);
        assertTrue(!diplomatic.contains("undiplomatic"));
    }
}
