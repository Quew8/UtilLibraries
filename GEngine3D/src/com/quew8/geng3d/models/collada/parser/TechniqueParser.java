package com.quew8.geng3d.models.collada.parser;

import com.quew8.geng.xmlparser.XMLElementParser;
import com.quew8.geng.xmlparser.XMLParseException;
import com.quew8.geng.xmlparser.XMLParser;
import com.quew8.geng3d.models.collada.Technique;
import java.util.HashMap;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
class TechniqueParser extends XMLParser {
    private static final String PHONG = "phong";
    
    private PhongParser phong;
    
    TechniqueParser() {
        super(false, false);
    }
    
    @Override
    public HashMap<String, XMLElementParser> addElementParsers(HashMap<String, XMLElementParser> to) {
        to = super.addElementParsers(to);
        to.put(PHONG, (XMLElementParser) (Element element) -> {
            phong = TechniqueParser.this.parseWith(element, new PhongParser());
        });
        return to;
    }

    @Override
    public XMLParseException onParsingDone() {
        if(phong == null) {
            return new XMLParseException("technique without phong");
        }
        return super.onParsingDone();
    }
    
    public Technique getTechnique() {
        return new Technique(phong.getPhong());
    }
}
