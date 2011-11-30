package edu.brown.seasr.geocodingcomponent;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * User: mdellabitta
 * Date: 2011-05-31
 * Time: 8:18 AM
 */
public class Geocoder {

    URL buildURL(String name) throws Exception {
        String urlTemplate = "http://maps.googleapis.com/maps/api/geocode/json?sensor=false&address=";
        return new URL(urlTemplate + URLEncoder.encode(name, "UTF-8"));
    }

    String urlToString(URL url) throws Exception {
        URLConnection con = url.openConnection();
        Reader r = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
        StringBuilder sb = new StringBuilder();

        while (true) {
            int ch = r.read();
            if (ch < 0)
                break;
            sb.append((char) ch);
        }

        return sb.toString();
    }

    String parseLatLong(String jsonString) {
        JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(jsonString);
        Object status = jsonObject.get("status");
        if (!"OK".equals(status)) return null;
        JSONArray array = (JSONArray) jsonObject.get("results");
        JSONObject firstResult = (JSONObject) array.get(0);
        JSONObject geometry = (JSONObject) firstResult.get("geometry");
        JSONObject location = (JSONObject) geometry.get("location");
        return location.getString("lat") + "," + location.getString("lng");
    }

    public String geocode(String name) throws Exception {
        URL url = buildURL(name);
        String jsonString = urlToString(url);
        return parseLatLong(jsonString);

    }
}
