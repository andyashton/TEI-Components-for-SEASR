package edu.brown.seasr.placenameextractorcomponent;

import edu.brown.seasr.SEASRComponentTest;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;
import org.meandre.core.ComponentContext;
import org.meandre.core.ExecutableComponent;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static edu.brown.seasr.AbstractXMLStreamComponent.IN_XML;
import static edu.brown.seasr.placenameextractorcomponent.PlaceNameExtractorComponent.OUT_TEXT;
import static org.hamcrest.Matchers.containsString;

/**
 * User: mdellabitta
 * Date: 2011-05-26
 * Time: 8:18 AM
 */
public class PlaceNameExtractorComponentTest extends SEASRComponentTest {

    @Override
    protected ExecutableComponent getComponent() {
        return new PlaceNameExtractorComponent();
    }

    @Test
    public void test() throws Exception {
        final String xml = "this is xml";
        final Set<String> results = new HashSet<String>(Arrays.asList("hi", "mom"));

        inputs.put(IN_XML, xml);

        Mockery context = utils.getMockery();
        final ComponentContext cc = utils.getComponentContext();
        final PlaceNameExtractor placeNameExtractor = context.mock(PlaceNameExtractor.class);
        setComponentReconfigurator(new Runnable() {
            public void run() {
                ((PlaceNameExtractorComponent) c).setPlaceNameExtractor(placeNameExtractor);
            }
        });

        Expectations expectations = new Expectations() {{
            oneOf(placeNameExtractor).extractPlaceNames(xml);
            will(returnValue(results));
            for (String result : results) {
                oneOf(cc).pushDataComponentToOutput(
                        with(OUT_TEXT),
                        with(containsString(result))
                );
            }
        }};

        test(expectations);
    }

}
