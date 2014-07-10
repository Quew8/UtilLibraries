package com.quew8.geng.glslparser;

import com.quew8.geng.xmlparser.XMLAttributeParser;
import com.quew8.geng.xmlparser.XMLParser;
import java.util.HashMap;
import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 * @param <T>
 */
public abstract class GLSLParser<T extends GLSLParser<T>> extends XMLParser {
    public static final String 
            SHADER_PROGRAM = "shader_program", 
            SHADER = "shader", 
            PIPELINE = "pipeline",
            EFFECT = "effect", 
            CODE = "code",
            VARIABLE = "variable",
            METHOD = "method",
            STRUCT = "struct",
            VERSION = "version",
            EXTENSION = "extension",
            EXTRA = "extra";
    
    public static final String 
            NAME = "name", 
            TYPE = "type", 
            SEMANTIC = "semantic",
            MOD = "mod", 
            SRC = "src",
            PREDEFINED = "predefined",
            RETURN_TYPE = "returnType";
    
    @Override
    public void parse(Element element) {
        super.parse(element);
    }
    
    public void loadPredefined(Element element, String predefinedName) {
        throw new RuntimeException("No predefined " + element.getName() + " found.");
    }

    @Override
    public HashMap<String, XMLAttributeParser> addAttributeParsers(HashMap<String, XMLAttributeParser> to) {
        to = super.addAttributeParsers(to);
        to.put(SRC, new XMLAttributeParser() {
            @Override
            public void parse(Attribute attribute, Element parent) {
                T srcParser = GLSLParser.this.parseWith(attribute.getValue(), getInstance());
                GLSLParser.this.setSource(srcParser);
            }
        });
        to.put(PREDEFINED, new XMLAttributeParser() {
            @Override
            public void parse(Attribute attribute, Element parent) {
                GLSLParser.this.loadPredefined(parent, attribute.getValue());
            }
        });
        return to;
    }
    
    public abstract T getInstance();
    
    public abstract void setSource(T source);
}
