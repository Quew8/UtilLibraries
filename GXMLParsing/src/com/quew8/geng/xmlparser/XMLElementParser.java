package com.quew8.geng.xmlparser;

import java.util.HashMap;
import java.util.List;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
public interface XMLElementParser {
    
    public void parse(Element element);
    
    public static void parseElements(List<Element> elements, HashMap<String, XMLElementParser> parsers, 
            boolean matchAll) {
        
        for(Element element: elements) {
            XMLElementParser parser = parsers.get(element.getName());
            if(parser != null) {
                parser.parse(element);
            } else if(matchAll) {
                throw new XMLParseException("Unknown Element: " + element.getUniquePath());
            }
        }
    }
}
