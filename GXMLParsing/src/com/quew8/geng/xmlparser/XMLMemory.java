package com.quew8.geng.xmlparser;

import com.quew8.gutils.GeneralUtils;
import com.quew8.gutils.ResourceLoader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
import org.dom4j.Attribute;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 *
 * @author Quew8
 */
class XMLMemory {
    private static final HashMap<String, XMLElementMemory> roots = new HashMap<String, XMLElementMemory>();
    private final ResourceLoader resourceLoader;

    XMLMemory(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
    
    XMLMemory() {
        this(ResourceLoader.INTERNAL);
    }
    
    public Element findRoot(String resourcePath) {
        return findRootMemory(resourcePath).getElement();
    }
    
    protected XMLElementMemory findRootMemory(String resourcePath) {
        XMLElementMemory memory = roots.get(resourcePath);
        if(memory == null) {
            memory = new XMLElementMemory(
                    loadElement(resourcePath),
                    GeneralUtils.getResourceParent(resourcePath)
            );
            roots.put(resourcePath, memory);
        }
        return memory;
    }
    
    /**
     * 
     */
    protected class XMLElementMemory {
        private final String directory; 
        private final Element element;
        private XMLParser parsedElement = null;
        private final HashMap<String, XMLElementMemory> elements = new HashMap<String, XMLElementMemory>();
        
        XMLElementMemory(Element element, String directory) {
            this.directory = directory;
            this.element = element;
        }
        
        XMLElementMemory(Element element) {
            this(element, null);
        }
        
        @SuppressWarnings("unchecked")
        public <K extends XMLParser> K parse(K parser) {
            if(parsedElement != null) {
                parser = (K) parsedElement;
            } else {
                parser.read(element, this);
                parsedElement = parser;
            }
            return parser;
        }
        
        public <K extends XMLParser> K parseElementWith(Element element, K parser) {
            String elementId = element.attributeValue("id");
            if(elementId != null) {
                XMLElementMemory mem = elements.get(elementId);
                if(mem == null) {
                    mem = new XMLElementMemory(element);
                    elements.put(elementId, mem);
                }
                return mem.parse(parser);
            } else {
                parser.read(element, this);
                return parser;
            }
        }
        
        public Element getElement() {
            return element;
        }
        
        public Element findTarget(String target) {
            return findTargetMemory(target).getElement();
        }
        
        protected XMLElementMemory findTargetMemory(String target) {
            String[] sections = target.split(Pattern.quote("#"));
            XMLMemory.XMLElementMemory toFindIn = this;
            if(!sections[0].isEmpty()) {
                toFindIn = findRootMemory(sections[0]);
            }
            if(sections.length > 1) {
                return toFindIn.findElementMemory(sections[1]);
            } else {
                return toFindIn;
            }
        }
        
        protected XMLElementMemory findRootMemory(String path) {
            return XMLMemory.this.findRootMemory(directory + path);
        }
        
        private XMLElementMemory findElementMemory(String id) {
            XMLElementMemory memory = elements.get(id);
            if(memory == null) {
                memory = new XMLElementMemory(searchForElement(getRootOf(element), "id", id));
                elements.put(id, memory);
            }
            return memory;
        }
        
        public Element findElement(String id) {
            return findElementMemory(id).getElement();
        }
        
        public void addElement(String id, Element element) {
            elements.put(id, new XMLElementMemory(element));
        }
    }
    
    private Element loadElement(String resourcePath) {
        try {
            return new SAXReader().read(resourceLoader.throwableLoad(resourcePath)).getRootElement();
        } catch(DocumentException ex) {
            throw new XMLParseException("Could not parse file", ex);
        } catch(IOException ex) {
            throw new XMLParseException("Could not load file", ex);
        }
    }
    
    private static Element getRootOf(Element element) {
        Element root = element;
        while(root.getParent() != null) {
            root = root.getParent();
        }
        return root;
    }
    
    private static Element searchForElement(Element in, String searchForAttribute, String searchForValue) {
        List<Element> elements = in.elements();
        for (Element element : elements) {
            Attribute id = element.attribute(searchForAttribute);
            if (id != null && id.getValue().equals(searchForValue)) {
                return element;
            }
            Element e = searchForElement(element, searchForAttribute, searchForValue);
            if(e != null) {
                return e;
            }
        }
        return null;
    }
}
