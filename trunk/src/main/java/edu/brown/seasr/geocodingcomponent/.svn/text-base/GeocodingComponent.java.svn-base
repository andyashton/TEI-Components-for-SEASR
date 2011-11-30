package edu.brown.seasr.geocodingcomponent;

import org.meandre.annotations.Component;
import org.meandre.annotations.ComponentInput;
import org.meandre.annotations.ComponentOutput;
import org.meandre.core.ComponentContext;
import org.meandre.core.ComponentContextProperties;
import org.meandre.core.system.components.ext.StreamInitiator;
import org.meandre.core.system.components.ext.StreamTerminator;
import org.seasr.datatypes.core.DataTypeParser;
import org.seasr.datatypes.core.Names;
import org.seasr.meandre.components.abstracts.AbstractExecutableComponent;

import java.util.Arrays;

/**
 * User: mdellabitta
 * Date: 2011-05-31
 * Time: 9:27 AM
 */

@Component(
        creator = "Michael Della Bitta",
        description = "This component takes place names as input and returns lists of latitude and longitude pairs.",
        name = "Geocoder",
        tags = "geocoding latitude longitude place map",
        dependency = {"protobuf-java-2.2.0.jar", "json-lib-2.2.1-jdk15.jar", "commons-beanutils-1.7.0.jar", "commons-collections-3.2.1.jar", "commons-lang-2.5.jar", "ezmorph-1.0.6.jar", "saxon9he.jar"},
        baseURL = "meandre://brown.edu/seasr/tei/components/",
        firingPolicy = Component.FiringPolicy.any,
        mode = Component.Mode.compute,
        rights = Component.Licenses.Other)

public class GeocodingComponent extends AbstractExecutableComponent {

    @ComponentInput(description = "The place stream.", name = "place")
    final static String IN_PLACES = "place";

    @ComponentOutput(description = "The coordinates.", name = "coordinates")
    final static String OUT_COORDINATES = "coordinates";

    private Geocoder geocoder = new Geocoder();

    public void setGeocoder(Geocoder geocoder) {
        this.geocoder = geocoder;
    }

    protected void handleStreamInitiators() throws Exception {
        componentContext.pushDataComponentToOutput(OUT_COORDINATES, componentContext.getDataComponentFromInput(IN_PLACES));
    }

    protected void handleStreamTerminators() throws Exception {
        componentContext.pushDataComponentToOutput(OUT_COORDINATES, componentContext.getDataComponentFromInput(IN_PLACES));
    }

    @Override
    public void executeCallBack(ComponentContext cc) throws Exception {
        if (cc.isInputAvailable(IN_PLACES)) {
            String[] places = DataTypeParser.parseAsString(cc.getDataComponentFromInput(IN_PLACES));

            for (String place: places) {
                String coordinates = geocoder.geocode(place);
                cc.pushDataComponentToOutput(OUT_COORDINATES, place + "=>" + coordinates);
            }
        }
    }

    @Override
    public void disposeCallBack(ComponentContextProperties componentContextProperties) throws Exception {
        //nothing
    }

    @Override
    public void initializeCallBack(ComponentContextProperties componentContextProperties) throws Exception {
        //nothing
    }

}
