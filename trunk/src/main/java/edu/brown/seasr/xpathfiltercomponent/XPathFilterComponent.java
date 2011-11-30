package edu.brown.seasr.xpathfiltercomponent;

import edu.brown.seasr.AbstractXMLStreamComponent;
import edu.brown.seasr.QueueBlock;
import edu.brown.seasr.xmltojsonconvertercomponent.XMLToJSONConverter;
import org.meandre.annotations.Component;
import org.meandre.annotations.ComponentInput;
import org.meandre.annotations.ComponentOutput;
import org.meandre.core.ComponentContext;
import org.seasr.datatypes.core.BasicDataTypesTools;
import org.seasr.datatypes.core.DataTypeParser;
import org.seasr.datatypes.core.Names;

import java.util.Queue;

/**
 * User: mdellabitta
 * Date: 2011-03-10
 * Time: 8:22 AM
 */

@Component(
        creator = "Michael Della Bitta",
        description = "This component filters an incoming stream of XML documents against an XPath string.",
        name = "XPath Filter",
        tags = "filter xpath xml document",
        dependency = { "protobuf-java-2.2.0.jar", "saxon9he.jar" },
        baseURL = "meandre://brown.edu/seasr/tei/components/",
        firingPolicy = Component.FiringPolicy.any,
        mode = Component.Mode.compute,
        rights = Component.Licenses.Other)

public class XPathFilterComponent extends AbstractXMLStreamComponent {

    @ComponentInput(name = "xpath", description = "The XPath to check documents against.")
    final static String IN_XPATH = "xpath";

    @ComponentOutput(description = "The documents that match the XPath.", name = "true")
    final static String OUT_TRUE = "true";

    @ComponentOutput(description = "The documents that don't match the XPath.", name = "false")
    final static String OUT_FALSE = "false";

    @Override
    protected String[] getStreamOutputPorts() {
        return new String[] {OUT_TRUE, OUT_FALSE};
    }

    private XPathFilter filter = new XPathFilter();
    private String xPathString;

    public void setFilter(XPathFilter filter) {
        this.filter = filter;
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
                String outputName = filter.hasXPath(xPathString, xml) ? OUT_TRUE : OUT_FALSE;
                cc.pushDataComponentToOutput(outputName, xml);
            }
        });
    }
}
