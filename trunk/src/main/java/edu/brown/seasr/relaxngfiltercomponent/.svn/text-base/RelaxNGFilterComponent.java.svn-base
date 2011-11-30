package edu.brown.seasr.relaxngfiltercomponent;

import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.io.StringReader;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import edu.brown.seasr.AbstractXMLStreamComponent;
import edu.brown.seasr.QueueBlock;
import edu.brown.seasr.relaxngcomponent.RelaxNGValidator;
import org.meandre.annotations.Component;
import org.meandre.annotations.ComponentInput;
import org.meandre.annotations.ComponentOutput;
import org.meandre.annotations.Component.FiringPolicy;
import org.meandre.annotations.Component.Licenses;
import org.meandre.annotations.Component.Mode;
import org.meandre.core.ComponentContext;
import org.meandre.core.ComponentContextProperties;
import org.meandre.core.ComponentExecutionException;
import org.seasr.datatypes.core.DataTypeParser;
import org.seasr.datatypes.core.Names;
import org.seasr.meandre.components.abstracts.AbstractExecutableComponent;


@SuppressWarnings({"WeakerAccess"})
@Component(
        creator = "Michael Della Bitta",
        description = "This component filters documents that are valid from those that aren't according to their RelaxNG schema .",
        name = "RelaxNG Filter",
        tags = "xml, RelaxNG, validator, valid, schema, filter",
        dependency = {"protobuf-java-2.2.0.jar", "isorelax.jar", "jing.jar", "saxon9he.jar"},
        baseURL = "meandre://brown.edu/seasr/tei/components/",
        firingPolicy = FiringPolicy.any,
        mode = Mode.compute,
        rights = Licenses.Other)

public class RelaxNGFilterComponent extends AbstractXMLStreamComponent {

    @ComponentInput(description = "The RELAX NG Schema." + "<br>TYPE: java.lang.String"
            + "<br>TYPE: org.seasr.datatypes.BasicDataTypes.Strings", name = "relax_ng_schema")
    final static String IN_RELAX_NG_SCHEMA = "relax_ng_schema";

    @ComponentOutput(description = "Valid Documents", name = "valid")
    final static String OUT_VALID = "valid";

    @ComponentOutput(description = "Invalid Documents", name = "invalid")
    final static String OUT_INVALID = "invalid";

    private RelaxNGValidator v = new RelaxNGValidator();

    public void setValidator(RelaxNGValidator v) {
        this.v = v;
    }

    @Override
    protected String[] getStreamOutputPorts() {
        return new String[] {OUT_VALID, OUT_INVALID};
    }

    @Override
    protected boolean isSidechainReady() {
        return v.hasSchema();
    }

    @Override
    protected void processSidechainInputs(ComponentContext cc) throws Exception {
        try {
            if (cc.isInputAvailable(IN_RELAX_NG_SCHEMA)) {
                String schemaTxt = DataTypeParser.parseAsString(cc.getDataComponentFromInput(IN_RELAX_NG_SCHEMA))[0];

                try {
                    v.setSchema(schemaTxt);

                } catch (Exception e) {
                    outputError("Unable to load schema.", Level.SEVERE);
                }
            }

        } catch (Exception e) {
            console.log(Level.SEVERE, e.getMessage());
            throw new ComponentExecutionException(e);
        }
    }

    @SuppressWarnings({"ConstantConditions"})
    protected void processQueued(final ComponentContext cc, Queue<String> queue) {
        console.fine(String.format("Processing %d queued XML documents...", queue.size()));

        foreachDoc(new QueueBlock() {
            public void process(String xml) throws Exception {
                boolean valid = v.validateDoc(xml);

                if (valid) {
                    cc.pushDataComponentToOutput(OUT_VALID, xml);

                } else {
                    cc.pushDataComponentToOutput(OUT_INVALID, xml);
                }

            }
        });

    }

    @Override
    public void disposeCallBack(ComponentContextProperties ccp) throws Exception {
        super.disposeCallBack(ccp);
        v = null;
    }
}
