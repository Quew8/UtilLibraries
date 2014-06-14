package com.quew8.geng.glslparser;

import com.quew8.geng.xmlparser.XMLElementParser;
import com.quew8.geng.xmlparser.XMLAttributeParser;
import com.quew8.gutils.opengl.shaders.glsl.GLSLCompileTimeConstant;
import com.quew8.gutils.opengl.shaders.glsl.GLSLExtra;
import com.quew8.gutils.opengl.shaders.glsl.GLSLMethod;
import com.quew8.gutils.opengl.shaders.glsl.GLSLType;
import com.quew8.gutils.opengl.shaders.glsl.GLSLVariable;
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
    private GLSLType returnType = GLSLType.VOID;
    private final ArrayList<GLSLVariable> params = new ArrayList<GLSLVariable>();

    public GLSLMethodParser() {
        super(new String[]{CODE}, new String[]{NAME, RETURN_TYPE});
    }
    
    public GLSLMethod getMethod() {
        finalized();
        return new GLSLMethod(
                name, code, returnType, 
                params.toArray(new GLSLVariable[params.size()]),
                getGlobalVariables().toArray(new GLSLVariable[getGlobalVariables().size()]),
                getExtras().toArray(new GLSLExtra[getExtras().size()]),
                getConstants().toArray(new GLSLCompileTimeConstant[getConstants().size()])
        );
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
                    addGlobalVariable(varParser.getVariable());
                } else if(varParser.isInputVariable()) {
                    params.add(varParser.getVariable());
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
                returnType = new GLSLType(attribute.getText());
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
