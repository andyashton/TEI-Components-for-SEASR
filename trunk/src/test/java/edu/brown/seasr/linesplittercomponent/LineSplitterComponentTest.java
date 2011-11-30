package edu.brown.seasr.linesplittercomponent;

import edu.brown.seasr.SEASRComponentTest;
import org.jmock.Expectations;
import org.junit.Test;
import org.meandre.core.ComponentContext;
import org.meandre.core.ComponentContextProperties;
import org.meandre.core.ExecutableComponent;
import org.seasr.datatypes.core.BasicDataTypes;
import org.seasr.datatypes.core.BasicDataTypesTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static edu.brown.seasr.linesplittercomponent.LineSplitterComponent.IN_LIST;
import static edu.brown.seasr.linesplittercomponent.LineSplitterComponent.OUT_STRINGS;
import static edu.brown.seasr.linesplittercomponent.LineSplitterComponent.PROP_DELIMITER;
import static org.junit.Assert.assertEquals;

/**
 * User: mdellabitta
 * Date: 2011-03-07
 * Time: 8:47 AM
 */
public class LineSplitterComponentTest extends SEASRComponentTest {

    class MockLineSplitter extends LineSplitter {

        private List<String> receivedData = new ArrayList<String>();

        public List<String> getReceivedData() {
            return receivedData;
        }

        private String[] responseData = new String[]{"eat", "at", "joe's"};

        public String[] getResponseData() {
            return responseData;
        }

        public MockLineSplitter(String delimiter) {
            super(delimiter);
        }

        @Override
        public String[] split(String inString) {
            receivedData.add(inString);
            return responseData;
        }
    }

    final MockLineSplitter ls = new MockLineSplitter("doesn't matter");

    @Override
    protected ExecutableComponent getComponent() {
        return new LineSplitterComponent();
    }

    @Test
    public void test() throws Exception {
        inputs.put(IN_LIST, "hey how's it going?");
        properties.put(PROP_DELIMITER, "\n");

        setComponentReconfigurator(new Runnable() {
            public void run() {
                ((LineSplitterComponent)c).setLS(ls);
            }
        });

        final BasicDataTypes.Strings strings = BasicDataTypesTools.stringToStrings(ls.getResponseData());
        final ComponentContext cc = utils.getComponentContext();
        
        Expectations expectations = new Expectations() {{
            oneOf(cc).pushDataComponentToOutput(with(OUT_STRINGS), with(equal(strings)));
        }};

        test(expectations);
        assertEquals(ls.getReceivedData().get(0), inputs.get(IN_LIST));
    }
}
