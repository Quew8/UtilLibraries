package com.quew8.geng3d.models.collada.parser;

import com.quew8.geng3d.models.collada.InstanceVisualScene;
import com.quew8.geng3d.models.collada.Node;
import com.quew8.geng.xmlparser.XMLAttributeParser;
import com.quew8.geng.xmlparser.XMLElementParser;
import com.quew8.geng.xmlparser.XMLParser;
import java.util.ArrayList;
import java.util.HashMap;
import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
class VisualSceneParser extends XMLParser {
    private static final String
            NAME = "name";
    private static final String
            NODE = "node";
    
    private String name;
    private final ArrayList<NodeParser> nodes = new ArrayList<NodeParser>();
    
    @Override
    public HashMap<String, XMLAttributeParser> addAttributeParsers(HashMap<String, XMLAttributeParser> to) {
        to = super.addAttributeParsers(to);
        to.put(NAME, new XMLAttributeParser() {

            @Override
            public void parse(Attribute attribute, Element parent) {
                name = attribute.getValue();
            }
            
        });
        return to;
    }
    
    @Override
    public HashMap<String, XMLElementParser> addElementParsers(HashMap<String, XMLElementParser> to) {
        to = super.addElementParsers(to);
        to.put(NODE, new XMLElementParser() {
            
            @Override
            public void parse(Element element) {
                nodes.add(VisualSceneParser.this.parseWith(element, new NodeParser(null)));
            }
            
        });
        return to;
    }
    
    public InstanceVisualScene getVisualScene() {
        @SuppressWarnings("unchecked")
        Node[] children = new Node[nodes.size()];
        for(int i = 0; i < children.length; i++) {
            children[i] = nodes.get(i).getNode();
        }
        return new InstanceVisualScene(name, children);
    }
}
