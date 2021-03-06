package edu.brown.seasr.xslt2component;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;

import javax.xml.transform.stream.StreamSource;

import edu.brown.seasr.AbstractXMLStreamComponent;
import edu.brown.seasr.QueueBlock;
import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.Serializer;
import net.sf.saxon.s9api.XdmNode;
import net.sf.saxon.s9api.XsltCompiler;
import net.sf.saxon.s9api.XsltExecutable;
import net.sf.saxon.s9api.XsltTransformer;

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

/**
 * This component uses Saxon Home Edition to provide XSLT 2.0 transformations.
 *
 * @author Michael Della Bitta
 */

@SuppressWarnings({"WeakerAccess"})
@Component(
        creator = "Michael Della Bitta",
        description = "This component uses Saxon Home Edition to provide XSLT 2.0 transformations.",
        name = "XSLT 2",
        tags = "saxon, xml, xslt, transform",
        dependency = {"saxon9he.jar", "protobuf-java-2.2.0.jar"},
        baseURL = "meandre://brown.edu/seasr/tei/components/",
        firingPolicy = FiringPolicy.any,
        mode = Mode.compute,
        rights = Licenses.Other)
public class XSLT2Component extends AbstractXMLStreamComponent {

    @ComponentInput(description = "XSLT Document", name = Names.PORT_XSL)
    final static String IN_XSL = Names.PORT_XSL;

    @ComponentOutput(description = "XML Output String", name = Names.PORT_XML)
    final static String OUT_XML = Names.PORT_XML;

    @Override
    protected String[] getStreamOutputPorts() {
        return new String[] {OUT_XML};
    }


    private Processor proc;
    private XsltCompiler comp;
    private XsltExecutable exp;

    @SuppressWarnings({"ConstantConditions"})
    protected void processQueued(final ComponentContext cc, Queue<String> queue) {
        console.fine(String.format("Processing %d queued XML documents...", queue.size()));
        foreachDoc(new QueueBlock() {
            public void process(String xml) throws Exception {
                XdmNode source = proc.newDocumentBuilder().build(new StreamSource(new StringReader(xml)));
                Serializer out = new Serializer();
                out.setOutputProperty(Serializer.Property.METHOD, "xml");
                out.setOutputProperty(Serializer.Property.INDENT, "yes");
                StringWriter sw = new StringWriter();
                out.setOutputWriter(sw);
                XsltTransformer trans = exp.load();
                trans.setInitialContextNode(source);
                trans.setDestination(out);
                trans.transform();

                cc.pushDataComponentToOutput(OUT_XML, sw.toString());
            }
        });
    }


    @Override
    public void initializeCallBack(ComponentContextProperties ccp) throws Exception {
        super.initializeCallBack(ccp);
        proc = new Processor(false);
        comp = proc.newXsltCompiler();
    }

    @Override
    protected boolean isSidechainReady() {
        return exp != null;
    }

    @Override
    protected void processSidechainInputs(ComponentContext cc) throws Exception {
        try {

            if (cc.isInputAvailable(IN_XSL)) {
                if (exp == null) {
                    String xslt = DataTypeParser.parseAsString(cc.getDataComponentFromInput(IN_XSL))[0];
                    exp = comp.compile(new StreamSource(new StringReader(xslt)));

                } else {
                    console.warning("XSL transformation already set - ignoring new XSL data input");
                }
            }

        } catch (Exception e) {
            outputError(e, Level.SEVERE);
        }
    }

    @Override
    public void disposeCallBack(ComponentContextProperties ccp) throws Exception {
        super.disposeCallBack(ccp);
        comp = null;
        proc = null;
        exp = null;
    }
}
