package edu.brown.seasr.tagstripper;

import edu.brown.seasr.AbstractXMLStreamComponent;
import edu.brown.seasr.QueueBlock;
import org.meandre.annotations.Component;
import org.meandre.annotations.ComponentOutput;
import org.meandre.core.ComponentContext;
import org.seasr.datatypes.core.Names;

import java.util.Queue;

/**
 * User: mdellabitta
 * Date: 2011-04-07
 * Time: 8:14 AM
 */

@Component(
        creator = "Michael Della Bitta",
        description = "This component strips the tags out of an XML file and returns the text nodes.",
        name = "Tag Stripper",
        tags = "xml document transform text",
        dependency = { "protobuf-java-2.2.0.jar", "saxon9he.jar"},
        baseURL = "meandre://brown.edu/seasr/tei/components/",
        firingPolicy = Component.FiringPolicy.any,
        mode = Component.Mode.compute,
        rights = Component.Licenses.Other)

public class TagStripperComponent extends AbstractXMLStreamComponent {

    @ComponentOutput(description = "The text of the documents.", name = Names.PORT_TEXT)
    final static String OUT_TEXT = Names.PORT_TEXT;


    private TagStripper tagStripper = new TagStripper();

    public void setTagStripper(TagStripper tagStripper) {
        this.tagStripper = tagStripper;
    }

    @Override
    protected String[] getStreamOutputPorts() {
        return new String[] { OUT_TEXT };
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
    protected void processQueued(final ComponentContext cc, Queue<String> queue) {
        foreachDoc(new QueueBlock() {
            @Override
            public void process(String xml) throws Exception {
                console.finest("XML: " + xml);
                console.finest("Tag stripper:" + tagStripper.getClass());
                String result = tagStripper.stripTags(xml);
                if (result == null) {
                    console.finest("No result.");
                } else {
                    console.finest(result);
                    cc.pushDataComponentToOutput(OUT_TEXT, result);
                }
            }
        });
    }
}
