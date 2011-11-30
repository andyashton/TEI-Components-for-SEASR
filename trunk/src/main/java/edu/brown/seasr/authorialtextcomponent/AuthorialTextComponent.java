package edu.brown.seasr.authorialtextcomponent;

import edu.brown.seasr.*;
import org.meandre.annotations.Component;

/**
 * User: mdellabitta
 * Date: 2011-08-24
 * Time: 7:35 PM
 */

@Component(
        creator = "Michael Della Bitta",
        description = "This component renders an authorial view of the TEI document.",
        name = "Authorial Text Converter",
        tags = "xml tei",
        dependency = {"protobuf-java-2.2.0.jar", "saxon9he.jar"},
        baseURL = "meandre://brown.edu/seasr/tei/components/",
        firingPolicy = Component.FiringPolicy.any,
        mode = Component.Mode.compute,
        rights = Component.Licenses.Other)

public class AuthorialTextComponent extends AbstractXMLConverterComponent {

    @Override
    protected Converter getConverter() {
        return new AuthorialTextConverter();
    }
}

