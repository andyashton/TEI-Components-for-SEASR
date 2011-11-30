package edu.brown.seasr.xmltojsonconvertercomponent;

import edu.brown.seasr.SEASRTestUtils;
import net.sf.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * User: mdellabitta
 * Date: 2011-02-17
 * Time: 6:20 PM
 */
public class XMLToJSONConverterTest {

    private SEASRTestUtils utils;
    private XMLToJSONConverter converter;

    @Before
    public void setup() {
        utils = new SEASRTestUtils();
        converter = new XMLToJSONConverter();
    }

    @Test
    public void simpleTest() throws Exception {
        String xml = utils.loadFromResources("xml-base-test.xml");
        String json = converter.convert(xml);
        JSONObject jo = parseJSON(json);
        assertTrue(jo.containsKey("@xmlns:xi"));
        assertTrue(jo.containsKey("@xmlns:xlink"));
        assertTrue(jo.containsKey("google"));
        assertTrue(jo.containsKey("file"));
    }

    @Test
    public void testTEI() throws Exception {
        String xml = utils.loadFromResources("bacon.sermons.tei.xml");
        parseJSON(converter.convert(xml));
    }

    @Test(expected = net.sf.json.JSONException.class)
    public void testParse() {
        //just making sure this throws if you feed it something that doesn't make sense
        System.out.println(parseJSON(""));
    }

    private JSONObject parseJSON(String jsonString) {
        return JSONObject.fromObject(jsonString);
    }
}
