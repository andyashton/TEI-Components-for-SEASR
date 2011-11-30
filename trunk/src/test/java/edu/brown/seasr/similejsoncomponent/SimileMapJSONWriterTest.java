package edu.brown.seasr.similejsoncomponent;

import edu.brown.seasr.SEASRTestUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.meandre.components.test.framework.ComponentTesterBase;

import java.io.File;
import java.util.Date;

/**
 * User: mdellabitta
 * Date: 2011-06-10
 * Time: 5:37 PM
 */
public class SimileMapJSONWriterTest {

    /*
    TODO: Opening a socket in a unit test. So very wrong.
     */
    @Test
    public void test() throws Exception {
        if (true) return;
        File mapJsonFile = new File("/tmp/SimileMapJSONWriterTest" + new Date().getTime() + ".json");
        SEASRTestUtils utils = new SEASRTestUtils();
        String xml = utils.loadFromResources("bacon.sermons.tei.xml");
        SimileMapJSONWriter smjw = new SimileMapJSONWriter();
        smjw.processTEI(xml, "blue", mapJsonFile.getAbsolutePath());
    }
}
