package edu.brown.seasr.editorcomponent;

import edu.brown.seasr.AbstractXMLConverterComponent;
import edu.brown.seasr.Converter;
import org.meandre.annotations.Component;
import org.meandre.annotations.ComponentProperty;
import org.meandre.core.ComponentContext;
import org.meandre.core.ComponentContextProperties;

/**
 * User: mdellabitta
 * Date: 2011-08-25
 * Time: 8:49 AM
 */

@Component(
        creator = "Michael Della Bitta",
        description = "This component renders a view of the TEI document from the perspective of a given editor.",
        name = "Editor X Converter",
        tags = "xml tei",
        dependency = {"protobuf-java-2.2.0.jar", "saxon9he.jar"},
        baseURL = "meandre://brown.edu/seasr/tei/components/",
        firingPolicy = Component.FiringPolicy.any,
        mode = Component.Mode.compute,
        rights = Component.Licenses.Other)


public class EditorXComponent extends AbstractXMLConverterComponent {

    @ComponentProperty(name = "editor", description = "Name of editor.", defaultValue = "")
    final static String PROP_EDITOR = "editor";

    private String editor;

    @Override
    public void initializeCallBack(ComponentContextProperties ccp) throws Exception {
        super.initializeCallBack(ccp);
        editor = ccp.getProperty(PROP_EDITOR);
    }

    @Override
    protected Converter getConverter() {
        return new EditorXConverter(editor);
    }
}


