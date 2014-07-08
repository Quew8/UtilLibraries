package com.quew8.codegen.glsl;

/**
 *
 * @author Quew8
 */
public class Type extends GLSLElement {
    public static final Type
            FLOAT = new Type("float"),
            INT = new Type("int"),
            BOOL = new Type("bool"),
            VEC2 = new Type("vec2"),
            VEC3 = new Type("vec3"),
            VEC4 = new Type("vec4"),
            MAT2 = new Type("mat2"),
            MAT3 = new Type("mat3"),
            MAT4 = new Type("mat4"),
            SAMPLER2D = new Type("sampler2D");
    
    private String name;

    public Type(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Type setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    protected String getConstructedCode() {
        return name == null ? "void" : name;
    }
}
