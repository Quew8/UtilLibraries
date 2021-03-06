package com.quew8.glslparser;

import com.quew8.codegen.glsl.Block;
import com.quew8.codegen.glsl.Method;
import com.quew8.codegen.glsl.Parameter;
import com.quew8.codegen.glsl.Type;
import com.quew8.glslparser.GLSLShaderParser.GLSLElements;
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
        to.put(CODE, (XMLElementParser) (Element element) -> {
            code = element.getText();
        });
        to.put(VARIABLE, (XMLElementParser) (Element element) -> {
            GLSLVariableParser varParser = GLSLMethodParser.this.parseWith(element, new GLSLVariableParser());
            if(varParser.isGlobalVariable()) {
                addGlobalVariable(varParser);
            } else if(varParser.isInputVariable()) {
                params.add(varParser);
            } else {
                throw new RuntimeException("Invalid method variable semantic: " + varParser.getSemantic());
            }
        });
        return to;
    }

    @Override
    public HashMap<String, XMLAttributeParser> addAttributeParsers(HashMap<String, XMLAttributeParser> to) {
        super.addAttributeParsers(to);
        to.put(NAME, (XMLAttributeParser) (Attribute attribute, Element parent) -> {
            name = attribute.getValue();
        });
        to.put(RETURN_TYPE, (XMLAttributeParser) (Attribute attribute, Element parent) -> {
            returnType = new Type(attribute.getText());
        });
        return to;
    }

    @Override
    public XMLParseException onParsingDone() {
        if(name == null || name.isEmpty()) {
            return new XMLParseException("Name attribute is empty in method");
        }
        if(code == null || code.isEmpty()) {
            return new XMLParseException("Method has no code");
        }
        return super.onParsingDone();
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
