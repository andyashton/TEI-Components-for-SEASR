package edu.brown.seasr.groupbuildercomponent;

import org.meandre.annotations.Component;
import org.meandre.annotations.ComponentInput;
import org.meandre.annotations.ComponentOutput;
import org.meandre.core.ComponentContext;
import org.meandre.core.ComponentContextProperties;
import org.meandre.core.system.components.ext.StreamInitiator;
import org.meandre.core.system.components.ext.StreamTerminator;
import org.seasr.datatypes.core.BasicDataTypesTools;
import org.seasr.datatypes.core.DataTypeParser;
import org.seasr.datatypes.core.Names;
import org.seasr.meandre.components.abstracts.AbstractExecutableComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * User: mdellabitta
 * Date: 2011-02-15
 * Time: 5:45 PM
 */

@Component(
        creator = "Michael Della Bitta",
        description = "This component takes a stream of documents returns them in a series of instances of Strings. Requires wrapped streams!",
        name = "Group Builder",
        tags = "group aggregate aggregator",
        dependency = { "protobuf-java-2.2.0.jar" , "saxon9he.jar"},
        baseURL = "meandre://brown.edu/seasr/tei/components/",
        firingPolicy = Component.FiringPolicy.any,
        mode = Component.Mode.compute,
        rights = Component.Licenses.Other)

public class GroupBuilderComponent extends AbstractExecutableComponent {

    @ComponentInput(description = "The document stream.", name = Names.PORT_TEXT)
    final static String IN_DOCUMENTS = Names.PORT_TEXT;

    @ComponentOutput(description = "The Strings instance(s)", name = Names.PORT_TEXT)
    final static String OUT_STRINGS = Names.PORT_TEXT;

    private final List<String> stringList = new ArrayList<String>();

    @Override
    public void disposeCallBack(ComponentContextProperties componentContextProperties) throws Exception {
        stringList.removeAll(stringList);
    }

    @Override
    public void initializeCallBack(ComponentContextProperties componentContextProperties) throws Exception {
        //do nothing
    }

    @Override
    public void executeCallBack(ComponentContext cc) throws Exception {
        if (cc.isInputAvailable(IN_DOCUMENTS)) {
            String[] inDocs = DataTypeParser.parseAsString(cc.getDataComponentFromInput(IN_DOCUMENTS));
            stringList.addAll(Arrays.asList(inDocs));
        }
    }

    @Override
    protected void handleStreamInitiators() throws Exception {
        console.entering(getClass().getName(), "handleStreamInitiators", inputPortsWithInitiators);
         //wipe the list
         stringList.removeAll(stringList);

        //push out the stream tokens
        console.fine("Forwarding " + StreamInitiator.class.getSimpleName() + " to the next component...");
        componentContext.pushDataComponentToOutput(OUT_STRINGS, componentContext.getDataComponentFromInput(IN_DOCUMENTS));
        
        console.exiting(getClass().getName(), "handleStreamInitiators");
    }

    @Override
    protected void handleStreamTerminators() throws Exception {
        console.entering(getClass().getName(), "handleStreamTerminators", inputPortsWithTerminators);

        //don't mess with things if this isn't our token (just in case)
            //here comes the Strings
            console.finer("pushing strings");
            componentContext.pushDataComponentToOutput(OUT_STRINGS, BasicDataTypesTools.stringToStrings(stringList.toArray(new String[stringList.size()])));
        

        console.fine("Forwarding " + StreamTerminator.class.getSimpleName() + " to the next component...");
        componentContext.pushDataComponentToOutput(OUT_STRINGS, componentContext.getDataComponentFromInput(IN_DOCUMENTS));

        console.exiting(getClass().getName(), "handleStreamTerminators");
    }
}
