package edu.brown.seasr;

import edu.brown.seasr.filesystemenumeratorcomponent.FilesystemEnumerator;
import edu.brown.seasr.xmluriloadercomponent.URIXMLLoader;
import org.w3c.dom.Document;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.Date;

/**
 * User: mdellabitta
 * Date: 2011-05-03
 * Time: 8:57 AM
 */
public class StandaloneRun {

    public static void main(String[] args) throws Exception {
        long start = new Date().getTime();
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("/Users/mdellabitta/Desktop/testoutput.txt"));

        String[] choices = new String[] { "sic", "orig", "abbr", "am" };
        FilesystemEnumerator filesystemEnumerator = new FilesystemEnumerator();
        URIXMLLoader loader = new URIXMLLoader();
        ComponentXMLUtils componentXMLUtils = new ComponentXMLUtils();
        String parentDir = "/Users/mdellabitta/Dropbox/Dev/brown/wwp/texts";

        String[] files = filesystemEnumerator.getURIs(parentDir, "", false);

        for (String fileURI : files) {
            try {
                String xml = loader.getDocString(fileURI);
                Document doc = componentXMLUtils.stringToDocument(xml);

                for (String choice : choices) {
                    componentXMLUtils.choose(doc, choice);
                }

                xml = componentXMLUtils.docToString(doc);
                out.write(xml.getBytes("utf8"));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        out.flush();
        out.close();
        long end = new Date().getTime();
        System.out.println((end-start) + "ms");
    }
}
