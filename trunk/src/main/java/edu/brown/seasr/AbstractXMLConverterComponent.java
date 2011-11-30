package edu.brown.seasr;

import org.meandre.annotations.ComponentOutput;
import org.meandre.core.ComponentContext;
import org.seasr.datatypes.core.Names;

import java.util.Queue;

/**
 * User: mdellabitta
 * Date: 2011-08-25
 * Time: 8:13 AM
 */
public abstract class AbstractXMLConverterComponent<T extends edu.brown.seasr.Converter> extends AbstractXMLStreamComponent {

    @ComponentOutput(description = "Converted Result", name = Names.PORT_XML)
    public final static String OUT_XML = Names.PORT_XML;

    @Override
    protected boolean isSidechainReady() {
        return true;
    }

    @Override
    protected void processSidechainInputs(ComponentContext cc) throws Exception {
        //do nothing
    }

    protected T converter;

    protected abstract T getConverter();

    public void setConverter(T converter) {
        this.converter = converter;
    }

    @Override
    protected void processQueued(final ComponentContext cc, Queue<String> queue) {
        final Converter converter = this.converter == null ? getConverter() : this.converter;

        foreachDoc(new QueueBlock() {

            @Override
            public void process(String xml) throws Exception {
                String responseText = converter.convert(xml);
                if (responseText != null) cc.pushDataComponentToOutput(OUT_XML,  responseText);
            }
        });
    }

    @Override
    protected String[] getStreamOutputPorts() {
        return new String[] {OUT_XML};
    }
}
