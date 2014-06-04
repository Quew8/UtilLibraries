package com.quew8.geng.xmlparser;

import com.quew8.gutils.ResourceLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
public abstract class XMLParser {
    private Element element;
    private String id;
    private XMLMemory.XMLElementMemory memory;
    private final boolean matchAllElements;
    private final boolean matchAllAttributes;
    private ArrayList<String> requiredElements;
    private ArrayList<String> requiredAttributes;
    
    public XMLParser(ArrayList<String> requiredElements, ArrayList<String> requiredAttributes, 
            boolean matchAllElements, boolean matchAllAttributes) {
        
        this.matchAllElements = matchAllElements;
        this.matchAllAttributes = matchAllAttributes;
        this.requiredElements = requiredElements;
        this.requiredAttributes = requiredAttributes;
    }
    
    public XMLParser(String[] requiredElements, String[] requiredAttributes, 
            boolean matchAllElements, boolean matchAllAttributes) {
        
        this(new ArrayList<String>(Arrays.asList(requiredElements)), 
                new ArrayList<String>(Arrays.asList(requiredAttributes)),
                matchAllElements, matchAllAttributes
        );
    }
    
    public XMLParser(boolean matchAllElements, boolean matchAllAttributes) {
        this(new ArrayList<String>(), new ArrayList<String>(), matchAllElements, matchAllAttributes);
    }
    
    public XMLParser() {
        this(true, true);
    }
    
    public HashMap<String, XMLAttributeParser> addAttributeParsers(HashMap<String, XMLAttributeParser> to) {
        to.put("id", new XMLAttributeParser() {
            @Override
            public void parse(Attribute attribute, Element parent) {
                XMLParser.this.id = attribute.getValue();
            }
        });
        return to;
    }

    public HashMap<String, XMLElementParser> addElementParsers(HashMap<String, XMLElementParser> to) {
        return to;
    }

    public HashMap<String, XMLAttributeParser> getAttributeParsers() {
        return addAttributeParsers(new HashMap<String, XMLAttributeParser>());
    }

    public HashMap<String, XMLElementParser> getElementParsers() {
        return addElementParsers(new HashMap<String, XMLElementParser>());
    }

    public <K extends XMLParser> K parseWith(Element element, K parser) {
        return memory.parseElementWith(element, parser);
    }

    public <K extends XMLParser> K parseWith(String targetURL, K parser) {
        return parseWith(findTarget(targetURL), parser);
    }
    
    public void parse(Element element) {
        XMLAttributeParser.parseAttributes(element.attributes(), element, getAttributeParsers(), requiredAttributes, matchAllAttributes);
        XMLElementParser.parseElements(element.elements(), getElementParsers(), requiredElements, matchAllElements);
    }

    public Element findTarget(String targetURL) {
        return memory.findTarget(targetURL);
    }
    
    void read(Element element, XMLMemory.XMLElementMemory memory) {
        this.element = element;
        this.memory = memory;
        parse(element);
    }

    public void read(String resource, ResourceLoader resourceLoader) {
        try {
            XMLMemory mainMemory = new XMLMemory(resourceLoader);
            memory = mainMemory.findRootMemory(resource);
            read(memory.getElement(), memory);
        } catch(XMLParseException ex) {
            throw new XMLParseException("Syntax Exception In \"" + resource + "\"", ex);
        }
    }

    public void read(String resource) {
        read(resource, ResourceLoader.INTERNAL);
    }
    
    public String getId() {
        return id;
    }
    
    public void hasRequiredElement(String element) {
        requiredElements.remove(element);
    }
    
    public void hasRequiredAttribute(String attribute) {
        requiredAttributes.remove(attribute);
    }
    
    public boolean checkHasRequiredAttributes() {
        return requiredAttributes.isEmpty();
    }
    
    public boolean checkHasRequiredElements() {
        return requiredElements.isEmpty();
    }
    
    public ArrayList<String> getMissingElements() {
        return requiredElements;
    }
    
    public ArrayList<String> getMissingAttributes() {
        return requiredAttributes;
    }
    
    public void setRequiredElements(ArrayList<String> requiredElements) {
        this.requiredElements = requiredElements;
    }
    
    public void setRequiredAttributes(ArrayList<String> requiredAttributes) {
        this.requiredAttributes = requiredAttributes;
    }
    
    public void finalized() {
        String msg = "Syntax Exception In \"" + element.getUniquePath() + "\"\nMissing the following:";
        if(!checkHasRequiredElements()) {
            msg += "Elements:";
            for (String requiredElement : requiredElements) {
                msg += "\n    " + requiredElement;
            }
        }
        if(!checkHasRequiredAttributes()) {
            msg += "Attributes:";
            for (String requiredAttribute : requiredAttributes) {
                msg += "\n    " + requiredAttribute;
            }
        }
        if(!checkHasRequiredElements() || !checkHasRequiredAttributes()) {
            throw new XMLParseException(msg);
        }
    }
    
}
