package edu.brown.seasr;

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

import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;

/**
 * User: mdellabitta
 * Date: 2011-02-04
 * Time: 6:11 PM
 */
public abstract class AbstractXMLStreamComponent extends AbstractExecutableComponent {

    @ComponentInput(description =
            "The XML document." +
            "<br>TYPE: org.w3c.dom.Document" +
            "<br>TYPE: java.lang.String" +
            "<br>TYPE: org.seasr.datatypes.BasicDataTypes.Strings",
            name = Names.PORT_XML)
    public final static String IN_XML = Names.PORT_XML;

    private Queue<String> queue;

    protected Queue<String> getQueue() {
        return queue;
    }

    @Override
    public void initializeCallBack(ComponentContextProperties ccp) throws Exception {
        queue = new LinkedList<String>();
    }

    @Override
    public void disposeCallBack(ComponentContextProperties ccp) throws Exception {
        queue = null;
    }

    @Override
    public void executeCallBack(ComponentContext cc) throws Exception {

        try {
            processSidechainInputs(cc);

            if (cc.isInputAvailable(IN_XML)) {
                for (String doc : DataTypeParser.parseAsString(cc.getDataComponentFromInput(IN_XML))) {
                    queue.offer(doc);
                }
            }

            if (queue.size() > 0 && isSidechainReady()) {
                processQueued(cc, queue);
            }

        } catch (Exception e) {
            console.log(Level.SEVERE, e.getMessage());
            throw new ComponentExecutionException(e);
        }

    }

    protected abstract void processSidechainInputs(ComponentContext cc) throws Exception;
    protected abstract void processQueued(ComponentContext cc, Queue<String> queue);
    protected abstract boolean isSidechainReady();

    protected void foreachDoc(QueueBlock block) {
        block.processQueue(queue, this);
    }

    protected String[] getStreamOutputPorts() {
        return null;
    }

    protected void handleStreamInitiators() throws Exception {
        console.entering(getClass().getName(), "handleStreamInitiators", inputPortsWithInitiators);

        if (getStreamOutputPorts() != null) {
            console.fine("Forwarding " + StreamInitiator.class.getSimpleName() + " to the next component...");
            for (String name: getStreamOutputPorts()) {
                componentContext.pushDataComponentToOutput(name, componentContext.getDataComponentFromInput(IN_XML));
            }
            
        } else {
            console.fine("Ignoring " + StreamInitiator.class.getSimpleName() + " received on ports " + inputPortsWithInitiators);
        }

        console.exiting(getClass().getName(), "handleStreamInitiators");
    }

    protected void handleStreamTerminators() throws Exception {

        console.entering(getClass().getName(), "handleStreamTerminators", inputPortsWithTerminators);

        if (getStreamOutputPorts() != null) {
            console.fine("Forwarding " + StreamTerminator.class.getSimpleName() + " to the next component...");
            for (String name: getStreamOutputPorts()) {
                componentContext.pushDataComponentToOutput(name, componentContext.getDataComponentFromInput(IN_XML));
            }
            
        } else {
            console.fine("Ignoring " + StreamTerminator.class.getSimpleName() + " received on ports " + inputPortsWithTerminators);
        }

        console.exiting(getClass().getName(), "handleStreamTerminators");
    }
}
