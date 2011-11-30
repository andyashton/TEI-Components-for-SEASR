package edu.brown.seasr.xmluriloadercomponent;

import org.meandre.annotations.Component;
import org.meandre.annotations.Component.FiringPolicy;
import org.meandre.annotations.Component.Licenses;
import org.meandre.annotations.Component.Mode;
import org.meandre.annotations.ComponentInput;
import org.meandre.annotations.ComponentOutput;
import org.meandre.core.ComponentContext;
import org.meandre.core.ComponentContextProperties;
import org.meandre.core.ComponentExecutionException;
import org.meandre.core.system.components.ext.StreamInitiator;
import org.meandre.core.system.components.ext.StreamTerminator;
import org.seasr.datatypes.core.DataTypeParser;
import org.seasr.datatypes.core.Names;
import org.seasr.meandre.components.abstracts.AbstractExecutableComponent;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.logging.Level;

import static org.apache.commons.io.IOUtils.readLines;
import static org.apache.commons.io.IOUtils.toInputStream;


@SuppressWarnings( { "WeakerAccess" })
@Component(
        creator = "Michael Della Bitta",
        description = "This component loads a series of XML documents from a list "
                + "of URIs that may indicate locations on a filesystem or a "
                + "HTTP service. Documents will be annotated with a processing "
                + "instruction indicating where they were retrieved from.",
        name = "XML URI Loader",
        tags = "xml, uri",
        dependency = { "protobuf-java-2.2.0.jar", "jaxen.jar", "jdom.jar", "saxpath.jar", "saxon9he.jar" },
        baseURL = "meandre://brown.edu/seasr/tei/components/",
        firingPolicy = FiringPolicy.all,
        mode = Mode.compute,
        rights = Licenses.Other)
public class XMLURILoaderComponent extends AbstractExecutableComponent {

    @ComponentInput(description = "The list of URIs." + "<br>TYPE: java.lang.String"
            + "<br>TYPE: org.seasr.datatypes.BasicDataTypes.Strings", name = Names.PORT_TEXT)
    final static String IN_LIST = Names.PORT_TEXT;

    @ComponentOutput(description = "Document stream", name = Names.PORT_XML)
    final static String OUT_DOCS = Names.PORT_XML;

    @Override
    public void initializeCallBack(ComponentContextProperties ccp) throws Exception {

    }

    @Override
    public void executeCallBack(ComponentContext cc) throws Exception {

        String[] urls = DataTypeParser.parseAsString(cc.getDataComponentFromInput(IN_LIST));
        System.err.println(urls.length);

        try {
            URIXMLLoader loader = new URIXMLLoader();

            for (String url : urls) {
                try {
                    @SuppressWarnings("unchecked")
                    List<String> lines = readLines(toInputStream(url));

                    for (String line : lines) {

                        try {

                            String doc = loader.getDocString(line);
                            console.finest(doc);
                            cc.pushDataComponentToOutput(OUT_DOCS, doc);

                        } catch (Exception e) {
                            outputError(e, Level.WARNING);
                        }
                    }

                } catch (Exception e) {
                    outputError(e, Level.WARNING);
                }
            }

        } catch (Exception e) {
            console.log(Level.SEVERE, e.getMessage());
            throw new ComponentExecutionException(e);
        }
    }


    @Override
    public void disposeCallBack(ComponentContextProperties ccp) throws Exception {

    }

    @Override
    protected void handleStreamInitiators() throws Exception {
        console.entering(getClass().getName(), "handleStreamInitiators", inputPortsWithInitiators);

        if (inputPortsWithInitiators.contains(IN_LIST)) {
            console.fine("Forwarding " + StreamInitiator.class.getSimpleName() + " to the next component...");
            componentContext.pushDataComponentToOutput(OUT_DOCS, componentContext.getDataComponentFromInput(IN_LIST));
        }

        console.exiting(getClass().getName(), "handleStreamInitiators");
    }

    @Override
    protected void handleStreamTerminators() throws Exception {
        console.entering(getClass().getName(), "handleStreamTerminators", inputPortsWithTerminators);

        if (inputPortsWithTerminators.contains(IN_LIST)) {
            console.fine("Forwarding " + StreamTerminator.class.getSimpleName() + " to the next component...");
            componentContext.pushDataComponentToOutput(OUT_DOCS, componentContext.getDataComponentFromInput(IN_LIST));
        }

        console.exiting(getClass().getName(), "handleStreamTerminators");
    }
}
