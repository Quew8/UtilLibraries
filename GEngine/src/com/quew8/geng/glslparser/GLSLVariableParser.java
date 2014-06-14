package com.quew8.geng.glslparser;

import com.quew8.geng.xmlparser.XMLAttributeParser;
import com.quew8.gutils.opengl.shaders.glsl.GLSLModifier;
import com.quew8.gutils.opengl.shaders.glsl.GLSLType;
import com.quew8.gutils.opengl.shaders.glsl.GLSLVariable;
import java.util.HashMap;
import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
public class GLSLVariableParser extends GLSLParser<GLSLVariableParser> {
    private String name;
    private GLSLModifier mod = GLSLModifier.NONE;
    private GLSLType type;
    private String semantic;
    private GLSLVariable predefinedVariable = null;

    public GLSLVariableParser() {
        super(new String[]{}, new String[]{TYPE, NAME, SEMANTIC});
    }
    
    @Override
    public void loadPredefined(Element element, String predefinedName) {
        predefinedVariable = GLSLVariable.getPredefinedVariable(predefinedName);
        mod = predefinedVariable.getMod();
        type = predefinedVariable.getType();
        hasRequiredAttribute(TYPE);
        name = predefinedVariable.getName();
        hasRequiredAttribute(NAME);
    }
    
    public GLSLVariable getVariable() {
        finalized();
        return 
                predefinedVariable == null ? 
                new GLSLVariable(mod, type, name) : 
                predefinedVariable;
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
                mod = GLSLModifier.getModifier(attribute.getValue());
            }
        });
        to.put(TYPE, new XMLAttributeParser() {
            @Override
            public void parse(Attribute attribute, Element parent) {
                ensureEditable();
                type = new GLSLType(attribute.getValue());
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
