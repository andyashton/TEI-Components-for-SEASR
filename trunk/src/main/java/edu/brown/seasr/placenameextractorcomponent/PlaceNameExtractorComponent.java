package edu.brown.seasr.placenameextractorcomponent;

import edu.brown.seasr.AbstractXMLStreamComponent;
import edu.brown.seasr.ComponentXMLUtils;
import edu.brown.seasr.QueueBlock;
import org.meandre.annotations.Component;
import org.meandre.annotations.ComponentOutput;
import org.meandre.core.ComponentContext;
import org.seasr.datatypes.core.Names;
import org.w3c.dom.Document;

import java.util.Queue;
import java.util.Set;

/**
 * User: mdellabitta
 * Date: 2011-05-23
 * Time: 8:25 AM
 */

@Component(
        creator = "Michael Della Bitta",
        description = "This component extracts the names of places it finds in TEI documents it reads.",
        name = "Place Name Extractor",
        tags = "xml, tei, place",
        dependency = {"protobuf-java-2.2.0.jar", "saxon9he.jar"},
        baseURL = "meandre://brown.edu/seasr/tei/components/",
        firingPolicy = Component.FiringPolicy.all,
        mode = Component.Mode.compute,
        rights = Component.Licenses.Other)

public class PlaceNameExtractorComponent extends AbstractXMLStreamComponent {

    @ComponentOutput(description =
            "The list of place names extracted.",
            name = Names.PORT_TEXT)
    public final static String OUT_TEXT = Names.PORT_TEXT;

    private PlaceNameExtractor extractor = new PlaceNameExtractor();

    public void setPlaceNameExtractor(PlaceNameExtractor extractor) {
        this.extractor = extractor;
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
        return new String[] {OUT_TEXT};
    }

    @Override
    protected void processQueued(final ComponentContext cc, Queue<String> queue) {
        foreachDoc(new QueueBlock() {
            @Override
            public void process(String xml) throws Exception {
                console.finest(xml);
                Set<String> places = extractor.extractPlaceNames(xml);
                for (String place: places) {
                    cc.pushDataComponentToOutput(OUT_TEXT, place);
                }
            }
        });
    }
}
