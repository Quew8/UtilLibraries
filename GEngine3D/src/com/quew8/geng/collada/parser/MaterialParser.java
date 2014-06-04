package com.quew8.geng.collada.parser;

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
class MaterialParser extends XMLParser {
    private static final String
            NAME = "name",
            URL = "url";
    private static final String
            INSTANCE_EFFECT = "instance_effect";
    
    private String name;
    private EffectParser effect;
    
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
        to.put(INSTANCE_EFFECT, new XMLElementParser() {
            
            @Override
            public void parse(Element element) {
                effect = MaterialParser.this.parseWith(element.attributeValue(URL), new EffectParser());
            }
            
        });
        return to;
    }
}