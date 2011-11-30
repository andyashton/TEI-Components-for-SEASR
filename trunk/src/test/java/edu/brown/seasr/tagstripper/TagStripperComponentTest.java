package edu.brown.seasr.tagstripper;

import edu.brown.seasr.SEASRComponentTest;
import edu.brown.seasr.xpathcomponent.XPathComponent;
import edu.brown.seasr.xpathcomponent.XPathProcessor;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;
import org.meandre.core.ComponentContext;
import org.meandre.core.ExecutableComponent;

import static edu.brown.seasr.AbstractXMLStreamComponent.IN_XML;
import static edu.brown.seasr.tagstripper.TagStripperComponent.OUT_TEXT;
import static org.hamcrest.Matchers.containsString;

/**
 * User: mdellabitta
 * Date: 2011-04-07
 * Time: 8:25 AM
 */
public class TagStripperComponentTest extends SEASRComponentTest {

        @Override
        protected ExecutableComponent getComponent() {
            return new TagStripperComponent();
        }

        @Test
        public void testProcess() throws Exception {
            final String xml = "this is xml";

            inputs.put(IN_XML, xml);

            Mockery context = utils.getMockery();
            final ComponentContext cc = utils.getComponentContext();
            final TagStripper ts = context.mock(TagStripper.class);

            setComponentReconfigurator(new Runnable() {
                public void run() {
                    ((TagStripperComponent)c).setTagStripper(ts);
                }
            });

            final String result = "hi mom";

            Expectations expectations = new Expectations() {{
                oneOf(ts).stripTags(xml);
                will(returnValue(result));
                oneOf(cc).pushDataComponentToOutput(
                        with(OUT_TEXT),
                        with(containsString(result))
                );
            }};

            test(expectations);
        }
    }

