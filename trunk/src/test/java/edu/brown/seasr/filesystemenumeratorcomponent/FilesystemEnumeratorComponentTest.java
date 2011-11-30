package edu.brown.seasr.filesystemenumeratorcomponent;

import edu.brown.seasr.SEASRComponentTest;
import static org.hamcrest.Matchers.*;
import org.jmock.Expectations;
import org.junit.Test;
import org.meandre.core.ComponentContext;
import org.meandre.core.ExecutableComponent;
import org.seasr.datatypes.core.BasicDataTypesTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static edu.brown.seasr.filesystemenumeratorcomponent.FilesystemEnumeratorComponent.*;
import static org.junit.Assert.assertTrue;

/**
 * User: mdellabitta
 * Date: 2011-03-24
 * Time: 9:13 AM
 */
public class FilesystemEnumeratorComponentTest extends SEASRComponentTest {

    final MockFilesystemEnumerator filesystemEnumerator = new MockFilesystemEnumerator();

    @Override
    protected ExecutableComponent getComponent() {
        return new FilesystemEnumeratorComponent() {
            @Override
            public FilesystemEnumerator getFilesystemEnumerator() {
                return filesystemEnumerator;
            }
        };
    }

    @Test
    public void testConfig() throws Exception {


        List<Map<String, String>> testData = new ArrayList<Map<String, String>>();
        
        testData.add(new HashMap<String, String>() {{
            put(PROP_REGEXP, "");
            put(PROP_DIR, "/test1");
            put(PROP_RECURSE, "true");
        }});
        testData.add(new HashMap<String, String>() {{
            put(PROP_REGEXP, "");
            put(PROP_DIR, "/test2");
            put(PROP_RECURSE, "false");
        }});
        testData.add(new HashMap<String, String>() {{
            put(PROP_REGEXP, ".xml$");
            put(PROP_DIR, "/test3");
            put(PROP_RECURSE, "true");
        }});
        testData.add(new HashMap<String, String>() {{
            put(PROP_REGEXP, ".xml$");
            put(PROP_DIR, "/test4");
            put(PROP_RECURSE, "false");
        }});

        for (final Map<String, String> testMap: testData) {
            reset();
            final ComponentContext cc = utils.getComponentContext();
            properties.putAll(testMap);

            Expectations expectations = new Expectations() {{
                oneOf(cc).pushDataComponentToOutput(with(equalTo("uris")), with(equalTo(BasicDataTypesTools.stringToStrings("http://www.lmgtfy.com"))));
            }};
            
            test(expectations);

            if (!(testMap.get(PROP_REGEXP) == null || "".equals(testMap.get(PROP_REGEXP)))) {
                assertTrue("Bad regexp. Received " + filesystemEnumerator.regexp + ", expected " + testMap.get(PROP_REGEXP), testMap.get(PROP_REGEXP).equals(filesystemEnumerator.regexp));
            }

            assertTrue("Bad dir: " + filesystemEnumerator.dirstring + " != " + testMap.get(PROP_DIR), testMap.get(PROP_DIR).equals(filesystemEnumerator.dirstring));
            assertTrue("Bad recurse: " + filesystemEnumerator.recurse + " != " + testMap.get(PROP_RECURSE), Boolean.parseBoolean(testMap.get(PROP_RECURSE)) == filesystemEnumerator.recurse);
        }
    }

    class MockFilesystemEnumerator extends FilesystemEnumerator {

        public String dirstring;
        public String regexp;
        public boolean recurse;

        public String[] getURIs(String parentDirString, String regexp, boolean recurse) throws Exception {
            this.dirstring = parentDirString;
            this.regexp = regexp;
            this.recurse = recurse;
            return new String[] { "http://www.lmgtfy.com" };
        }
    }
}
