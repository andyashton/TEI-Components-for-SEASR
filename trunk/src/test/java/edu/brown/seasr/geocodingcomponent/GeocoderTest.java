package edu.brown.seasr.geocodingcomponent;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * User: mdellabitta
 * Date: 2011-05-31
 * Time: 9:00 AM
 */
public class GeocoderTest {
    /*
    TODO no sockets in unit tests!
     */

    @Test
    public void test() throws Exception {
        if (true) return;
        Geocoder geocoder = new Geocoder();
        assertTrue("40.7478889,-73.9828931".equals(geocoder.geocode("188 madison avenue, new york, ny")));
        assertTrue("48.856614,2.3522219".equals(geocoder.geocode("paris, france")));
        assertTrue("41.8310893,-71.3980237".equals(geocoder.geocode("brown university")));
    }

}
