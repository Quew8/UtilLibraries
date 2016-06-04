package com.quew8.geng3d.models.collada.parser;

import com.quew8.geng.xmlparser.XMLAttributeParser;
import com.quew8.geng.xmlparser.XMLElementParser;
import com.quew8.geng.xmlparser.XMLIntAttributeParser;
import com.quew8.geng.xmlparser.XMLParser;
import java.util.ArrayList;
import java.util.HashMap;
import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
class InstanceMaterialParser extends XMLParser {
    private static final String
            SYMBOL = "symbol",
            TARGET = "target";
    private static final String
            BIND_VERTEX_INPUT = "bind_vertex_input";
    
    private String symbol;
    private MaterialParser material;
    private final ArrayList<BindVertexInputParser> bindVertexInputs = new ArrayList<BindVertexInputParser>();
    
    @Override
    public HashMap<String, XMLAttributeParser> addAttributeParsers(HashMap<String, XMLAttributeParser> to) {
        to = super.addAttributeParsers(to);
        to.put(SYMBOL, (XMLAttributeParser) (Attribute attribute, Element parent) -> {
            symbol = attribute.getValue();
        });
        to.put(TARGET, (XMLAttributeParser) (Attribute attribute, Element parent) -> {
            material = InstanceMaterialParser.this.parseWith(attribute.getValue(), new MaterialParser());
        });
        return to;
    }
    
    @Override
    public HashMap<String, XMLElementParser> addElementParsers(HashMap<String, XMLElementParser> to) {
        to = super.addElementParsers(to);
        to.put(BIND_VERTEX_INPUT, (XMLElementParser) (Element element) -> {
            bindVertexInputs.add(InstanceMaterialParser.this.parseWith(element, new BindVertexInputParser()));
        });
        return to;
    }
    
    static class BindVertexInputParser extends XMLParser {
        private static final String
                SEMANTIC = "semantic",
                IN_SEMANTIC = "input_semantic",
                IN_SET = "input_set";
    
        private String semantic;
        private String inSemantic;
        private int inSet;
        
        @Override
        public HashMap<String, XMLAttributeParser> addAttributeParsers(HashMap<String, XMLAttributeParser> to) {
            to = super.addAttributeParsers(to);
            to.put(SEMANTIC, (XMLAttributeParser) (Attribute attribute, Element parent) -> {
                semantic = attribute.getValue();
            });
            to.put(IN_SEMANTIC, (XMLAttributeParser) (Attribute attribute, Element parent) -> {
                inSemantic = attribute.getValue();
            });
            to.put(IN_SET, (XMLIntAttributeParser) (int value, Element parent) -> {
                inSet = value;
            });
            return to;
        }
    }
    
}
