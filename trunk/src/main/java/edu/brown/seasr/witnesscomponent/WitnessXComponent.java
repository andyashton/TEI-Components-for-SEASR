package edu.brown.seasr.witnesscomponent;

import edu.brown.seasr.AbstractXMLConverterComponent;
import edu.brown.seasr.Converter;
import org.meandre.annotations.Component;
import org.meandre.annotations.ComponentProperty;
import org.meandre.core.ComponentContextProperties;

/**
 * User: mdellabitta
 * Date: 2011-08-26
 * Time: 8:28 AM
 */

@Component(
        creator = "Michael Della Bitta",
        description = "This component renders a view of the TEI document from the point of view of a given witness.",
        name = "Witness X Converter",
        tags = "xml tei",
        dependency = {"protobuf-java-2.2.0.jar", "saxon9he.jar"},
        baseURL = "meandre://brown.edu/seasr/tei/components/",
        firingPolicy = Component.FiringPolicy.any,
        mode = Component.Mode.compute,
        rights = Component.Licenses.Other)

public class WitnessXComponent extends AbstractXMLConverterComponent {

    @ComponentProperty(name = "witness", description = "Name of witness.", defaultValue = "")
    final static String PROP_WITNESS = "witness";

    private String witness;

    @Override
    public void initializeCallBack(ComponentContextProperties ccp) throws Exception {
        super.initializeCallBack(ccp);
        witness = ccp.getProperty(PROP_WITNESS);
    }
    
    @Override
    protected Converter getConverter() {
        return new WitnessXConverter(witness);
    }
}
