package com.quew8.geng.content.parser;

import com.quew8.geng.xmlparser.XMLAttributeParser;
import com.quew8.geng.xmlparser.XMLElementParser;
import com.quew8.geng.xmlparser.XMLParser;
import com.quew8.gutils.content.Source;
import java.util.ArrayList;
import java.util.HashMap;
import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
public class GroupParser extends XMLParser {
    private static final String 
            SOURCE = "source";
    private static final String
            DIR = "dir";
    
    private String dir = "";
    private final ArrayList<Source> sources = new ArrayList<Source>();
    
    @Override
    public HashMap<String, XMLElementParser> addElementParsers(HashMap<String, XMLElementParser> to) {
        to = super.addElementParsers(to);
        to.put(SOURCE, new XMLElementParser() {
            
            @Override
            public void parse(Element element) {
                SourceParser sourceParser = GroupParser.this.parseWith(element, new SourceParser(dir));
                sources.add(sourceParser.getSource());
            }
            
        });
        return to;
    }
    
    @Override
    public HashMap<String, XMLAttributeParser> addAttributeParsers(HashMap<String, XMLAttributeParser> to) {
        to = super.addAttributeParsers(to);
        to.put(DIR, new XMLAttributeParser() {

            @Override
            public void parse(Attribute attribute, Element parent) {
                dir = attribute.getValue();
            }
            
        });
        return to;
    }
    
    public ArrayList<Source> getSources() {
        return sources;
    }
}
