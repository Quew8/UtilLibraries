package com.quew8.geng.xmlparser;

import org.dom4j.Element;

/**
 *
 * @author William
 */
public class XMLTextParser extends XMLParser {
    private String text;

    @Override
    public void parse(Element element) {
        this.text = element.getText();
        super.parse(element);
    }
    
    public String getText() {
        return text;
    }
}
