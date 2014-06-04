package com.quew8.geng.content.parser;

import com.quew8.geng.xmlparser.XMLAttributeParser;
import com.quew8.geng.xmlparser.XMLParser;
import java.util.HashMap;
import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
public class ParamParser extends XMLParser {
    private static final String 
            KEY = "key",
            VALUE = "val";
    
    private String key, value;
    
    @Override
    public HashMap<String, XMLAttributeParser> addAttributeParsers(HashMap<String, XMLAttributeParser> to) {
        to = super.addAttributeParsers(to);
        to.put(KEY, new XMLAttributeParser() {
            
            @Override
            public void parse(Attribute attribute, Element parent) {
                key = attribute.getValue();
            }
            
        });
        to.put(VALUE, new XMLAttributeParser() {
            
            @Override
            public void parse(Attribute attribute, Element parent) {
                value = attribute.getValue();
            }
            
        });
        return to;
    }
    
    public String getKey() {
        return key;
    }
    
    public String getValue() {
        return value;
    }
}
