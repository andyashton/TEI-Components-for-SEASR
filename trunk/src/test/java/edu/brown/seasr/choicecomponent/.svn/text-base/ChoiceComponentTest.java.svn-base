package edu.brown.seasr.choicecomponent;

import edu.brown.seasr.ComponentXMLUtils;
import edu.brown.seasr.SEASRComponentTest;

import static edu.brown.seasr.choicecomponent.ChoiceComponent.*;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;
import org.meandre.core.ComponentContext;
import org.meandre.core.ExecutableComponent;
import org.w3c.dom.Document;

import static edu.brown.seasr.AbstractXMLStreamComponent.IN_XML;
import static edu.brown.seasr.choicecomponent.ChoiceComponent.OUT_XML;
import static org.hamcrest.Matchers.containsString;

/**
 * User: mdellabitta
 * Date: 2011-04-27
 * Time: 8:25 AM
 */
public class ChoiceComponentTest extends SEASRComponentTest {

    @Override
    protected ExecutableComponent getComponent() {
        return new ChoiceComponent();
    }

    @Test
    public void test() throws Exception {
        final String xml = "this is xml";
        final String result = "hi mom";
        final String choice = "corr";
        inputs.put(IN_XML, xml);
        properties.put(PROP_CHOICES, choice);

        Mockery context = utils.getMockery();
        final ComponentContext cc = utils.getComponentContext();
        final ComponentXMLUtils cxu = context.mock(ComponentXMLUtils.class);
        final Document doc = context.mock(Document.class, "initialDoc");
        final Document returnDoc = context.mock(Document.class, "returnDoc");
        setComponentReconfigurator(new Runnable() {
            public void run() {
                ((ChoiceComponent) c).setXmlUtils(cxu);
            }
        });

        Expectations expectations = new Expectations() {{
            oneOf(cxu).stringToDocument(xml);
            will(returnValue(doc));
            oneOf(cxu).choose(doc, choice);
            will(returnValue(returnDoc));
            oneOf(cxu).docToString(returnDoc);
            will(returnValue(result));
            oneOf(cc).pushDataComponentToOutput(
                    with(OUT_XML),
                    with(containsString(result))
            );
        }};

        test(expectations);
    }
}
