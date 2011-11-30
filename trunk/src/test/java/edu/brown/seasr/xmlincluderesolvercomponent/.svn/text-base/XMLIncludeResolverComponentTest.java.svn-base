package edu.brown.seasr.xmlincluderesolvercomponent;

import static edu.brown.seasr.xmlincluderesolvercomponent.XMLIncludeResolverComponent.IN_XML;
import static edu.brown.seasr.xmlincluderesolvercomponent.XMLIncludeResolverComponent.OUT_XML;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;

import java.io.*;

import edu.brown.seasr.SEASRComponentTest;
import edu.brown.seasr.xmluriloadercomponent.URIXMLLoader;
import org.jdom.*;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;
import org.jmock.Expectations;
import org.junit.Test;
import org.meandre.core.ComponentContext;
import org.meandre.core.ExecutableComponent;

public class XMLIncludeResolverComponentTest extends SEASRComponentTest {

    private URIXMLLoader loader = new URIXMLLoader();

    class MockResolver extends Resolver {

        private String base;

        MockResolver(String base) {
            this.base = base;
        }

        @Override
        public String resolve(String docString) throws Exception {

            SAXBuilder b = new SAXBuilder();
            Document doc = b.build(new StringReader(docString));

            Namespace xiNS = Namespace.getNamespace("xi", "http://www.w3.org/2001/XInclude");
            XPath xiIncludeXPath = XPath.newInstance("//xi:include");
            xiIncludeXPath.addNamespace(xiNS);

            for (Object o : xiIncludeXPath.selectNodes(doc)) {
                Element e = (Element) o;
                e.setAttribute("href", base + "/" + e.getAttributeValue("href"));
            }

            StringWriter w = new StringWriter();
            XMLOutputter outputter = new XMLOutputter();
            outputter.output(doc, w);

            return w.toString();
        }
    }

    private static final String BASE = "base";

    @Override
    public ExecutableComponent getComponent() {
        XMLIncludeResolverComponent c = new XMLIncludeResolverComponent();
        c.setResolver(new MockResolver(BASE));
        return c;
    }

    @SuppressWarnings({"unchecked"})
    @Test
    public void test() throws Exception {

        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith("tei.xml");
            }
        };
        for (File file : baseDir.listFiles(filter)) {
            String documentString = loader.getDocString(file.toURI().toURL().toString());

            inputs.put(IN_XML, documentString);

            final ComponentContext cc = utils.getComponentContext();
            Expectations expectations = new Expectations() {{
                oneOf(cc).pushDataComponentToOutput(
                        with(OUT_XML),
                        with(allOf(
                                containsString("<?brown-seasr"),
                                containsString("location="),
                                containsString("xi:include href=\"" + BASE)
                        ))
                );
            }};

            test(expectations);
        }

    }

    @Test
    @SuppressWarnings({"unchecked"})
    public void testBaconSermons() throws Exception {
        String baconSermons = loader.getDocString(new File(baseDir, "bacon.sermons.tei.xml").toURI().toURL().toString());
        inputs.put(IN_XML, baconSermons);

        final ComponentContext cc = utils.getComponentContext();
        Expectations expectations = new Expectations() {{
            oneOf(cc).pushDataComponentToOutput(with(OUT_XML), with(allOf(
                    containsString("xi:include href=\"" + BASE + "/availability.xml"),
                    containsString("xi:include href=\"" + BASE + "/projectDesc.xml"),
                    containsString("xi:include href=\"" + BASE + "/samplingDecl.xml"),
                    containsString("xi:include href=\"" + BASE + "/editorialDecl.xml"),
                    containsString("xi:include href=\"" + BASE + "/taxonomy.xml")
            )));
        }};

        test(expectations);
    }
}
