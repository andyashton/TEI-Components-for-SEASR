package edu.brown.seasr.similejsoncomponent;

import edu.brown.seasr.AbstractXMLStreamComponent;
import edu.brown.seasr.QueueBlock;
import edu.brown.seasr.geocodingcomponent.Geocoder;
import edu.brown.seasr.placenameextractorcomponent.PlaceNameExtractor;
import edu.brown.seasr.xpathcomponent.XPathProcessor;
import org.meandre.annotations.Component;
import org.meandre.annotations.ComponentProperty;
import org.meandre.core.ComponentContext;
import org.meandre.core.ComponentContextProperties;

import java.util.Queue;
import java.util.Set;

/**
 * User: mdellabitta
 * Date: 2011-06-09
 * Time: 6:25 PM
 */

@Component(
        creator = "Michael Della Bitta",
        description = "This component takes a stream of TEI XML documents and writes a JSON file for Simile to the filesystem containing geocode data.",
        name = "Simile JSON Writer",
        tags = "geocoding latitude longitude place map simile json tei",
        dependency = {"protobuf-java-2.2.0.jar", "json-lib-2.2.1-jdk15.jar"},
        baseURL = "meandre://brown.edu/seasr/tei/components/",
        firingPolicy = Component.FiringPolicy.any,
        mode = Component.Mode.compute,
        rights = Component.Licenses.Other)

public class SimileJSONComponent extends AbstractXMLStreamComponent {

    @ComponentProperty(
            description = "The path to write the file to.",
            defaultValue = "/dev/null",
            name = "path"
    )

    protected static final String PROP_PATH = "path";

    @ComponentProperty(
            description = "Color of pins on map",
            defaultValue = "blue",
            name = "pin color"
    )

    protected static final String PROP_COLOR = "color";

    public void setWriter(SimileMapJSONWriter writer) {
        this.writer = writer;
    }

    private SimileMapJSONWriter writer = new SimileMapJSONWriter();
    private String path = null;
    private String color = null;

    @Override
    protected void processSidechainInputs(ComponentContext cc) throws Exception {
        //do nothing
    }

    @Override
    protected void processQueued(ComponentContext cc, Queue<String> queue) {
        foreachDoc(new QueueBlock() {
            public void process(String xml) throws Exception {
                writer.processTEI(xml, color, path);
            }
        });
    }

    @Override
    protected boolean isSidechainReady() {
        return true;
    }

    @Override
    public void initializeCallBack(ComponentContextProperties ccp) throws Exception {
        super.initializeCallBack(ccp);
        path = ccp.getProperty(PROP_PATH);
        color = ccp.getProperty(PROP_COLOR);
    }

}