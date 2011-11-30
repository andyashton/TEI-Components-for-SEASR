package edu.brown.seasr.diplomaticcomponent;

import edu.brown.seasr.AbstractXMLConverterComponent;
import edu.brown.seasr.Converter;
import org.meandre.annotations.Component;

/**
 * User: mdellabitta
 * Date: 2011-06-10
 * Time: 6:15 PM
 */

@Component(
        creator = "Michael Della Bitta",
        description = "This component renders a diplomatic view of the TEI document.",
        name = "Diplomatic Converter",
        tags = "xml tei",
        dependency = {"protobuf-java-2.2.0.jar", "saxon9he.jar"},
        baseURL = "meandre://brown.edu/seasr/tei/components/",
        firingPolicy = Component.FiringPolicy.any,
        mode = Component.Mode.compute,
        rights = Component.Licenses.Other)

public class DiplomaticComponent extends AbstractXMLConverterComponent {

    @Override
    protected Converter getConverter() {
        return new DiplomaticConverter();
    }
}
