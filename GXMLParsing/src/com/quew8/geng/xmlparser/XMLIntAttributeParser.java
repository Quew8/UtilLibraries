package com.quew8.geng.xmlparser;

import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
public interface XMLIntAttributeParser extends XMLAttributeParser {

    public void parse(int value, Element parent);
    
    @Override
    default void parse(Attribute attribute, Element parent) {
        parse(Integer.parseInt(attribute.getValue()), parent);
    }
    
}
