package edu.brown.seasr.relaxngcomponent;

import com.thaiopensource.validate.ValidationDriver;
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
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;

@SuppressWarnings({"WeakerAccess"})
@Component(
        creator = "Michael Della Bitta",
        description = "This component returns a boolean value for every document representing whether it validated against its Relax NG schema or not.",
        name = "RelaxNG Validator",
        tags = "xml, RelaxNG, validator, valid, schema",
        dependency = {"protobuf-java-2.2.0.jar", "isorelax.jar", "jing.jar", "saxon9he.jar"},
        baseURL = "meandre://brown.edu/seasr/tei/components/",
        firingPolicy = FiringPolicy.any,
        mode = Mode.compute,
        rights = Licenses.Other)
public class RelaxNGValidatorComponent extends AbstractXMLStreamComponent {

    @ComponentInput(description = "The RELAX NG Schema." + "<br>TYPE: java.lang.String"
            + "<br>TYPE: org.seasr.datatypes.BasicDataTypes.Strings", name = "relax_ng_schema")
    final static String IN_RELAX_NG_SCHEMA = "relax_ng_schema";

    @ComponentOutput(description = "Validation Result", name = "valid")
    final static String OUT_VALID = "valid";

    protected RelaxNGValidator v = new RelaxNGValidator();

    public void setRelaxNGValidator(RelaxNGValidator v) {
        this.v = v;
    }

    @Override
    protected String[] getStreamOutputPorts() {
        return new String[] {OUT_VALID};
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
                    outputError("Unable to load schema.", e, Level.SEVERE);
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
                cc.pushDataComponentToOutput(OUT_VALID, v.validateDoc(xml));
            }
        });
    }

    @Override
    public void disposeCallBack(ComponentContextProperties ccp) throws Exception {
        super.disposeCallBack(ccp);
        v = null;
    }
}
