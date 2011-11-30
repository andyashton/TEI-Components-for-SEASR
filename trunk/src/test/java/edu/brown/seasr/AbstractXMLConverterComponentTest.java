package edu.brown.seasr;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;
import org.meandre.core.ComponentContext;
import org.meandre.core.ComponentContextProperties;
import org.seasr.datatypes.core.Names;

import java.util.Arrays;
import java.util.Queue;
import java.util.UUID;

import static org.junit.Assert.assertTrue;

/**
 * Created by IntelliJ IDEA.
 * User: mdellabitta
 * Date: 2011-10-11
 * Time: 8:59 AM
 */
public class AbstractXMLConverterComponentTest {

    private class TestConverter extends Converter {

        private String outputString;

        protected TestConverter(String outputString) {
            this.outputString = outputString;
        }

        @Override
        public String convert(String doc) throws Exception {
            return outputString;
        }
    }

    private class NullOutputtingConverter extends TestConverter {

        private NullOutputtingConverter(String outputString) {
            super(null);
        }

    }

    private AbstractXMLConverterComponent<TestConverter> component;
    private TestConverter converter;
    private String outputString;
    private ComponentContext cc;
    private Mockery context;

    @Before
    public void setUp() throws Exception {
        context = new Mockery();
        cc = context.mock(ComponentContext.class);

        UUID uuid = UUID.randomUUID();
        outputString = uuid.toString();
        converter = new TestConverter(outputString);
        final TestConverter c = converter;
        component = new AbstractXMLConverterComponent<TestConverter>() {
            @Override
            protected TestConverter getConverter() {
                return this.converter;
            }
        };

        component.setConverter(converter);
    }

    @Test
    public void testIsSideChainReady() throws Exception {
        assertTrue(component.isSidechainReady());
    }

    @Test
    public void testGetConverter() throws Exception {
        assertTrue(component.getConverter() == converter);
    }

    @Test
    public void testSetConverter() throws Exception {
        TestConverter testConverter = new TestConverter("testconverter");
        component.setConverter(testConverter);
        assertTrue(component.getConverter() == testConverter);
    }

    @Test
    public void testGetOutputPorts() throws Exception {
        assertTrue(Arrays.deepEquals(component.getStreamOutputPorts(), new String[]{AbstractXMLConverterComponent.OUT_XML}));
    }

    @Test
    public void testProcessSidechainInputs() throws Exception {
        component.processSidechainInputs(cc);
    }

    @Test
    public void testOutputPort() throws Exception {
        assertTrue(AbstractXMLConverterComponent.OUT_XML.equals(Names.PORT_XML));
    }

    @Test
    public void testProcessQueued() throws Exception {

        ComponentContextProperties ccp = context.mock(ComponentContextProperties.class);
        component.initializeCallBack(ccp);
        Queue<String> queue = component.getQueue();
        queue.add("hi");
        queue.add("mom");
        
        context.checking(new Expectations() {{
            allowing(cc).pushDataComponentToOutput(Names.PORT_XML, outputString);
            allowing(cc).pushDataComponentToOutput(Names.PORT_XML, outputString);
        }});

        component.processQueued(cc, queue);
        context.assertIsSatisfied();
    }

    @Test
    public void testNullOutput() throws Exception {
        ComponentContextProperties ccp = context.mock(ComponentContextProperties.class);
        component.initializeCallBack(ccp);
        component.setConverter(new NullOutputtingConverter("himom"));
        Queue<String> queue = component.getQueue();
        queue.add("hi");
        queue.add("mom");

        context.checking(new Expectations() {{
            never(cc).pushDataComponentToOutput(Names.PORT_XML, any(String.class));
        }});

        component.processQueued(cc, queue);
        context.assertIsSatisfied();

    }
}
