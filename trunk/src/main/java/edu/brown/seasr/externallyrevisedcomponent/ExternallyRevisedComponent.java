package edu.brown.seasr.externallyrevisedcomponent;

import edu.brown.seasr.AbstractXMLConverterComponent;
import edu.brown.seasr.Converter;
import org.meandre.annotations.Component;
import org.meandre.annotations.ComponentProperty;
import org.meandre.core.ComponentContextProperties;

/**
 * User: mdellabitta
 * Date: 2011-08-25
 * Time: 8:57 AM
 */

@Component(
        creator = "Michael Della Bitta",
        description = "This component renders an externally-revised view of the TEI document.",
        name = "Externally Revised Converter",
        tags = "xml tei",
        dependency = {"protobuf-java-2.2.0.jar", "saxon9he.jar"},
        baseURL = "meandre://brown.edu/seasr/tei/components/",
        firingPolicy = Component.FiringPolicy.any,
        mode = Component.Mode.compute,
        rights = Component.Licenses.Other)

public class ExternallyRevisedComponent extends AbstractXMLConverterComponent {

    @ComponentProperty(name = "revisor", description = "Name of revisor.", defaultValue = "")
    final static String PROP_REVISOR = "revisor";

    private String revisor;

    @Override
    public void initializeCallBack(ComponentContextProperties ccp) throws Exception {
        super.initializeCallBack(ccp);
        revisor = ccp.getProperty(PROP_REVISOR);
    }

    @Override
    protected Converter getConverter() {
        return new ExternallyRevisedConverter(revisor);
    }
}




