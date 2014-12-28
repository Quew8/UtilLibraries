package com.quew8.geng.content.parser.xml;

import com.quew8.geng.xmlparser.XMLAttributeParser;
import com.quew8.geng.xmlparser.XMLIntAttributeParser;
import com.quew8.geng.xmlparser.XMLParser;
import java.util.HashMap;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
class XMLInputParser extends XMLParser {
    public static final String INDEX = "index";
    
    private int index = -1;
    private String source;
    
    @Override
    public void parse(Element element) {
        super.parse(element);
        this.source = element.getText().trim();
    }

    @Override
    public HashMap<String, XMLAttributeParser> addAttributeParsers(HashMap<String, XMLAttributeParser> to) {
        to = super.addAttributeParsers(to);
        to.put(INDEX, new XMLIntAttributeParser() {

            @Override
            public void parse(int value, Element parent) {
                index = value;
            }
            
        });
        return to;
    }
    
    public int getIndex() {
        return index;
    }
    
    public String getSource() {
        return source;
    }
}
