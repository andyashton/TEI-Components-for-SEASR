package edu.brown.seasr.dedicationextractorcomponent;

import edu.brown.seasr.AbstractXMLConverterComponent;
import edu.brown.seasr.Converter;
import org.meandre.annotations.Component;

/**
 * Created by IntelliJ IDEA.
 * User: mdellabitta
 * Date: 2011-10-10
 * Time: 4:27 PM
 */

@Component(
        creator = "Michael Della Bitta",
        description = "This component returns an XML document containing <div type='ded'> elements found in the text.",
        name = "Dedication Extractor",
        tags = "xml tei dedication",
        dependency = {"protobuf-java-2.2.0.jar", "saxon9he.jar"},
        baseURL = "meandre://brown.edu/seasr/tei/components/",
        firingPolicy = Component.FiringPolicy.any,
        mode = Component.Mode.compute,
        rights = Component.Licenses.Other)


public class DedicationExtractorComponent extends AbstractXMLConverterComponent {

    @Override
    protected Converter getConverter() {
        return new DedicationConverter();
    }
}
