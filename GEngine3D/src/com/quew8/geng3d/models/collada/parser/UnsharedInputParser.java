package com.quew8.geng3d.models.collada.parser;

import com.quew8.geng3d.models.Semantic;
import com.quew8.geng.xmlparser.XMLAttributeParser;
import com.quew8.geng.xmlparser.XMLParser;
import java.util.HashMap;
import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
class UnsharedInputParser extends XMLParser {
    private static final String 
            SEMANTIC = "semantic",
            SOURCE = "source";
    
    private DataSource source;
    private String semantic;
    
    @Override
    public HashMap<String, XMLAttributeParser> addAttributeParsers(HashMap<String, XMLAttributeParser> to) {
        to = super.addAttributeParsers(to);
        to.put(SOURCE, (XMLAttributeParser) (Attribute attribute, Element parent) -> {
            Element element = UnsharedInputParser.this.findTarget(attribute.getValue());
            if(element.getName().matches("vertices")) {
                source = UnsharedInputParser.this.parseWith(element, new VerticesParser());
            } else {
                source = UnsharedInputParser.this.parseWith(element, new SourceParser());
            }
        });
        to.put(SEMANTIC, (XMLAttributeParser) (Attribute attribute, Element parent) -> {
            semantic = attribute.getValue();
        });
        return to;
    }
    
    public Semantic getSemantic() {
        return Semantic.valueOf(semantic);
    }
    
    protected DataSource getSource() {
        return source;
    }
    
    protected int getCount() {
        return getSource().getCount();
    }
}
