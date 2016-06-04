package com.quew8.geng3d.models.collada.parser;

import com.quew8.geng.xmlparser.XMLAttributeParser;
import com.quew8.geng.xmlparser.XMLElementParser;
import com.quew8.geng.xmlparser.XMLParseException;
import com.quew8.geng.xmlparser.XMLParser;
import com.quew8.geng3d.models.collada.ColladaTexture;
import java.util.HashMap;
import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
public class SurfaceParser extends XMLParser {
    private static final String INIT_FROM = "init_from",
            TYPE = "type";
    
    private String type;
    private ImageParser initFrom;
    
    SurfaceParser() {
        
    }

    @Override
    public HashMap<String, XMLElementParser> addElementParsers(HashMap<String, XMLElementParser> to) {
        to.put(INIT_FROM, (XMLElementParser) (Element element) -> {
            initFrom = SurfaceParser.this.parseWith(element, new InitFromParser()).initFrom;
        });
        return super.addElementParsers(to);
    }

    @Override
    public HashMap<String, XMLAttributeParser> addAttributeParsers(HashMap<String, XMLAttributeParser> to) {
        to.put(TYPE, (XMLAttributeParser) (Attribute attribute, Element parent) -> {
            type = attribute.getValue();
        });
        return super.addAttributeParsers(to);
    }

    @Override
    public XMLParseException onParsingDone() {
        if(type == null) {
            return new XMLParseException("surface without type");
        }
        if(initFrom == null) {
            return new XMLParseException("surface without init_from");
        }
        return super.onParsingDone();
    }
    
    public ColladaTexture.Surface getSurface() {
        return new ColladaTexture.Surface(type, initFrom.getImage());
    }
    
    private static class InitFromParser extends XMLParser {
        private ImageParser initFrom;

        @Override
        public void parse(Element element) {
            initFrom = parseWith("#" + element.getText(), new ImageParser());
            super.parse(element);
        }

        @Override
        public XMLParseException onParsingDone() {
            if(initFrom == null) {
                return new XMLParseException("init_from without body");
            }
            return super.onParsingDone();
        }
        
        
    }
}
