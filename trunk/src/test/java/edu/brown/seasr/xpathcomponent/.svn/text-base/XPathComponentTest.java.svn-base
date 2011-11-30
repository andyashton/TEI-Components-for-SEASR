package edu.brown.seasr.xpathcomponent;

import edu.brown.seasr.SEASRComponentTest;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;
import org.meandre.core.ComponentContext;
import org.meandre.core.ExecutableComponent;

import static edu.brown.seasr.AbstractXMLStreamComponent.IN_XML;
import static edu.brown.seasr.xpathcomponent.XPathComponent.IN_XPATH;
import static edu.brown.seasr.xpathcomponent.XPathComponent.OUT_XML;
import static org.hamcrest.Matchers.containsString;

/**
 * User: mdellabitta
 * Date: 2011-04-06
 * Time: 8:45 AM
 */
public class XPathComponentTest extends SEASRComponentTest {

    @Override
    protected ExecutableComponent getComponent() {
        return new XPathComponent();
    }

    @Test
    public void testProcess() throws Exception {
        final String xpath = "this is xpath";
        final String xml = "this is xml";

        inputs.put(IN_XPATH, xpath);
        inputs.put(IN_XML, xml);

        Mockery context = utils.getMockery();
        final ComponentContext cc = utils.getComponentContext();
        final XPathProcessor p = context.mock(XPathProcessor.class);

        setComponentReconfigurator(new Runnable() {
            public void run() {
                ((XPathComponent)c).setProcessor(p);
            }
        });

        final String result = "hi mom";

        Expectations expectations = new Expectations() {{
            oneOf(p).executeXPath(xml, xpath);
            will(returnValue(result));
            oneOf(cc).pushDataComponentToOutput(
                    with(OUT_XML),
                    with(containsString(result))
            );
        }};

        test(expectations);
    }
}

