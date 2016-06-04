package com.quew8.geng.xmlparser;

import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
public interface XMLFloatAttributeParser extends XMLAttributeParser {
    
    public void parse(float value, Element parent);
    
    @Override
    default void parse(Attribute attribute, Element parent) {
        parse(Float.parseFloat(attribute.getValue()), parent);
    }
    
}
