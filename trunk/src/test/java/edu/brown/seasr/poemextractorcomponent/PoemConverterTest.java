package edu.brown.seasr.poemextractorcomponent;

import edu.brown.seasr.SEASRTestUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: mdellabitta
 * Date: 2011-10-18
 * Time: 9:04 AM
 */
public class PoemConverterTest {

    private SEASRTestUtils testUtils;
    private PoemConverter poemConverter;


    @Before
    public void setUp() throws Exception {
        testUtils = new SEASRTestUtils();
        poemConverter = new PoemConverter();
    }

    @Test
        public void testXPath() throws Exception {
        String xmlString = testUtils.loadFromResources("yearsley.poems.xml");
        String outputString = poemConverter.convert(xmlString);
        System.out.println(outputString);
    }
}
