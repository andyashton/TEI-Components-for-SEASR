package edu.brown.seasr.xpathcomponent;

import edu.brown.seasr.AbstractXMLStreamComponent;
import edu.brown.seasr.QueueBlock;
import org.meandre.annotations.Component;
import org.meandre.annotations.ComponentInput;
import org.meandre.annotations.ComponentOutput;
import org.meandre.core.ComponentContext;
import org.seasr.datatypes.core.DataTypeParser;
import org.seasr.datatypes.core.Names;

import java.util.Queue;

/**
 * User: mdellabitta
 * Date: 2011-04-06
 * Time: 8:30 AM
 */
@Component(
        creator = "Michael Della Bitta",
        description = "This component processes an incoming stream of XML documents against an XPath string.",
        name = "XPath Processor",
        tags = "xpath xml document transform",
        dependency = { "protobuf-java-2.2.0.jar", "saxon9he.jar" },
        baseURL = "meandre://brown.edu/seasr/tei/components/",
        firingPolicy = Component.FiringPolicy.any,
        mode = Component.Mode.compute,
        rights = Component.Licenses.Other)

public class XPathComponent extends AbstractXMLStreamComponent {

    @ComponentInput(name = "xpath", description = "The XPath to process documents against.")
    final static String IN_XPATH = "xpath";

    @ComponentOutput(description = "The documents that match the XPath.", name = Names.PORT_XML)
    final static String OUT_XML = Names.PORT_XML;

    @Override
    protected String[] getStreamOutputPorts() {
        return new String[] {OUT_XML};
    }
   
    private XPathProcessor processor = new XPathProcessor();
    private String xPathString;

    public void setProcessor(XPathProcessor processor) {
        this.processor = processor;
    }

    @Override
    protected boolean isSidechainReady() {
        return xPathString != null;
    }

    @Override
    protected void processSidechainInputs(ComponentContext cc) throws Exception {
        if (cc.isInputAvailable(IN_XPATH)) {
            xPathString = DataTypeParser.parseAsString(cc.getDataComponentFromInput(IN_XPATH))[0];
            console.finest("XPath: " + xPathString);
        }
    }

    @Override
    protected void processQueued(final ComponentContext cc, Queue<String> queue) {
        foreachDoc(new QueueBlock() {
            @Override
            public void process(String xml) throws Exception {
                console.finest("XML: " + xml);
                console.finest("processor:" + processor.getClass());
                String result = processor.executeXPath(xml, xPathString);
                if (result == null) {
                    console.finest("No result.");
                } else {
                    console.finest(result);
                    cc.pushDataComponentToOutput(OUT_XML, result);
                }
            }
        });
    }
}
