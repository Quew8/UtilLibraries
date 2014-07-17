package com.quew8.geng.content.parser;

import com.quew8.geng.xmlparser.XMLAttributeParser;
import com.quew8.geng.xmlparser.XMLElementParser;
import com.quew8.geng.xmlparser.XMLParser;
import com.quew8.gutils.content.Source;
import java.util.HashMap;
import java.util.Map.Entry;
import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
public class SourceParser extends XMLParser {
    private static final String
            INPUT = "input",
            PARAM = "param",
            PARAM_LIST = "param_list";
    
    private String source;
    private final HashMap<String, String> params = new HashMap<String, String>();
    private final HashMap<String, Entry<String, String>[]> paramLists = new HashMap<String, Entry<String, String>[]>();
    
    public SourceParser(String dir) {
        this.source = dir;
    }
    
    public SourceParser() {
        this("");
    }

    @Override
    public HashMap<String, XMLAttributeParser> addAttributeParsers(HashMap<String, XMLAttributeParser> to) {
        to = super.addAttributeParsers(to);
        to.put(INPUT, new XMLAttributeParser() {
            
            @Override
            public void parse(Attribute attribute, Element parent) {
                source += attribute.getValue();
            }
            
        });
        return to;
    }
    
    @Override
    public HashMap<String, XMLElementParser> addElementParsers(HashMap<String, XMLElementParser> to) {
        to = super.addElementParsers(to);
        to.put(INPUT, new XMLElementParser() {
            
            @Override
            public void parse(Element element) {
                source += element.getText();
            }
            
        });
        to.put(PARAM, new XMLElementParser() {
            
            @Override
            public void parse(Element element) {
                ParamParser parser = SourceParser.this.parseWith(element, new ParamParser());
                params.put(parser.getKey(), parser.getValue());
            }
            
        });
        to.put(PARAM_LIST, new XMLElementParser() {
            
            @Override
            public void parse(Element element) {
                ParamListParser parser = SourceParser.this.parseWith(element, new ParamListParser());
                paramLists.put(parser.getKey(), parser.getParams());
            }
            
        });
        return to;
    }
    
    
    
    public Source getSource() {
        return new Source(source, params, paramLists);
    }
}
