package edu.brown.seasr.linesplittercomponent;

import org.meandre.annotations.Component;
import org.meandre.annotations.ComponentInput;
import org.meandre.annotations.ComponentOutput;
import org.meandre.annotations.ComponentProperty;
import org.meandre.core.ComponentContext;
import org.meandre.core.ComponentContextProperties;
import org.meandre.core.system.components.ext.StreamInitiator;
import org.meandre.core.system.components.ext.StreamTerminator;
import org.seasr.datatypes.core.BasicDataTypes;
import org.seasr.datatypes.core.BasicDataTypesTools;
import org.seasr.datatypes.core.DataTypeParser;
import org.seasr.datatypes.core.Names;
import org.seasr.meandre.components.abstracts.AbstractExecutableComponent;

import javax.sound.sampled.Line;
import java.io.File;
import java.util.logging.Level;

/**
 * User: mdellabitta
 * Date: 2011-03-07
 * Time: 8:18 AM
 */

@SuppressWarnings({"WeakerAccess"})
@Component(
        creator = "Michael Della Bitta",
        description = "This component splits a string on a given delimiter and returns it as an instance of Strings.",
        name = "Line Splitter",
        tags = "split, delimiter, list, strings",
        dependency = {"protobuf-java-2.2.0.jar", "saxon9he.jar"},
        baseURL = "meandre://brown.edu/seasr/tei/components/",
        firingPolicy = Component.FiringPolicy.any,
        mode = Component.Mode.compute,
        rights = Component.Licenses.Other)
public class LineSplitterComponent extends AbstractExecutableComponent {

    @ComponentInput(description = "The delimited list.<br>TYPE: java.lang.String", name = Names.PORT_TEXT)
    final static String IN_LIST = Names.PORT_TEXT;

    @ComponentOutput(description = "The Strings instances", name = "strings")
    final static String OUT_STRINGS = "strings";

    @ComponentProperty(
            description = "The string to split the input string on.",
            defaultValue = "\n",
            name = "delimiter"
    )

    protected static final String PROP_DELIMITER = "delimiter";

    @ComponentProperty(
            name = Names.PROP_WRAP_STREAM,
            description = "Should the pushed message be wrapped as a stream.",
            defaultValue = "false"
    )
    protected static final String PROP_WRAP_STREAM = Names.PROP_WRAP_STREAM;
    
    private LineSplitter ls;
    private boolean wrap;

    public void setLS(LineSplitter ls) {
        this.ls = ls;
    }

    @Override
    public void disposeCallBack(ComponentContextProperties componentContextProperties) throws Exception {
        //do nothing
    }

    @Override
    public void initializeCallBack(ComponentContextProperties ccp) throws Exception {
        ls = new LineSplitter(ccp.getProperty(PROP_DELIMITER));
        String wrapProp = ccp.getProperty(PROP_WRAP_STREAM);
        
        if (wrapProp != null && "true".equals(wrapProp.toLowerCase())) {
            wrap = true;
        }
    }

    @Override
    public void executeCallBack(ComponentContext cc) throws Exception {
        
        if (!cc.isInputAvailable(IN_LIST)) {
            outputError(IN_LIST + " not populated!", Level.WARNING);
        }

        String list = DataTypeParser.parseAsString(cc.getDataComponentFromInput(IN_LIST))[0];
        String[] listElements = ls.split(list);
        BasicDataTypes.Strings output = BasicDataTypesTools.stringToStrings(listElements);

        if (wrap) {
            pushInitiator(list);
        }

        cc.pushDataComponentToOutput(OUT_STRINGS, output);

        if (wrap) {
            pushTerminator(list);
        }

    }

    private void pushInitiator(String list) throws Exception {
        console.fine("Pushing " + StreamInitiator.class.getSimpleName());
        StreamInitiator si = new StreamInitiator();
        si.put("list", list);
        componentContext.pushDataComponentToOutput(OUT_STRINGS, si);
    }

    private void pushTerminator(String list) throws Exception {
        console.fine("Pushing " + StreamTerminator.class.getSimpleName());
        StreamTerminator st = new StreamTerminator();
        st.put("list", list);
        componentContext.pushDataComponentToOutput(OUT_STRINGS, st);
    }

}
