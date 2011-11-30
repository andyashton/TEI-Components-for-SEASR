package edu.brown.seasr.xpathfindandreplacecomponent;

import edu.brown.seasr.SEASRComponentTest;
import edu.brown.seasr.xpathcomponent.XPathComponent;
import edu.brown.seasr.xpathcomponent.XPathProcessor;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;
import org.meandre.core.ComponentContext;
import org.meandre.core.ExecutableComponent;

import static edu.brown.seasr.xpathfindandreplacecomponent.XPathFindAndReplaceComponent.*;

import static edu.brown.seasr.AbstractXMLStreamComponent.IN_XML;
import static org.hamcrest.Matchers.containsString;

/**
 * User: mdellabitta
 * Date: 2011-04-08
 * Time: 5:22 PM
 */
public class XPathFindAndReplaceComponentTest extends SEASRComponentTest {

    @Override
    protected ExecutableComponent getComponent() {
        return new XPathFindAndReplaceComponent();
    }

    @Test
    public void testProcess() throws Exception {
        final String findXpath = "this is xpath";
        final String replaceXpath = "this is also xpath";
        final String xml = "this is xml";

        inputs.put(IN_FIND_XPATH, findXpath);
        inputs.put(IN_REPLACE_XPATH, replaceXpath);
        inputs.put(IN_XML, xml);

        Mockery context = utils.getMockery();
        final ComponentContext cc = utils.getComponentContext();
        final XPathFindAndReplace x = context.mock(XPathFindAndReplace.class);

        setComponentReconfigurator(new Runnable() {
            public void run() {
                ((XPathFindAndReplaceComponent)c).setProcessor(x);
            }
        });

        final String result = "hi mom";

        Expectations expectations = new Expectations() {{
            oneOf(x).process(xml, findXpath, replaceXpath);
            will(returnValue(result));
            oneOf(cc).pushDataComponentToOutput(
                    with(OUT_XML),
                    with(containsString(result))
            );
        }};

        test(expectations);
    }
}

