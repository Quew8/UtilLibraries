package com.quew8.geng.glslparser;

import com.quew8.geng.xmlparser.XMLAttributeParser;
import com.quew8.geng.xmlparser.XMLElementParser;
import com.quew8.gutils.opengl.shaders.glsl.GLSLStruct;
import com.quew8.gutils.opengl.shaders.glsl.GLSLVariable;
import java.util.ArrayList;
import java.util.HashMap;
import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
public class GLSLStructParser extends GLSLParser<GLSLStructParser> {
    private String name;
    private final ArrayList<GLSLVariable> variables = new ArrayList<GLSLVariable>();
    
    public GLSLStructParser() {
        super(new String[]{}, new String[]{NAME});
    }
    
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
        to.put(VARIABLE, new XMLElementParser() {
            @Override
            public void parse(Element element) {
                GLSLVariableParser parser = GLSLStructParser.this.parseWith(element, new GLSLVariableParser());
                if(parser.isMemberVariable()) {
                    variables.add(parser.getVariable());
                } else {
                    throw new RuntimeException("Invalid struct variable semantic: " + parser.getSemantic());
                }
            }
        });
        return to;
    }
    
    @Override
    public GLSLStructParser getInstance() {
        return new GLSLStructParser();
    }
    
    @Override
    public void setSource(GLSLStructParser source) {
        this.name = source.name;
        this.variables.addAll(source.variables);
    }
    
    public GLSLStruct getStruct() {
        finalized();
        return new GLSLStruct(name, variables.toArray(new GLSLVariable[]{}));
    }
}
