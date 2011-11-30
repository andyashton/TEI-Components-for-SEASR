package edu.brown.seasr.lettersextractorcomponent;

import edu.brown.seasr.AbstractXMLConverterComponent;
import edu.brown.seasr.Converter;
import org.meandre.annotations.Component;

/**
 * Created by IntelliJ IDEA.
 * User: mdellabitta
 * Date: 2011-10-10
 * Time: 4:32 PM
 */

@Component(
        creator = "Michael Della Bitta",
        description = "This component returns an XML document containing any <div type='prefatory'> elements found in the text.",
        name = "Letters Extractor",
        tags = "xml tei letters preface prefatory",
        dependency = {"protobuf-java-2.2.0.jar", "saxon9he.jar"},
        baseURL = "meandre://brown.edu/seasr/tei/components/",
        firingPolicy = Component.FiringPolicy.any,
        mode = Component.Mode.compute,
        rights = Component.Licenses.Other)

public class LettersExtractorComponent extends AbstractXMLConverterComponent {

    @Override
    protected Converter getConverter() {
        return new LettersConverter();
    }
}
