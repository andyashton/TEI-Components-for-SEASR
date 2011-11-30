package edu.brown.seasr.linesofverseextractorcomponent;

import edu.brown.seasr.AbstractXMLConverterComponent;
import edu.brown.seasr.Converter;
import org.meandre.annotations.Component;

/**
 * Created by IntelliJ IDEA.
 * User: mdellabitta
 * Date: 2011-10-11
 * Time: 8:48 AM
 */

@Component(
        creator = "Michael Della Bitta",
        description = "This component extracts any lines of verse found in the text.",
        name = "Lines of Verse Extractor",
        tags = "xml tei line verse poem",
        dependency = {"protobuf-java-2.2.0.jar", "saxon9he.jar"},
        baseURL = "meandre://brown.edu/seasr/tei/components/",
        firingPolicy = Component.FiringPolicy.any,
        mode = Component.Mode.compute,
        rights = Component.Licenses.Other)

public class LinesOfVerseExtractorComponent extends AbstractXMLConverterComponent {
    @Override
    protected Converter getConverter() {
        return new LinesOfVerseConverter();
    }
}
