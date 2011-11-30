package edu.brown.seasr.xmltojsonconvertercomponent;

import edu.brown.seasr.SEASRComponentTest;
import org.jmock.Expectations;
import org.junit.Test;
import org.meandre.core.ComponentContext;
import org.meandre.core.ExecutableComponent;

import static edu.brown.seasr.xmltojsonconvertercomponent.XMLToJSONConverterComponent.*;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;

/**
 * User: mdellabitta
 * Date: 2011-02-18
 * Time: 8:50 AM
 */
public class XMLToJSONConverterComponentTest extends SEASRComponentTest {

    class MockConverter extends XMLToJSONConverter {
        @Override
        public String convert(String xml) {
            return "hi mom";
        }
    }

    @Override
    protected ExecutableComponent getComponent() {
        XMLToJSONConverterComponent c = new XMLToJSONConverterComponent();
        c.setConverter(new MockConverter());
        return c;
    }

    @Test
    public void test() throws Exception {
        inputs.put(IN_XML, "hi dad");

        final ComponentContext cc = utils.getComponentContext();
        Expectations expectations = new Expectations() {{
            oneOf(cc).pushDataComponentToOutput(
                    with(OUT_JSON),
                    with(containsString("hi mom"))
            );
        }};

        test(expectations);

    }
}
