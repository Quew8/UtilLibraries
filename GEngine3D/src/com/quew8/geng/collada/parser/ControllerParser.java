package com.quew8.geng.collada.parser;

import com.quew8.geng.collada.DataFactory;
import com.quew8.geng.collada.Node;
import com.quew8.geng.geometry.TextureArea;
import com.quew8.geng.xmlparser.XMLAttributeParser;
import com.quew8.geng.xmlparser.XMLElementParser;
import com.quew8.geng.xmlparser.XMLParser;
import java.util.HashMap;
import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
class ControllerParser extends XMLParser {
    private static final String
            NAME = "name";
    private static final String
            SKIN = "skin";
    
    private String name;
    private SkinParser skin;
    
    @Override
    public HashMap<String, XMLAttributeParser> addAttributeParsers(HashMap<String, XMLAttributeParser> to) {
        to = super.addAttributeParsers(to);
        to.put(NAME, new XMLAttributeParser() {

            @Override
            public void parse(Attribute attribute, Element parent) {
                name = attribute.getValue();
            }
            
        });
        return to;
    }
    
    @Override
    public HashMap<String, XMLElementParser> addElementParsers(HashMap<String, XMLElementParser> to) {
        to = super.addElementParsers(to);
        to.put(SKIN, new XMLElementParser() {
            
            @Override
            public void parse(Element element) {
                skin = ControllerParser.this.parseWith(element, new SkinParser());
            }
            
        });
        return to;
    }
    
    public String getName() {
        return name;
    }
    
    public <T> T getSkin(Node<Void, Void>[] joints, DataFactory<?, ?, ?, T> factory, TextureArea texture) {
        return skin.getSkin(joints, factory, texture);
    } 
}
