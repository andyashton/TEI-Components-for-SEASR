package edu.brown.seasr.authorsfirstintentcomponent;

import edu.brown.seasr.AbstractXMLStreamComponent;
import edu.brown.seasr.QueueBlock;
import org.meandre.annotations.Component;
import org.meandre.annotations.ComponentOutput;
import org.meandre.core.ComponentContext;
import org.seasr.datatypes.core.Names;

import java.util.Queue;

/**
 * User: mdellabitta
 * Date: 2011-08-24
 * Time: 7:36 PM
 */

@Component(
        creator = "Michael Della Bitta",
        description = "This component renders an author's intent view of the TEI document.",
        name = "Author's First Intent Converter",
        tags = "xml tei",
        dependency = {"protobuf-java-2.2.0.jar", "saxon9he.jar"},
        baseURL = "meandre://brown.edu/seasr/tei/components/",
        firingPolicy = Component.FiringPolicy.any,
        mode = Component.Mode.compute,
        rights = Component.Licenses.Other)


public class AuthorsFirstIntentComponent extends AbstractXMLStreamComponent {

    @ComponentOutput(description = "Author's Intent Result", name = Names.PORT_XML)
    final static String OUT_XML = Names.PORT_XML;

    public void setAuthorialTextConverter(AuthorsFirstIntentConverter authorsFirstIntentConverter) {
        this.authorsFirstIntentConverter = authorsFirstIntentConverter;
    }

    private AuthorsFirstIntentConverter authorsFirstIntentConverter = new AuthorsFirstIntentConverter();

    @Override
    protected void processSidechainInputs(ComponentContext cc) throws Exception {
        //do nothing
    }

    @Override
    protected void processQueued(final ComponentContext cc, Queue<String> queue) {
        foreachDoc(new QueueBlock() {
            @Override
            public void process(String xml) throws Exception {
                String authorsFirstIntent = authorsFirstIntentConverter.convert(xml);
                cc.pushDataComponentToOutput(OUT_XML, authorsFirstIntent);
            }
        });
    }

    @Override
    protected boolean isSidechainReady() {
        return true;
    }

    @Override
    protected String[] getStreamOutputPorts() {
        return new String[] {OUT_XML};
    }
}


