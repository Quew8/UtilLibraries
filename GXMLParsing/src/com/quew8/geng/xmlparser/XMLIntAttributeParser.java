package com.quew8.geng.xmlparser;

import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
public abstract class XMLIntAttributeParser extends XMLAttributeParser {

    public abstract void parse(int value, Element parent);
    
    @Override
    public void parse(Attribute attribute, Element parent) {
        parse(Integer.parseInt(attribute.getValue()), parent);
    }
    
}
