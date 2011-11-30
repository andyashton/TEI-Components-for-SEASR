package edu.brown.seasr.poemextractorcomponent;

import edu.brown.seasr.AbstractXMLConverterComponent;
import edu.brown.seasr.Converter;
import org.meandre.annotations.Component;

/**
 * Created by IntelliJ IDEA.
 * User: mdellabitta
 * Date: 2011-10-10
 * Time: 4:03 PM
 */
@Component(
        creator = "Michael Della Bitta",
        description = "This component extracts any poems it finds in TEI documents it reads.",
        name = "Poem Extractor",
        tags = "xml, tei, poem",
        dependency = {"protobuf-java-2.2.0.jar", "saxon9he.jar"},
        baseURL = "meandre://brown.edu/seasr/tei/components/",
        firingPolicy = Component.FiringPolicy.all,
        mode = Component.Mode.compute,
        rights = Component.Licenses.Other)

public class PoemExtractorComponent extends AbstractXMLConverterComponent {
    @Override
    protected Converter getConverter() {
        return new PoemConverter();
    }
}
