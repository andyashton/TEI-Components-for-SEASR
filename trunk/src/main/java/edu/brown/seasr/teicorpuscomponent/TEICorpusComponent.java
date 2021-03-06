package edu.brown.seasr.teicorpuscomponent;

import java.io.StringReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Queue;
import java.util.TimeZone;
import java.util.logging.Level;

import edu.brown.seasr.AbstractXMLStreamComponent;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.meandre.annotations.Component;
import org.meandre.annotations.Component.FiringPolicy;
import org.meandre.annotations.Component.Licenses;
import org.meandre.annotations.Component.Mode;
import org.meandre.annotations.ComponentInput;
import org.meandre.annotations.ComponentOutput;
import org.meandre.core.ComponentContext;
import org.meandre.core.ComponentContextProperties;
import org.seasr.datatypes.core.DataTypeParser;
import org.seasr.datatypes.core.Names;
import org.seasr.meandre.components.abstracts.AbstractExecutableComponent;

@Component(
        creator = "Michael Della Bitta",
        description = "This component takes a group of TEI documents and bundles them together into one big document.",
        name = "TEI Corpus Builder",
        tags = "TEI teiCorpus corpus combine glob concatenate",
        dependency = {"protobuf-java-2.2.0.jar", "jdom.jar", "saxon9he.jar"},
        baseURL = "meandre://brown.edu/seasr/tei/components/",
        firingPolicy = FiringPolicy.any,
        mode = Mode.compute,
        rights = Licenses.Other)
public class TEICorpusComponent extends AbstractXMLStreamComponent {

    @ComponentOutput(description = "The combined XML document.", name = Names.PORT_XML)
    final static String OUT_XML = Names.PORT_XML;

    private TEICorpusBuilder builder = new TEICorpusBuilder();

    public void setCorpusBuilder(TEICorpusBuilder builder) {
        this.builder = builder;
    }

    @Override
    protected boolean isSidechainReady() {
        return true;
    }

    @Override
    protected void processSidechainInputs(ComponentContext cc) throws Exception {
        //nothing
    }

    @Override
    protected String[] getStreamOutputPorts() {
        return new String[] {OUT_XML};
    }

    @SuppressWarnings({"serial", "ConstantConditions"})
    @Override
    protected void processQueued(ComponentContext cc, Queue<String> queue) {

        try {
            String[] documents = queue.toArray(new String[queue.size()]);
            String corpusDocString = builder.buildCorpusDoc(documents);
            cc.pushDataComponentToOutput(OUT_XML, corpusDocString);
            queue.clear();

        } catch (Exception e) {
            outputError(e, Level.SEVERE);
        }
    }
}

