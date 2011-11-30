package edu.brown.seasr.personalnameextractorcomponent;

import edu.brown.seasr.AbstractXMLConverterComponent;
import edu.brown.seasr.Converter;
import org.meandre.annotations.Component;

/**
 * Created by IntelliJ IDEA.
 * User: mdellabitta
 * Date: 2011-10-10
 * Time: 3:19 PM
 */

@Component(
        creator = "Michael Della Bitta",
        description = "This component returns an XML document containing <persName> elements found in the text.",
        name = "Personal Name Extractor",
        tags = "xml tei name",
        dependency = {"protobuf-java-2.2.0.jar", "saxon9he.jar"},
        baseURL = "meandre://brown.edu/seasr/tei/components/",
        firingPolicy = Component.FiringPolicy.any,
        mode = Component.Mode.compute,
        rights = Component.Licenses.Other)

public class PersonalNameComponent extends AbstractXMLConverterComponent {

    @Override
    protected Converter getConverter() {
        return new PersonalNameConverter();
    }
}
