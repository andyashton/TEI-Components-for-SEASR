package edu.brown.seasr.relaxngcomponent;

import com.thaiopensource.validate.ValidationDriver;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.logging.Level;

/**
 * User: mdellabitta
 * Date: 2011-01-04
 * Time: 9:01 AM
 */
public class RelaxNGValidator {

    private boolean gotSchema;
    private ValidationDriver driver = new ValidationDriver();

    public boolean hasSchema() {
        return gotSchema;
    }

    public void setSchema(String schema) throws Exception {
        
        if (!driver.loadSchema(new InputSource(new StringReader(schema)))) {
            throw new Exception("Unable to parse schema.");
        }

        gotSchema = true;
    }

    public boolean validateDoc(String xml) throws Exception {
        return driver.validate(new InputSource(new StringReader(xml)));
    }
}
