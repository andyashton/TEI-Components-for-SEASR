package edu.brown.seasr.relaxngcomponent;

/**
 * User: mdellabitta
 * Date: 2011-02-04
 * Time: 7:23 PM
 */
public class MockRelaxNGValidator extends RelaxNGValidator {

    private String schema;

    @Override
    public void setSchema(String schema) throws Exception {
        this.schema = schema;
    }

    @Override
    public boolean hasSchema() {
        return schema != null;
    }

    @Override
    public boolean validateDoc(String xml) throws Exception {
        return Boolean.parseBoolean(schema);
    }
}
