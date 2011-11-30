package edu.brown.seasr.xpathfiltercomponent;

import edu.brown.seasr.SEASRComponentTest;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;
import org.meandre.core.ComponentContext;
import org.meandre.core.ExecutableComponent;

import static edu.brown.seasr.xpathfiltercomponent.XPathFilterComponent.*;
import static org.hamcrest.Matchers.containsString;

/**
 * User: mdellabitta
 * Date: 2011-03-10
 * Time: 8:12 AM
 */
public class XPathFilterComponentTest extends SEASRComponentTest {

    @Override
    protected ExecutableComponent getComponent() {
        return new XPathFilterComponent();
    }

    @Test
    public void testFilter() throws Exception {
        final String xpath = "this is xpath";
        final String xml = "this is xml";

        inputs.put(IN_XPATH, xpath);
        inputs.put(IN_XML, xml);

        Mockery context = utils.getMockery();
        final ComponentContext cc = utils.getComponentContext();
        final XPathFilter f = context.mock(XPathFilter.class);
        
        setComponentReconfigurator(new Runnable() {
            public void run() {
                ((XPathFilterComponent)c).setFilter(f);
            }
        });

        Expectations expectations = new Expectations() {{
            oneOf(f).hasXPath(xpath, xml);
            will(returnValue(false));
            oneOf(cc).pushDataComponentToOutput(
                    with(OUT_FALSE),
                    with(containsString(xml))
            );
        }};

        test(expectations);
    }
}
