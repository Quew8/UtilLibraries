package com.quew8.glslparser;

import com.quew8.codegen.glsl.Struct;
import com.quew8.codegen.glsl.Variable;
import com.quew8.geng.xmlparser.XMLAttributeParser;
import com.quew8.geng.xmlparser.XMLElementParser;
import com.quew8.geng.xmlparser.XMLParseException;
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
    private final ArrayList<GLSLVariableParser> variables = new ArrayList<GLSLVariableParser>();
    
    @Override
    public HashMap<String, XMLAttributeParser> addAttributeParsers(HashMap<String, XMLAttributeParser> to) {
        to = super.addAttributeParsers(to);
        to.put(NAME, (XMLAttributeParser) (Attribute attribute, Element parent) -> {
            name = attribute.getValue();
        });
        return to;
    }
    
    @Override
    public HashMap<String, XMLElementParser> addElementParsers(HashMap<String, XMLElementParser> to) {
        to = super.addElementParsers(to);
        to.put(VARIABLE, (XMLElementParser) (Element element) -> {
            GLSLVariableParser parser = GLSLStructParser.this.parseWith(element, new GLSLVariableParser());
            if(parser.isMemberVariable()) {
                variables.add(parser);
            } else {
                throw new RuntimeException("Invalid struct variable semantic: " + parser.getSemantic());
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
    
    public Struct getStruct() {
        Variable[] vars = new Variable[variables.size()];
        for(int i = 0; i < vars.length; i++) {
            vars[i] = variables.get(i).getVariable();
        }
        return new Struct(name, vars);
    }

    @Override
    public XMLParseException onParsingDone() {
        if(name == null || name.isEmpty()) {
            throw new XMLParseException("Name attribute is empty in struct");
        }
        return super.onParsingDone();
    }
}
