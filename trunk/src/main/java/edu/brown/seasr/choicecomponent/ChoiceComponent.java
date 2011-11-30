package edu.brown.seasr.choicecomponent;

import edu.brown.seasr.AbstractXMLStreamComponent;
import edu.brown.seasr.ComponentXMLUtils;
import edu.brown.seasr.QueueBlock;
import org.meandre.annotations.Component;
import org.meandre.annotations.ComponentInput;
import org.meandre.annotations.ComponentOutput;
import org.meandre.annotations.ComponentProperty;
import org.meandre.core.ComponentContext;
import org.meandre.core.ComponentContextException;
import org.meandre.core.ComponentContextProperties;
import org.meandre.core.ComponentExecutionException;
import org.seasr.datatypes.core.Names;
import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

/**
 * User: mdellabitta
 * Date: 2011-04-22
 * Time: 5:51 PM
 */

@Component(
        creator = "Michael Della Bitta",
        description = "This component renders into the document one of the children of the <tei:choice> element depending on configuration.",
        name = "Choice Converter",
        tags = "xml, tei, choice",
        dependency = {"protobuf-java-2.2.0.jar", "saxon9he.jar"},
        baseURL = "meandre://brown.edu/seasr/tei/components/",
        firingPolicy = Component.FiringPolicy.any,
        mode = Component.Mode.compute,
        rights = Component.Licenses.Other)
public class ChoiceComponent extends AbstractXMLStreamComponent {

    public static final List<String> availableChoices = Arrays.asList("sic", "corr", "abbr", "expan", "orig", "reg", "am", "ex");

    private List<String> choices;

    @ComponentOutput(description =
            "The transformed XML document.",
            name = Names.PORT_XML)
    
    public final static String OUT_XML = Names.PORT_XML;

    @ComponentProperty(name = "choices", description = "The names of tags below <choice> tags to prefer. Will ignore non-specified choices. Separate with commas.", defaultValue = "sic, orig, abbr, am")
    public final static String PROP_CHOICES = "choices";

    private ComponentXMLUtils xmlUtils = new ComponentXMLUtils();


    public void setXmlUtils(ComponentXMLUtils xmlUtils) {
        this.xmlUtils = xmlUtils;
    }

    @Override
    protected boolean isSidechainReady() {
        return true;
    }

    @Override
    protected void processSidechainInputs(ComponentContext cc) throws Exception {
        //do nothing
    }

    @Override
    protected String[] getStreamOutputPorts() {
        return new String[] {OUT_XML};
    }


    @Override
    protected void processQueued(final ComponentContext cc, Queue<String> queue) {


        foreachDoc(new QueueBlock() {
            @Override
            public void process(String xml) throws Exception {
                Document doc = xmlUtils.stringToDocument(xml);
                for (String choice: choices) {
                    doc = xmlUtils.choose(doc, choice);
                }
                String newXML = xmlUtils.docToString(doc);
                console.finest("Pushing: " + newXML);
                cc.pushDataComponentToOutput(OUT_XML, newXML);
            }
        });
    }

    @Override
    public void initializeCallBack(ComponentContextProperties ccp) throws Exception {
        super.initializeCallBack(ccp);
        String choiceProp = ccp.getProperty(PROP_CHOICES);
        if (choiceProp == null || "".equals(choiceProp)) return;
        choices = new ArrayList<String>(Arrays.asList(choiceProp.replaceAll(" ", "").split(",")));
        choices.retainAll(availableChoices);
        console.info("Resolving choices for: ");
        for (String choice: choices) {
            console.info(choice);
        }
    }
}
