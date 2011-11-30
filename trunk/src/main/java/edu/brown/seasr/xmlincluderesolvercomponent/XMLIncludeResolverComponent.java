package edu.brown.seasr.xmlincluderesolvercomponent;

import edu.brown.seasr.AbstractXMLStreamComponent;
import edu.brown.seasr.QueueBlock;
import org.meandre.annotations.Component;
import org.meandre.annotations.Component.FiringPolicy;
import org.meandre.annotations.Component.Licenses;
import org.meandre.annotations.Component.Mode;
import org.meandre.annotations.ComponentInput;
import org.meandre.annotations.ComponentOutput;
import org.meandre.core.ComponentContext;
import org.meandre.core.ComponentContextProperties;
import org.meandre.core.ComponentExecutionException;
import org.seasr.datatypes.core.DataTypeParser;
import org.seasr.datatypes.core.Names;
import org.seasr.meandre.components.abstracts.AbstractExecutableComponent;

import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;


@SuppressWarnings({"WeakerAccess"})
@Component(
        creator = "Michael Della Bitta",
        description = "This component sets the xml:base attribute for the root node of the document to the parent of the URI specified in the brown-seasr processing instruction from XMLURILoader and then processes the xml includes.",
        name = "XML Include Resolver",
        tags = "xml, xinclude, xi",
        baseURL = "meandre://brown.edu/seasr/tei/components/",
        dependency = {"protobuf-java-2.2.0.jar", "jdom.jar", "saxon9he.jar"},
        firingPolicy = FiringPolicy.any,
        mode = Mode.compute,
        rights = Licenses.Other)

public class XMLIncludeResolverComponent extends AbstractXMLStreamComponent {

    @ComponentOutput(description = "XML output", name = Names.PORT_XML)
    public final static String OUT_XML = Names.PORT_XML;

    @Override
    protected String[] getStreamOutputPorts() {
        return new String[] {OUT_XML};
    }

    private Resolver resolver = new Resolver();

    public void setResolver(Resolver resolver) {
        this.resolver = resolver;
    }

    @SuppressWarnings({"ConstantConditions"})
    protected void processQueued(final ComponentContext cc, Queue<String> queue) {
        console.fine(String.format("Processing %d queued XML documents...", queue.size()));

        foreachDoc(new QueueBlock() {
            public void process(String xml) throws Exception {
                String resolvedDoc = resolver.resolve(xml);
                console.finest(resolvedDoc);
                cc.pushDataComponentToOutput(OUT_XML, resolvedDoc);
            }
        });
    }

    @Override
    protected void processSidechainInputs(ComponentContext cc) throws Exception {
        //do nothing
    }

    @Override
    protected boolean isSidechainReady() {
        return true;
    }
}
