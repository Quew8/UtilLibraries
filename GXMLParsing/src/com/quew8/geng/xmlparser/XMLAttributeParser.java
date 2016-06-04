package com.quew8.geng.xmlparser;

import java.util.HashMap;
import java.util.List;
import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
public interface XMLAttributeParser {
    
    public void parse(Attribute attribute, Element parent);
    
    public static void parseAttributes(List<Attribute> attributes, Element parent, 
            HashMap<String, XMLAttributeParser> parsers, boolean matchAll) {
        
        for(Attribute attribute : attributes) {
            XMLAttributeParser parser = parsers.get(attribute.getName());
            if(parser != null) {
                parser.parse(attribute, parent);
            } else if(matchAll) {
                throw new XMLParseException("Unknown Attribute: " + attribute.getUniquePath());
            }
        }
    }
}
