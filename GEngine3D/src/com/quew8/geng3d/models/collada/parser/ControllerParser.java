package com.quew8.geng3d.models.collada.parser;

import com.quew8.geng.geometry.Image;
import com.quew8.geng3d.models.collada.Node;
import com.quew8.geng.xmlparser.XMLAttributeParser;
import com.quew8.geng.xmlparser.XMLElementParser;
import com.quew8.geng.xmlparser.XMLParser;
import com.quew8.geng3d.models.DataFactory;
import java.util.HashMap;
import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
public class ControllerParser extends XMLParser {
    private static final String
            NAME = "name";
    private static final String
            SKIN = "skin";
    
    private String name;
    private SkinParser skin;
    
    @Override
    public HashMap<String, XMLAttributeParser> addAttributeParsers(HashMap<String, XMLAttributeParser> to) {
        to = super.addAttributeParsers(to);
        to.put(NAME, (XMLAttributeParser) (Attribute attribute, Element parent) -> {
            name = attribute.getValue();
        });
        return to;
    }
    
    @Override
    public HashMap<String, XMLElementParser> addElementParsers(HashMap<String, XMLElementParser> to) {
        to = super.addElementParsers(to);
        to.put(SKIN, (XMLElementParser) (Element element) -> {
            skin = ControllerParser.this.parseWith(element, new SkinParser());
        });
        return to;
    }
    
    public String getName() {
        return name;
    }
    
    public <T> T getSkin(Node[] joints, DataFactory<?, ?, ?, T> factory, Image img) {
        return skin.getSkin(joints, factory, img);
    } 
}
