package com.quew8.gutils.opengl.shaders.glsl;

/**
 *
 * @author Quew8
 */
public class GLSLType extends GLSLElement {
    public static final GLSLType 
            FLOAT = new GLSLType("float"), INT = new GLSLType("int"), BOOL = new GLSLType("bool"), 
            MAT2 = new GLSLType("mat2"), MAT3 = new GLSLType("mat3"), MAT4 = new GLSLType("mat4"), 
            VEC2 = new GLSLType("vec2"), VEC3 = new GLSLType("vec3"), VEC4 = new GLSLType("vec4"), 
            IVEC2 = new GLSLType("ivec2"), IVEC3 = new GLSLType("ivec3"), IVEC4 = new GLSLType("ivec4"), 
            BVEC2 = new GLSLType("bvec2"), BVEC3 = new GLSLType("bvec3"), BVEC4 = new GLSLType("bvec4"),
            SAMPLER2D = new GLSLType("sampler2D");
    
    protected static final GLSLType ANY = new GLSLType("ANY_TYPE") {
        @Override
        public boolean matches(GLSLType other) {
            return true;
        }
    };
    
    public static final GLSLType VOID = new GLSLType("void") {
        @Override
        public boolean matches(GLSLType other) {
            return false;
        }
    };
    
    private final String code;
    
    public GLSLType(String code) {
        this.code = code;
    }
    
    @Override
    public String getCode() {
        return code;
    }
    
    public boolean matches(GLSLType other) {
        return this.code.equals(other.code);
    }
    
}
