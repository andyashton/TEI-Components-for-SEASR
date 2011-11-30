package edu.brown.seasr.relaxngcomponent;

import edu.brown.seasr.SEASRTestUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * User: mdellabitta
 * Date: 2011-01-05
 * Time: 8:20 AM
 */
public class RelaxNGValidatorTest {

    SEASRTestUtils utils;
    RelaxNGValidator v;

    @Before
    public void setUp() throws Exception {
        utils = new SEASRTestUtils();
        v = new RelaxNGValidator();
    }

    @Test
    public void jingCommandLineTest() throws IOException, InterruptedException {
        Process proc = Runtime
                .getRuntime()
                .exec("java -jar lib/jing.jar src/test/resources/schema/wwp-store.rng src/test/resources/texts/anger.protection.xml");
        assertEquals(0, proc.waitFor());
    }

    @Test
    public void testLoadGoodSchema() throws Exception {
        String schema = utils.loadFromResources("schema/address-book.rng");
        v.setSchema(schema);
    }

    @Test(expected = java.lang.Exception.class)
    public void testLoadBadSchema() throws Exception {
        String schema = utils.loadFromResources("availability.xml");
        v.setSchema(schema);
    }

    @Test
    public void testValidateGoodDoc() throws Exception {
        assertTrue(validate(utils.loadFromResources("address-book.xml")));
    }

    @Test
    public void testValidateBadDoc() throws Exception {
        assertFalse(validate(utils.loadFromResources("availability.xml")));
    }

    @Test(expected = org.xml.sax.SAXParseException.class)
    public void testValidateMalformedDoc() throws Exception {
        validate("Hi, mom!");
    }

    boolean validate(String xml) throws Exception {
        String schema = utils.loadFromResources("schema/address-book.rng");
        v.setSchema(schema);

        return v.validateDoc(xml);
    }
}
