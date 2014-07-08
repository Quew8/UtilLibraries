package com.quew8.geng.glslparser;

import com.quew8.codegen.glsl.Modifier;
import com.quew8.codegen.glsl.Parameter;
import com.quew8.codegen.glsl.Type;
import com.quew8.codegen.glsl.Variable;
import com.quew8.geng.xmlparser.XMLAttributeParser;
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

    public GLSLVariableParser() {
        super(new String[]{}, new String[]{TYPE, NAME, SEMANTIC});
    }
    
    @Override
    public void loadPredefined(Element element, String predefinedName) {
        predefinedVariable = Variable.getBuiltInVariable(predefinedName);
        mod = predefinedVariable.getModifier();
        type = predefinedVariable.getType();
        hasRequiredAttribute(TYPE);
        name = predefinedVariable.getName();
        hasRequiredAttribute(NAME);
    }
    
    public Parameter getParameter() {
        finalized();
        if(predefinedVariable == null) {
            return new Parameter(type, name);
        } else {
            throw new RuntimeException("Cannot Have Predefined Parameters");
        }
    }
    
    public Variable getVariable() {
        finalized();
        if(predefinedVariable == null) {
            return new Variable(mod, type, name);
        } else {
            return predefinedVariable;
        }
    }
    
    private void ensureEditable() {
        if(predefinedVariable != null) {
            throw new RuntimeException("Predefined variables cannot be edited");
        }
    }
    
    public String getSemantic() {
        finalized();
        return semantic;
    }
    
    public boolean isInputVariable() {
        finalized();
        return semantic.equals("in_var");
    }
    
    public boolean isOutputVariable() {
        finalized();
        return semantic.equals("out_var");
    }
    
    public boolean isGlobalVariable() {
        finalized();
        return semantic.equals("global");
    }

    public boolean isMemberVariable() {
        finalized();
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
