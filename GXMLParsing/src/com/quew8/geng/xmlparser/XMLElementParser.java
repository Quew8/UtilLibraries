package com.quew8.geng.xmlparser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
public abstract class XMLElementParser {
    
    public abstract void parse(Element element);
    
    public static void parseElements(List<Element> elements, HashMap<String, XMLElementParser> parsers, 
            ArrayList<String> requiredElements, boolean matchAll) {
        
        for(Element element: elements) {
            XMLElementParser parser = parsers.get(element.getName());
            requiredElements.remove(element.getName());
            if(parser != null) {
                parser.parse(element);
            } else if(matchAll) {
                throw new XMLParseException("Unknown Element: " + element.getUniquePath());
            }
        }
    }
}
