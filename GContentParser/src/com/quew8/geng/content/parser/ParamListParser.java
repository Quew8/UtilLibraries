package com.quew8.geng.content.parser;

import com.quew8.geng.xmlparser.XMLAttributeParser;
import com.quew8.geng.xmlparser.XMLElementParser;
import com.quew8.geng.xmlparser.XMLParser;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
public class ParamListParser extends XMLParser {
    private static final String 
            KEY = "key",
            PARAM = "param";

    private String key;
    private final ArrayList<Entry<String, String>> params = new ArrayList<Entry<String, String>>();
    
    @Override
    public HashMap<String, XMLAttributeParser> addAttributeParsers(HashMap<String, XMLAttributeParser> to) {
        to = super.addAttributeParsers(to);
        to.put(KEY, new XMLAttributeParser() {
            
            @Override
            public void parse(Attribute attribute, Element parent) {
                key = attribute.getValue();
            }
            
        });
        return to;
    }
    
    @Override
    public HashMap<String, XMLElementParser> addElementParsers(HashMap<String, XMLElementParser> to) {
        to = super.addElementParsers(to);
        to.put(PARAM, new XMLElementParser() {
            
            @Override
            public void parse(Element element) {
                ParamParser p = ParamListParser.this.parseWith(element, new ParamParser());
                params.add(new SimpleEntry<String, String>(p.getKey(), p.getValue()));
            }
            
        });
        return to;
    }
    
    public String getKey() {
        return key;
    }
    
    @SuppressWarnings("unchecked")
    public Entry<String, String>[] getParams() {
        return params.toArray(ParamListParser.<Entry<String, String>>getArray(params.size()));
    }
    
    @SuppressWarnings("unchecked")
    private static <T> T[] getArray(int length, T... array) {
        return Arrays.copyOf(array, length);
    }
}
