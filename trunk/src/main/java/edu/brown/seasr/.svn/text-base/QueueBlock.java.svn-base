package edu.brown.seasr;

import java.util.Queue;
import java.util.logging.Level;

/**
 * User: mdellabitta
 * Date: 2011-02-23
 * Time: 9:05 AM
 */
public abstract class QueueBlock {

    public void processQueue(Queue<String> queue, AbstractXMLStreamComponent c) {
        
        while (!queue.isEmpty()) {
            String xml = queue.remove();

            try {
                process(xml);

            } catch (Exception e) {
                c.outputError(e, Level.WARNING);
            }
        }
    }

    public abstract void process(String xml) throws Exception;

}

