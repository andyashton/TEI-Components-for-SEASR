package edu.brown.seasr.authoriallyrevisedcomponent;

import edu.brown.seasr.AbstractXMLConverterComponent;
import edu.brown.seasr.Converter;
import org.meandre.annotations.Component;

/**
 * User: mdellabitta
 * Date: 2011-08-24
 * Time: 9:17 AM
 */

@Component(
        creator = "Michael Della Bitta",
        description = "This component renders an authorially-revised view of the TEI document.",
        name = "Authorially Revised Converter",
        tags = "xml tei",
        dependency = {"protobuf-java-2.2.0.jar", "saxon9he.jar"},
        baseURL = "meandre://brown.edu/seasr/tei/components/",
        firingPolicy = Component.FiringPolicy.any,
        mode = Component.Mode.compute,
        rights = Component.Licenses.Other)


public class AuthoriallyRevisedComponent extends AbstractXMLConverterComponent {
    
    @Override
    protected Converter getConverter() {
        return new AuthoriallyRevisedConverter();
    }
}

