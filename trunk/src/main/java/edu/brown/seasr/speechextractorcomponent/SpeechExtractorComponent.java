package edu.brown.seasr.speechextractorcomponent;

import edu.brown.seasr.AbstractXMLConverterComponent;
import edu.brown.seasr.Converter;
import edu.brown.seasr.personalnameextractorcomponent.PersonalNameConverter;
import org.meandre.annotations.Component;

/**
 * Created by IntelliJ IDEA.
 * User: mdellabitta
 * Date: 2011-10-10
 * Time: 3:27 PM
 */

@Component(
        creator = "Michael Della Bitta",
        description = "This component returns an XML document containing <q> or <sp> elements found in the text.",
        name = "Speech Extractor",
        tags = "xml tei name",
        dependency = {"protobuf-java-2.2.0.jar", "saxon9he.jar"},
        baseURL = "meandre://brown.edu/seasr/tei/components/",
        firingPolicy = Component.FiringPolicy.any,
        mode = Component.Mode.compute,
        rights = Component.Licenses.Other)

public class SpeechExtractorComponent extends AbstractXMLConverterComponent {

    @Override
    protected Converter getConverter() {
        return new SpeechConverter();
    }

}
