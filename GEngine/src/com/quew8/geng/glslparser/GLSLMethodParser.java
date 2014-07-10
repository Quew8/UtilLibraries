package com.quew8.geng.glslparser;

import com.quew8.codegen.glsl.Block;
import com.quew8.codegen.glsl.Method;
import com.quew8.codegen.glsl.Parameter;
import com.quew8.codegen.glsl.Type;
import com.quew8.geng.glslparser.GLSLShaderParser.GLSLElements;
import com.quew8.geng.xmlparser.XMLAttributeParser;
import com.quew8.geng.xmlparser.XMLElementParser;
import java.util.ArrayList;
import java.util.HashMap;
import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
public class GLSLMethodParser extends GLSLElementStructure<GLSLMethodParser> {
    private String name;
    private String code;
    private Type returnType = null;
    private final ArrayList<GLSLVariableParser> params = new ArrayList<GLSLVariableParser>();
    
    public Method getMethod(GLSLElements elements) {
        addTo(elements);
        Parameter[] parameters = new Parameter[params.size()];
        for(int i = 0; i < params.size(); i++) {
            parameters[i] = params.get(i).getParameter();
        }
        return new Method(name, new Block(code))
                .setReturnType(returnType)
                .setParameters(parameters);
    }
    
    @Override
    public HashMap<String, XMLElementParser> addElementParsers(HashMap<String, XMLElementParser> to) {
        super.addElementParsers(to);
        to.put(CODE, new XMLElementParser() {
            @Override
            public void parse(Element element) {
                code = element.getText();
            }
        }
        );
        to.put(VARIABLE, new XMLElementParser() {
            @Override
            public void parse(Element element) {
                GLSLVariableParser varParser = GLSLMethodParser.this.parseWith(element, new GLSLVariableParser());
                if(varParser.isGlobalVariable()) {
                    addGlobalVariable(varParser);
                } else if(varParser.isInputVariable()) {
                    params.add(varParser);
                } else {
                    throw new RuntimeException("Invalid method variable semantic: " + varParser.getSemantic());
                }
            }
        }
        );
        return to;
    }

    @Override
    public HashMap<String, XMLAttributeParser> addAttributeParsers(HashMap<String, XMLAttributeParser> to) {
        super.addAttributeParsers(to);
        to.put(NAME, new XMLAttributeParser() {
            @Override
            public void parse(Attribute attribute, Element parent) {
                name = attribute.getValue();
            }
        });
        to.put(RETURN_TYPE, new XMLAttributeParser() {
            @Override
            public void parse(Attribute attribute, Element parent) {
                returnType = new Type(attribute.getText());
            }
        });
        return to;
    }
    
    @Override
    public void setSource(GLSLMethodParser source) {
        super.setSource(source);
        this.name = source.name;
        this.code = source.code;
        this.returnType = source.returnType;
        this.params.addAll(source.params);
    }
    
    @Override
    public GLSLMethodParser getInstance() {
        return new GLSLMethodParser();
    }
}
