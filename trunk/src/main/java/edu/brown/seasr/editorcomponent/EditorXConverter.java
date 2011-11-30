package edu.brown.seasr.editorcomponent;

import edu.brown.seasr.Converter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.swing.tree.DefaultTreeCellEditor;
import java.util.Map;

/**
 * User: mdellabitta
 * Date: 2011-08-24
 * Time: 7:00 PM
 */
public class EditorXConverter extends Converter {
    /*
     the document reflecting the editorial choices and input of a specific editor
        --include <note> from chosen editor
    */

    public EditorXConverter(String editor) {
        this.editor = editor;
    }

    private String editor;


    public String convert(String xml) throws Exception {
        read(xml);
        
        //--when processing <app> (other elements?) use readings selected by the chosen editor?
        String findXPath = String.format("//%1$s:app", teiPrefix);
        String replaceXPath = String.format("./%1$s:rdg[@editor='%2$s']", teiPrefix, editor);
        utils.xPathElementFindAndReplace(doc, findXPath, replaceXPath, namespaces);

        //--include <note> from chosen editor
        findXPath = String.format("//%1$s:note", teiPrefix);
        NodeList nodes = utils.runXPathNodeListResult(findXPath, doc, namespaces);
        for (int i = 0; i < nodes.getLength(); i++) {
            Element note = (Element) nodes.item(i);
            if (!note.hasAttribute("editor") || editor.equals(note.getAttribute("editor"))) {
                note.getParentNode().removeChild(note);
            }
        }

        return utils.docToString(doc);
    }
}
