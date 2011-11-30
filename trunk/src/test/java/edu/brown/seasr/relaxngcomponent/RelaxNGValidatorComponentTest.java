package edu.brown.seasr.relaxngcomponent;

import static edu.brown.seasr.relaxngcomponent.RelaxNGValidatorComponent.IN_RELAX_NG_SCHEMA;
import static edu.brown.seasr.relaxngcomponent.RelaxNGValidatorComponent.IN_XML;

import edu.brown.seasr.SEASRComponentTest;
import org.jmock.Expectations;
import org.junit.Test;

import org.meandre.core.*;

public class RelaxNGValidatorComponentTest extends SEASRComponentTest {

    @Override
    public ExecutableComponent getComponent() {
        RelaxNGValidatorComponent c = new RelaxNGValidatorComponent();
        c.setRelaxNGValidator(new MockRelaxNGValidator());

        return c;
    }

    @Test
    public void testValid() throws Exception {
        inputs.put(IN_XML, "xml");
        inputs.put(IN_RELAX_NG_SCHEMA, "true");

        final ComponentContext cc = utils.getComponentContext();
        Expectations expectations = new Expectations() {
            {
                oneOf(cc).pushDataComponentToOutput(with(RelaxNGValidatorComponent.OUT_VALID), with(true));
            }
        };

        test(expectations);
    }

    @Test
    public void testInvalid() throws Exception {
        inputs.put(IN_XML, "xml");
        inputs.put(IN_RELAX_NG_SCHEMA, "false");

        final ComponentContext cc = utils.getComponentContext();

        Expectations expectations = new Expectations() {
            {
                oneOf(cc).pushDataComponentToOutput(with(RelaxNGValidatorComponent.OUT_VALID), with(false));
            }
        };

        test(expectations);
    }
}
