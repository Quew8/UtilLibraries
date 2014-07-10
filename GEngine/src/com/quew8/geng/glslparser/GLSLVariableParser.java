package com.quew8.geng.glslparser;

import com.quew8.codegen.glsl.Modifier;
import com.quew8.codegen.glsl.Parameter;
import com.quew8.codegen.glsl.Type;
import com.quew8.codegen.glsl.Variable;
import com.quew8.geng.xmlparser.XMLAttributeParser;
import com.quew8.geng.xmlparser.XMLParseException;
import java.util.HashMap;
import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
public class GLSLVariableParser extends GLSLParser<GLSLVariableParser> {
    private String name;
    private Modifier mod = Modifier.NONE;
    private Type type;
    private String semantic;
    private Variable predefinedVariable = null;
    
    @Override
    public void loadPredefined(Element element, String predefinedName) {
        predefinedVariable = Variable.getBuiltInVariable(predefinedName);
        mod = predefinedVariable.getModifier();
        type = predefinedVariable.getType();
        name = predefinedVariable.getName();
    }
    
    public Parameter getParameter() {
        if(predefinedVariable == null) {
            return new Parameter(type, name);
        } else {
            throw new RuntimeException("Cannot Have Predefined Parameters");
        }
    }
    
    public Variable getVariable() {
        if(predefinedVariable == null) {
            return new Variable(mod, type, name);
        } else {
            return predefinedVariable;
        }
    }
    
    private void ensureEditable() {
        if(predefinedVariable != null) {
            throw new XMLParseException("Predefined variables cannot be modified");
        }
    }
    
    public String getSemantic() {
        return semantic;
    }
    
    public boolean compatible(GLSLVariableParser other) {
        return type.getName().matches(other.type.getName());
    }
    
    public boolean isInputVariable() {
        return semantic.equals("in_var");
    }
    
    public boolean isOutputVariable() {
        return semantic.equals("out_var");
    }
    
    public boolean isGlobalVariable() {
        return semantic.equals("global");
    }

    public boolean isMemberVariable() {
        return semantic.equals("member");
    }
    
    @Override
    public HashMap<String, XMLAttributeParser> addAttributeParsers(HashMap<String, XMLAttributeParser> to) {
        to = super.addAttributeParsers(to);
        to.put(MOD, new XMLAttributeParser() {
            @Override
            public void parse(Attribute attribute, Element parent) {
                ensureEditable();
                mod = Modifier.getModifier(attribute.getValue());
            }
        });
        to.put(TYPE, new XMLAttributeParser() {
            @Override
            public void parse(Attribute attribute, Element parent) {
                ensureEditable();
                type = new Type(attribute.getValue());
            }
        });
        to.put(NAME, new XMLAttributeParser() {
            @Override
            public void parse(Attribute attribute, Element parent) {
                name = attribute.getValue();
            }
        });
        to.put(SEMANTIC, new XMLAttributeParser() {
            @Override
            public void parse(Attribute attribute, Element parent) {
                semantic = attribute.getValue();
            }
        });
        return to;
    }
    
    @Override
    public GLSLVariableParser getInstance() {
        return new GLSLVariableParser();
    }
    
    @Override
    public void setSource(GLSLVariableParser source) {
        this.name = source.name;
        this.mod = source.mod;
        this.predefinedVariable = source.predefinedVariable;
        this.semantic = source.semantic;
        this.type = source.type;
    }
    
}
