package edu.brown.seasr.filesystemenumeratorcomponent;

import edu.brown.seasr.SEASRTestUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * User: mdellabitta
 * Date: 2011-03-23
 * Time: 5:09 PM
 */
public class FilesystemEnumeratorTest {

    SEASRTestUtils utils;
    FilesystemEnumerator fe;
    File resourcesDir;

    @Before
    public void setUp() throws Exception {
        fe = new FilesystemEnumerator();
        utils = new SEASRTestUtils();
        resourcesDir = utils.getResourcesDir();
    }

    @Test
    public void testShallowNoRegexp() throws Exception {
        List<String> uriList = Arrays.asList(fe.getURIs(resourcesDir.getAbsolutePath(), null, false));

        for (File file: resourcesDir.listFiles()) {
            if (!file.isHidden() && file.isFile()) {
                assertTrue("Fail for " + file.getAbsolutePath(), uriList.contains(file.toURI().toString()));
            }
        }
    }

    @Test
    public void testShallowRegexp() throws Exception {
        String filter = ".xml$";

        List<String> uriList = Arrays.asList(fe.getURIs(resourcesDir.getAbsolutePath(), filter, false));

        for (File file: resourcesDir.listFiles(new FilenameFilter() {
            public boolean accept(File file, String s) {
                return s.endsWith(".xml");
            }
        })) {
            if (!file.isHidden() && file.isFile()) {
                assertTrue("Fail for " + file.getAbsolutePath(), uriList.contains(file.toURI().toString()));
            }
        }
    }

    @Test
    public void testDeepNoRegexp() throws Exception {
        List<String> uriList = Arrays.asList(fe.getURIs(resourcesDir.getAbsolutePath(), null, true));
        assertTrue(uriList.contains(new File(utils.getResourcesDir(), "address-book.xml").toURI().toString()));
        assertTrue(uriList.contains(new File(utils.getResourcesDir(), "texts/anger.protection.xml").toURI().toString()));
    }

    @Test
    public void testDeepRegexp() throws Exception {
        List<String> uriList = Arrays.asList(fe.getURIs(resourcesDir.getAbsolutePath(), ".xml$", true));
        assertTrue("Missing address-book.xml", uriList.contains(new File(utils.getResourcesDir(), "address-book.xml").toURI().toString()));
        assertTrue("Missing anger.protection.xml", uriList.contains(new File(utils.getResourcesDir(), "texts/anger.protection.xml").toURI().toString()));
        assertFalse("Contains address-book.rng", uriList.contains(new File(utils.getResourcesDir(), "schema/address-book.rng").toURI().toString()));
    }
}
