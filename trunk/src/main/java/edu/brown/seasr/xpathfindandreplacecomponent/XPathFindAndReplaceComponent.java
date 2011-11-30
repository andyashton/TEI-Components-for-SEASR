package edu.brown.seasr.xpathfindandreplacecomponent;

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
 * Date: 2011-04-08
 * Time: 5:12 PM
 */

@Component(
        creator = "Michael Della Bitta",
        description = "This component transforms incoming documents according to two parameters: " +
                "an XPath expression selecting nodes to find and an XPath expression selecting nodes " +
                "to replace them with from the context of the found node.",
        name = "XPath Find And Replace",
        tags = "find replace xpath xml document transform",
        dependency = { "protobuf-java-2.2.0.jar", "saxon9he.jar" },
        baseURL = "meandre://brown.edu/seasr/tei/components/",
        firingPolicy = Component.FiringPolicy.any,
        mode = Component.Mode.compute,
        rights = Component.Licenses.Other)

public class XPathFindAndReplaceComponent extends AbstractXMLStreamComponent {

    @ComponentOutput(description = "The transformed documents.", name = Names.PORT_XML)
    final static String OUT_XML = Names.PORT_XML;

    @ComponentInput(name = "find_xpath", description = "The XPath to find nodes.")
    final static String IN_FIND_XPATH = "find_xpath";

    @ComponentInput(name = "replace_xpath", description = "The XPath to the replacement nodes given the found node as the context.")
    final static String IN_REPLACE_XPATH = "replace_xpath";

    private XPathFindAndReplace processor = new XPathFindAndReplace();
    private String findXPathString;
    private String replaceXPathString;

    public void setProcessor(XPathFindAndReplace processor) {
        this.processor = processor;
    }

    @Override
    protected boolean isSidechainReady() {
        return findXPathString != null && replaceXPathString != null;
    }

    @Override
    protected String[] getStreamOutputPorts() {
        return new String[] {OUT_XML};
    }

    @Override
    protected void processSidechainInputs(ComponentContext cc) throws Exception {
        if (cc.isInputAvailable(IN_FIND_XPATH)) {
            findXPathString = DataTypeParser.parseAsString(cc.getDataComponentFromInput(IN_FIND_XPATH))[0];
            console.finest("Find XPath: " + findXPathString);
        }

        if (cc.isInputAvailable(IN_REPLACE_XPATH)) {
            replaceXPathString = DataTypeParser.parseAsString(cc.getDataComponentFromInput(IN_REPLACE_XPATH))[0];
            console.finest("Replace XPath: " + replaceXPathString);
        }
    }

    @Override
    protected void processQueued(final ComponentContext cc, Queue<String> queue) {
        foreachDoc(new QueueBlock() {
            @Override
            public void process(String xml) throws Exception {
                console.finest("XML: " + xml);
                String outputDoc = processor.process(xml, findXPathString, replaceXPathString);
                cc.pushDataComponentToOutput(OUT_XML, outputDoc);
            }
        });
    }
}
