package com.quew8.gutils.opengl.shaders.glsl;

/**
 *
 * @author Quew8
 */
public class GLSLVariable extends GLSLCodeElement<GLSLVariable> {
    public static final GLSLVariable 
            GL_POSITION = new OpenGLConstantGLSLVariable(GLSLModifier.VARYING, GLSLType.VEC4, "gl_Position"),
            GL_FRAG_COLOR = new OpenGLConstantGLSLVariable(GLSLModifier.NONE, GLSLType.VEC4, "gl_FragColor");
    
    private final GLSLModifier mod;
    private final GLSLType type;
    private final String name;
    
    public GLSLVariable(GLSLModifier mod, GLSLType type, String name) {
        this.mod = mod;
        this.type = type;
        this.name = name;
    }
    
    @Override
    String getCode() {
        return 
                mod == GLSLModifier.NONE ?
                type.getCode() + " " + name:
                mod.getCode() + " " + type.getCode() + " " + name;
    }
    
    public GLSLModifier getMod() {
        return mod;
    }
    
    public GLSLType getType() {
        return type;
    }
    
    public String getName() {
        return name;
    }
    
    boolean compatible(GLSLVariable other) {
        return this.type.matches(other.type);
    }
    
    @Override
    public String toString() {
        return "GLSLVariable: " + getCode();
    }
    
    public static GLSLVariable getPredefinedVariable(String name) {
        switch(name) {
        case "gl_Position": return GL_POSITION;
        case "gl_FragColor": return GL_FRAG_COLOR;
        default: throw new GLSLIllegalOperationException("No Such Predefined Variable: " + name);
        }
    }
    
    private static class OpenGLConstantGLSLVariable extends GLSLVariable {
        
        public OpenGLConstantGLSLVariable(GLSLModifier modifier, GLSLType type, String name) {
            super(modifier, type, name);
        }
        
        @Override
        String getCode() {
            return "/*" + super.getCode() + "*/";
        }
        
    }
}
