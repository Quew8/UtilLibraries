package com.quew8.geng.xmlparser;

import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
public abstract class XMLFloatAttributeParser extends XMLAttributeParser {
    
    public abstract void parse(float value, Element parent);
    
    @Override
    public void parse(Attribute attribute, Element parent) {
        parse(Float.parseFloat(attribute.getValue()), parent);
    }
    
}
