package edu.brown.seasr.linesofverseextractorcomponent;

import edu.brown.seasr.SEASRTestUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: mdellabitta
 * Date: 2011-10-18
 * Time: 6:08 PM
 */
public class LinesOfVerseConverterTest {

    SEASRTestUtils testUtils;
    LinesOfVerseConverter linesOfVerseConverter;

    @Before
    public void setUp() throws Exception {
        testUtils = new SEASRTestUtils();
        linesOfVerseConverter = new LinesOfVerseConverter();
    }

    @Test
    public void test() throws Exception {
        String xmlString = testUtils.loadFromResources("yearsley.poems.xml");
        String outputString = linesOfVerseConverter.convert(xmlString);
        System.out.println(outputString);
    }
}
