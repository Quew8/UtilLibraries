package com.quew8.geng.glslparser;

import com.quew8.geng.xmlparser.XMLElementParser;
import com.quew8.gutils.opengl.shaders.glsl.GLSLCompileTimeConstant;
import com.quew8.gutils.opengl.shaders.glsl.GLSLEffect;
import com.quew8.gutils.opengl.shaders.glsl.GLSLExtra;
import com.quew8.gutils.opengl.shaders.glsl.GLSLMethod;
import com.quew8.gutils.opengl.shaders.glsl.GLSLStruct;
import com.quew8.gutils.opengl.shaders.glsl.GLSLVariable;
import java.util.HashMap;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
public class GLSLEffectParser extends GLSLElementStructure<GLSLEffectParser> {
    private static final String 
            IN_VAR = "IN_VARIABLE", 
            OUT_VAR = "OUT_VARIABLE";
    
    private String code;
    private GLSLVariable inVar;
    private GLSLVariable outVar;

    public GLSLEffectParser() {
        super(new String[]{CODE, IN_VAR, OUT_VAR}, new String[]{});
    }
    
    @Override
    public void loadPredefined(Element element, String predefinedName) {
        GLSLEffect predefinedEffect = GLSLEffect.getPredefinedEffect(predefinedName);
        code = predefinedEffect.getCode();
        hasRequiredElement(CODE);
        inVar = predefinedEffect.getInputVar();
        hasRequiredElement(IN_VAR);
        outVar = predefinedEffect.getOutputVar();
        hasRequiredElement(OUT_VAR);
        add(predefinedEffect.getGlobalVariables());
        add(predefinedEffect.getConstants());
        add(predefinedEffect.getExtras());
    }
    
    private void code(Element codeElement) {
        code = codeElement.getText();
    }
    
    private void variable(Element variableElement) {
        GLSLVariableParser variableParser = parseWith(variableElement, new GLSLVariableParser());
        if(variableParser.isInputVariable()) {
            inVar = variableParser.getVariable();
            hasRequiredElement(IN_VAR);
        } else if(variableParser.isOutputVariable()) {
            outVar = variableParser.getVariable();
            hasRequiredElement(OUT_VAR);
        } else if(variableParser.isGlobalVariable()) {
            addGlobalVariable(variableParser.getVariable());
        }
    }
    
    public GLSLEffect getEffect() {
        finalized();
        return new GLSLEffect(
                code, inVar, outVar, 
                getStructs().toArray(new GLSLStruct[getStructs().size()]),
                getMethods().toArray(new GLSLMethod[getMethods().size()]),
                getGlobalVariables().toArray(new GLSLVariable[getGlobalVariables().size()]),
                getExtras().toArray(new GLSLExtra[getExtras().size()]),
                getConstants().toArray(new GLSLCompileTimeConstant[getConstants().size()])
        );
    }
    
    @Override
    public HashMap<String, XMLElementParser> addElementParsers(HashMap<String, XMLElementParser> to) {
        to = super.addElementParsers(to);
        to.put(CODE, new XMLElementParser() {
            @Override
            public void parse(Element element) {
                GLSLEffectParser.this.code(element);
            }
        }
        );
        to.put(VARIABLE, new XMLElementParser() {
            @Override
            public void parse(Element element) {
                GLSLEffectParser.this.variable(element);
            }
        }
        );
        return to;
    }
    
    @Override
    public void setSource(GLSLEffectParser source) {
        super.setSource(source);
        this.code = source.code;
        this.inVar = source.inVar;
        this.outVar = source.outVar;
    }
    
    @Override
    public GLSLEffectParser getInstance() {
        return new GLSLEffectParser();
    }
    
    @Override
    public String toString() {
        return "GLSLEffectParser{" + "inVar=" + inVar + ", outVar=" + outVar + '}';
    }
}
