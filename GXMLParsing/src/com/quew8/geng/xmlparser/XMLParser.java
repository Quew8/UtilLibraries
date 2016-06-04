package com.quew8.geng.xmlparser;

import com.quew8.gutils.ResourceLoader;
import java.io.File;
import java.util.HashMap;
import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
public abstract class XMLParser {
    private String id;
    private Element element;
    private XMLMemory.XMLElementMemory memory;
    private final boolean matchAllElements;
    private final boolean matchAllAttributes;
    
    public XMLParser(boolean matchAllElements, boolean matchAllAttributes) {
        this.matchAllElements = matchAllElements;
        this.matchAllAttributes = matchAllAttributes;
    }
    
    public XMLParser() {
        this(true, true);
    }
    
    public Element getElement() {
        return element;
    }
    
    public HashMap<String, XMLAttributeParser> addAttributeParsers(HashMap<String, XMLAttributeParser> to) {
        to.put("id", (XMLAttributeParser) (Attribute attribute, Element parent) -> {
            XMLParser.this.id = attribute.getValue();
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
        Element elem = findTarget(targetURL);
        if(elem == null) {
            throw new XMLParseException("No element found with url \"" + targetURL + "\"");
        }
        return parseWith(elem, parser);
    }
    
    public void parse(Element element) {
        this.element = element;
        XMLAttributeParser.parseAttributes(element.attributes(), element, getAttributeParsers(), matchAllAttributes);
        XMLElementParser.parseElements(element.elements(), getElementParsers(), matchAllElements);
        XMLParseException ex = onParsingDone();
        if(ex != null) {
            throw new XMLParseException("Syntax Exception In \"" + element.getUniquePath() + "\"", ex);
        }
    }

    public Element findTarget(String targetURL) {
        return memory.findTarget(targetURL);
    }
    
    void read(Element element, XMLMemory.XMLElementMemory memory) {
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
    
    public void read(File file) {
        read(file.getAbsolutePath(), ResourceLoader.EXTERNAL);
    }
    
    public String getId() {
        return id;
    }
    
    public XMLParseException onParsingDone() {
        return null;
    }
}
