package com.quew8.gutils.opengl.shaders.glsl;

import java.util.regex.Pattern;

/**
 *
 * @author Quew8
 */
public class GLSLEffect extends GLSLElement {
    public static final GLSLEffect NO_EFFECT = new GLSLEffect(
            "noEffectOutVar = noEffectInVar;",
            new GLSLVariable(GLSLModifier.NONE, GLSLType.ANY, "noEffectInVar"), 
            new GLSLVariable(GLSLModifier.NONE, GLSLType.ANY, "noEffectOutVar"),
            new GLSLStruct[]{},
            new GLSLMethod[]{},
            new GLSLVariable[]{},
            new GLSLExtra[]{},
            new GLSLCompileTimeConstant[]{}
    );
    
    private String code;
    private GLSLVariable inVar;
    private GLSLVariable outVar;
    private final GLSLStruct[] structs;
    private final GLSLMethod[] methods;
    private final GLSLVariable[] globalVariables;
    private final GLSLExtra[] extras;
    private final GLSLCompileTimeConstant[] constants;
    
    public GLSLEffect(String code, GLSLVariable inVar, GLSLVariable outVar, GLSLStruct[] structs, GLSLMethod[] methods, GLSLVariable[] globalVariables, GLSLExtra[] extras, GLSLCompileTimeConstant[] constants) {
        this.code = code;
        this.inVar = inVar;
        this.outVar = outVar;
        this.structs = structs;
        this.methods = methods;
        this.globalVariables = globalVariables;
        this.extras = extras;
        this.constants = constants;
    }
    
    public GLSLEffect combine(GLSLEffect effect) {
        if(!this.outVar.compatible(effect.inVar)) {
            throw new GLSLIllegalOperationException("Incompatible Types: A Out " + this.outVar + ", B In " + effect.inVar + ". Cannot Combine Effects");
        }
        String newCode = this.outVar.getCode() + ";\n";
        newCode += this.code;
        newCode += "\n" + effect.code.replaceAll(Pattern.quote(effect.inVar.getName()), this.outVar.getName());
        return new GLSLEffect(
                newCode, this.inVar, effect.outVar, 
                GLSLCodeElement.combineElements(this.structs, effect.structs, new GLSLStruct[]{}),
                GLSLCodeElement.combineElements(this.methods, effect.methods, new GLSLMethod[]{}),
                GLSLCodeElement.combineElements(this.globalVariables, effect.globalVariables, new GLSLVariable[]{}),
                GLSLCodeElement.combineElements(this.extras, effect.extras, new GLSLExtra[]{}),
                GLSLCompileTimeConstant.combineConstants(this.constants, effect.constants)
        );
    }
    
    void setInput(GLSLVariable to) {
        if(!inVar.compatible(to)) {
            throw new GLSLIllegalOperationException("Incompatible Types. Cannot Pipe Input\nFrom " + inVar + "\nTo " + to);
        }
        code = code.replaceAll(Pattern.quote(inVar.getName()), to.getName());
        inVar = to;
    }
    
    void setOutput(GLSLVariable to) {
        if(!outVar.compatible(to)) {
            throw new GLSLIllegalOperationException("Incompatible Types. Cannot Pipe Output\nFrom " + outVar + "\nTo " + to);
        }
        code = code.replaceAll(Pattern.quote(outVar.getName()), to.getName());
        outVar = to;
    }
    
    public GLSLStruct[] getStructs() {
        return structs;
    }
    
    public GLSLMethod[] getMethods() {
        return methods;
    }
    
    public GLSLCompileTimeConstant[] getConstants() {
        return constants;
    }
    
    public GLSLVariable[] getGlobalVariables() {
        return globalVariables;
    }

    public GLSLExtra[] getExtras() {
        return extras;
    }
    
    @Override
    public String getCode() {
        return code;
    }
    
    public GLSLVariable getInputVar() {
        return inVar;
    }
    
    public GLSLVariable getOutputVar() {
        return outVar;
    }
    
    public static GLSLEffect getPredefinedEffect(String name) {
        switch(name) {
        case "no_effect": return NO_EFFECT;
        default: throw new GLSLIllegalOperationException("No Such Predefined Effect: " + name);
        }
    }
    
    @Override
    public String toString() {
        return "GLSLEffect{" + "inVar=" + inVar + ", outVar=" + outVar + '}';
    }
}
