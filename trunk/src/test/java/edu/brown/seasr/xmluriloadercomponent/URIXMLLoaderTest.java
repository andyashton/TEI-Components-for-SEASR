package edu.brown.seasr.xmluriloadercomponent;

import edu.brown.seasr.SEASRTestUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;

/**
 * User: mdellabitta
 * Date: 2010-12-31
 * Time: 4:03 PM
 */
public class URIXMLLoaderTest {

    URIXMLLoader loader;
    SEASRTestUtils utils;


    @Before
    public void setup() {
        loader = new URIXMLLoader();
        utils = new SEASRTestUtils();
    }


    @Test
    public void testLocalDoc() throws Exception {
        File baconSermons = new File(utils.getResourcesDir(), "bacon.sermons.tei.xml");
        String docstring = loader.getDocString(baconSermons.toURI().toString());
    }

    //These aren't really unit tests. Hmm...

    @Ignore
    public void testRemoteDoc() throws Exception {
        String docstring = loader.getDocString("http://www.brown.edu/rss");
    }

    @Ignore
    public void testRemoteDocWithDTD() throws Exception {
        String docstring = loader.getDocString("http://www.gutenberg.org/files/26495/26495-tei/26495-tei.tei");
    }

}
