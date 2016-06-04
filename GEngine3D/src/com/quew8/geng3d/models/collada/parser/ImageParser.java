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
public class ImageParser extends XMLParser {
    private static final String INIT_FROM = "init_from",
            NAME = "name";
    
    private String initFrom;
    private String name;
    
    ImageParser() {
        super(true, true);
    }

    @Override
    public HashMap<String, XMLElementParser> addElementParsers(HashMap<String, XMLElementParser> to) {
        to.put(INIT_FROM, (XMLElementParser) (Element element) -> {
            if(initFrom != null) {
                throw new XMLParseException("Cannot have mulitple init_from");
            }
            initFrom = element.getText();
        });
        return super.addElementParsers(to);
    }

    @Override
    public HashMap<String, XMLAttributeParser> addAttributeParsers(HashMap<String, XMLAttributeParser> to) {
        to.put(NAME, (XMLAttributeParser) (Attribute attribute, Element parent) -> {
            name = attribute.getValue();
        });
        return super.addAttributeParsers(to);
    }

    @Override
    public XMLParseException onParsingDone() {
        if(initFrom == null) {
            return new XMLParseException("image without init_from");
        }
        if(name == null) {
            return new XMLParseException("image without name");
        }
        return super.onParsingDone();
    }
    
    public ColladaTexture.Image getImage() {
        return new ColladaTexture.Image(name, initFrom);
    }
}
