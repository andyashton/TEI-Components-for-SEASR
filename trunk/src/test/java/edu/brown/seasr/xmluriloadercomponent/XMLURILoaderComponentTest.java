package edu.brown.seasr.xmluriloadercomponent;

import edu.brown.seasr.SEASRComponentTest;
import org.jmock.Expectations;
import org.junit.Test;
import org.meandre.core.ComponentContext;
import org.meandre.core.ExecutableComponent;
import org.seasr.datatypes.core.BasicDataTypes;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URI;

import static edu.brown.seasr.xmluriloadercomponent.XMLURILoaderComponent.IN_LIST;
import static edu.brown.seasr.xmluriloadercomponent.XMLURILoaderComponent.OUT_DOCS;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;

public class XMLURILoaderComponentTest extends SEASRComponentTest {

    @Override
    public ExecutableComponent getComponent() {
        return new XMLURILoaderComponent();
    }
    
    @Test
    public void testReality() throws Exception {
        String href = "availability.xml";
        File docLocation = new File(baseDir, "bacon.sermons.tei.xml");
        URI docURI = docLocation.toURI();
        URI hrefURI = docURI.resolve(href);
        assertEquals("URI resolution failed.", new File(baseDir, href).toURI().toString(), hrefURI.toString());        
    }

    @SuppressWarnings("unchecked")
    @Test
    public void test() throws Exception { 

        final File[] docFiles = baseDir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".tei.xml");
            }
        });

        BasicDataTypes.Strings.Builder builder = BasicDataTypes.Strings.newBuilder();

        for (File docFile : docFiles) {
            builder.addValue(docFile.toURI().toString());
        }


        inputs.put(IN_LIST, builder.build());

        final ComponentContext cc = utils.getComponentContext();
        Expectations expectations = new Expectations() {
            {
                for (final File docfile : docFiles) {
                    oneOf(cc).pushDataComponentToOutput(
                            with(OUT_DOCS),
                            with(allOf(containsString("<?brown-seasr"), containsString("location="),
                                    containsString(docfile.toURI().toString()))));
                }
            }

        };

        test(expectations);
    }

}
