package edu.brown.seasr;

import org.jdom.input.SAXBuilder;
import org.jdom.output.DOMOutputter;
import org.jdom.xpath.XPath;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.meandre.core.*;
import org.xml.sax.InputSource;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class SEASRTestUtils {

    private File baseDir = new File(System.getProperty("user.dir"), "src/test/resources");
    private Mockery context;
    private ComponentContextProperties ccp;
    private ComponentContext cc;

    private ComponentXMLUtils u;

    {
        context = new Mockery() {{
            setImposteriser(ClassImposteriser.INSTANCE);
        }};
        ccp = context.mock(ComponentContextProperties.class);
        cc = context.mock(ComponentContext.class);
        u = new ComponentXMLUtils();
    }

    public Mockery getMockery() {
        return context;
    }

    public final ComponentContext getComponentContext() {
        return cc;
    }

    public final ComponentContextProperties getComponentContextProperties() {
        return ccp;
    }

    public File getResourcesDir() {
        return baseDir;
    }

    public String loadString(File file) throws Exception {
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        byte[] data = new byte[(int) file.length()];
        in.readFully(data);
        return new String(data, Charset.forName("UTF8"));
    }

    public String loadFromResources(String name) throws Exception {
        File resourceFile = new File(getResourcesDir(), name);
        return loadString(resourceFile);
    }

    public void initializeComponent(ExecutableComponent c, final Map<String, String> properties) throws ComponentContextException, ComponentExecutionException {
        context.checking(new Expectations() {{

            if (properties != null) {
                for (String propName : properties.keySet()) {
                    allowing(ccp).getProperty(with(propName));
                    will(returnValue(properties.get(propName)));
                }
            }

            allowing(ccp).getExecutionInstanceID();
            will(returnValue(UUID.randomUUID().toString()));
            allowing(ccp).getFlowExecutionInstanceID();
            will(returnValue(UUID.randomUUID().toString()));
            allowing(ccp).getFlowID();
            will(returnValue(UUID.randomUUID().toString()));
            allowing(ccp).getLogger();
            will(returnValue(Logger.getLogger("jmock")));
            allowing(ccp).getOutputConsole();
            will(returnValue(System.out));
            allowing(ccp).getProperty("debug_level");
            will(returnValue("all"));
            allowing(ccp).getProperty("ignore_errors");
            will(returnValue("false"));
            allowing(ccp).getProperty("wrap_stream");
            will(returnValue("false"));
            allowing(ccp).getInputNames();
            allowing(ccp).getOutputNames();
        }});

        c.initialize(ccp);
    }

    public void initializeComponent(ExecutableComponent c) throws ComponentExecutionException, ComponentContextException {
        initializeComponent(c, null);
    }

    public void prepareInputs(final Map<String, Object> inputs) throws ComponentContextException {
        context.checking(new Expectations() {{
            for (String input : inputs.keySet()) {
                allowing(cc).isInputAvailable(input);
                will(returnValue(true));
                allowing(cc).getDataComponentFromInput(input);
                will(returnValue(inputs.get(input)));
            }
        }});
    }

    public String documentToString(org.jdom.Document doc) throws Exception {
        DOMOutputter domOutputter = new DOMOutputter();
        return u.docToString(domOutputter.output(doc));
    }

    public void checkXPaths(String xml, Map<String, String> namespaces, String... paths) throws Exception {
        SAXBuilder sb = new SAXBuilder();
        org.jdom.Document doc = sb.build(new InputSource(new StringReader(xml)));
        if (namespaces == null) namespaces = new HashMap<String, String>();

        for (String path : paths) {
            XPath xpath = XPath.newInstance(path);
            for (Map.Entry<String, String> entry : namespaces.entrySet()) {
                xpath.addNamespace(entry.getKey(), entry.getValue());
            }

            List results = xpath.selectNodes(doc);
            assertTrue(results.size() > 0);
        }
    }
}
