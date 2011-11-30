package edu.brown.seasr.xslt2component;

import static edu.brown.seasr.xslt2component.XSLT2Component.IN_XML;
import static edu.brown.seasr.xslt2component.XSLT2Component.IN_XSL;
import static edu.brown.seasr.xslt2component.XSLT2Component.OUT_XML;

import edu.brown.seasr.SEASRComponentTest;
import org.hamcrest.Matchers;
import org.jmock.Expectations;
import org.junit.Test;
import org.meandre.core.ComponentContext;
import org.meandre.core.ExecutableComponent;

public class XSLT2ComponentTest extends SEASRComponentTest {

    private static final String XML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><tutorials><tutorial><name>XML Tutorial</name><url>http://www.quackit.com/xml/tutorial</url></tutorial><tutorial><name>HTML Tutorial</name><url>http://www.quackit.com/html/tutorial</url></tutorial></tutorials>";
    private static final String XSL = "<?xml version=\"1.0\"?><xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"><xsl:template match=\"/\"><html><head><title>XML XSL Example</title><style type=\"text/css\">body{margin:10px;background-color:#ccff00;font-family:verdana,helvetica,sans-serif;}.tutorial-name{display:block;font-weight:bold;}.tutorial-url{display:block;color:#636363;font-size:small;font-style:italic;}</style></head><body><h2>Cool Tutorials</h2><p>Hey, check out these tutorials!</p><xsl:apply-templates/></body></html></xsl:template><xsl:template match=\"tutorial\"><span class=\"tutorial-name\"><xsl:value-of select=\"name\"/></span><span class=\"tutorial-url\"><xsl:value-of select=\"url\"/></span></xsl:template></xsl:stylesheet>";
    
    @Override
    public ExecutableComponent getComponent() {
        return new XSLT2Component();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGoodTemplate() throws Exception {
        
        final ComponentContext cc = utils.getComponentContext();
        
        Expectations expectations = new Expectations() {
            {
                oneOf(cc)
                        .pushDataComponentToOutput(
                                with(OUT_XML),
                                with(Matchers.allOf(
                                        Matchers.containsString("<html>"),
                                        Matchers.containsString("HTML Tutorial")
                                )));
            }
        };
        
        inputs.put(IN_XML, XML);
        inputs.put(IN_XSL, XSL);

        test(expectations);
    }

    @Test
    public void testBadTemplate() throws Exception {
        final ComponentContext cc = utils.getComponentContext();        
        Expectations expectations = new Expectations() {{
            oneOf(cc).pushDataComponentToOutput(with("error"), with(any(String.class)));
        }};

        inputs.put(IN_XML, XML);
        inputs.put(IN_XSL, XML);

        test(expectations);
    }
}