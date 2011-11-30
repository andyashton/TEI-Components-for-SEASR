package edu.brown.seasr.teicorpuscomponent;

import edu.brown.seasr.SEASRComponentTest;
import org.jmock.Expectations;
import org.junit.Test;
import org.meandre.core.ComponentContext;
import org.meandre.core.ExecutableComponent;
import org.seasr.datatypes.core.BasicDataTypes;

import static org.hamcrest.Matchers.containsString;

@SuppressWarnings("unchecked")
public class TEICorpusComponentTest extends SEASRComponentTest {

    class MockCorpusBuilder extends TEICorpusBuilder {
        @Override
        public String buildCorpusDoc(String[] teiDocs) throws Exception {
            return join(teiDocs);
        }
    }

    public String join(String[] strings) {
        
        StringBuilder sb = new StringBuilder();

        for (String s: strings) {
            sb.append(s);
            sb.append("|");
        }
        
        sb.deleteCharAt(sb.length() - 1);
        
        return sb.toString();
    }
    
    @Override
    public ExecutableComponent getComponent() {
        TEICorpusComponent c = new TEICorpusComponent();
        c.setCorpusBuilder(new MockCorpusBuilder());
        return c;
    }

    @Test
    public void testComponent() throws Exception {
        
        BasicDataTypes.Strings.Builder b = BasicDataTypes.Strings.newBuilder();
        b.addValue("himom");
        b.addValue("hidad");

        inputs.put(TEICorpusComponent.IN_XML, b.build());

        final ComponentContext cc = utils.getComponentContext();
        Expectations expectations = new Expectations() {{
            oneOf(cc).pushDataComponentToOutput(with(TEICorpusComponent.OUT_XML), with(
                    containsString("himom|hidad")
            ));
        }};

        test(expectations);
    }
}

