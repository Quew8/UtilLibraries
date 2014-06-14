package com.quew8.geng3d.collada.parser;

import com.quew8.geng.xmlparser.XMLAttributeParser;
import com.quew8.geng.xmlparser.XMLParser;
import java.util.HashMap;
import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
abstract class ArrayParser extends XMLParser {
    private static final 
            String COUNT = "count";
    
    private int count;
    protected Object[] data;

    @Override
    public HashMap<String, XMLAttributeParser> addAttributeParsers(HashMap<String, XMLAttributeParser> to) {
        to = super.addAttributeParsers(to);
        to.put(COUNT, new XMLAttributeParser() {
            @Override
            public void parse(Attribute attribute, Element parent) {
                count = Integer.parseInt(attribute.getValue());
            }
        });
        return to;
    }
    
    protected int getCount() {
        return count;
    }

    protected Object get(int i) {
        return data[i];
    }
    
    static class FloatArrayParser extends ArrayParser {

        @Override
        public void parse(Element element) {
            super.parse(element);
            Float[] fData = new Float[getCount()];
            CollParseUtils.parseFloatsInto(element.getText(), fData, 0, getCount());
            data = fData;
        }
    }
    
    static class NameArrayParser extends ArrayParser {

        @Override
        public void parse(Element element) {
            super.parse(element);
            String[] sData = new String[getCount()];
            CollParseUtils.parseStringsInto(element.getText(), sData, 0, getCount());
            data = sData;
        }
    }
}
