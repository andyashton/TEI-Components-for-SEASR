package edu.brown.seasr.relaxngfiltercomponent;

import static edu.brown.seasr.relaxngfiltercomponent.RelaxNGFilterComponent.IN_RELAX_NG_SCHEMA;
import static edu.brown.seasr.relaxngfiltercomponent.RelaxNGFilterComponent.IN_XML;
import static edu.brown.seasr.relaxngfiltercomponent.RelaxNGFilterComponent.OUT_INVALID;
import static edu.brown.seasr.relaxngfiltercomponent.RelaxNGFilterComponent.OUT_VALID;
import static org.junit.Assert.assertEquals;

import edu.brown.seasr.SEASRComponentTest;
import edu.brown.seasr.relaxngcomponent.MockRelaxNGValidator;
import org.jmock.Expectations;
import org.junit.Test;
import org.meandre.core.*;

public class RelaxNGFilterComponentTest extends SEASRComponentTest {

    @Override
    public ExecutableComponent getComponent() {
        RelaxNGFilterComponent c = new RelaxNGFilterComponent();
        c.setValidator(new MockRelaxNGValidator());

        return c;
    }

    @Test
    public void testValid() throws Exception {
        inputs.put(IN_XML, "xml");
        inputs.put(IN_RELAX_NG_SCHEMA, "true");

        final ComponentContext cc = utils.getComponentContext();

        Expectations expectations = new Expectations() {{
            oneOf(cc).pushDataComponentToOutput(with(OUT_VALID), with("xml"));
        }};

        test(expectations);
    }

    @Test
    public void testInvalid() throws Exception {
        final String output = "xml";

        inputs.put(IN_XML, output);
        inputs.put(IN_RELAX_NG_SCHEMA, "false");

        final ComponentContext cc = utils.getComponentContext();

        Expectations expectations = new Expectations() {{
            oneOf(cc).pushDataComponentToOutput(with(OUT_INVALID), with(output));
        }};

        test(expectations);
    }
}
