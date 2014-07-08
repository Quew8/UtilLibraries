package com.quew8.codegen.glsl;

/**
 *
 * @author Quew8
 */
public class Variable extends GLSLElement {
    public static final Variable
            GL_POSITION = new Variable(Modifier.VARYING, Type.VEC4, "gl_Position"),
            GL_FRAG_COLOR = new Variable(Modifier.NONE, Type.VEC4, "gl_FragColor");
    
    private Modifier mod;
    private Type type;
    private String name;

    public Variable(Modifier mod, Type type, String name) {
        this.mod = mod;
        this.type = type;
        this.name = name;
    }

    public Variable(Type type, String name) {
        this(Modifier.NONE, type, name);
    }
    
    public Modifier getModifier() {
        return mod;
    }
    
    public Variable setModifier(Modifier mod) {
        this.mod = mod;
        return this;
    }

    public Type getType() {
        return type;
    }

    public Variable setType(Type type) {
        this.type = type;
        return this;
    }

    public String getName() {
        return name;
    }

    public Variable setName(String name) {
        this.name = name;
        return this;
    }
    
    public boolean isCoreVariable() {
        return name.startsWith("gl_");
    }
    
    @Override
    protected String getConstructedCode() {
        return GLSLCodeGenUtils.getConstruction()
                .add(mod, type)
                .add(name)
                .addNoGap(";")
                .get();
    }
    
    public static Variable getBuiltInVariable(String name) {
        switch(name) {
        case "gl_Position": return GL_POSITION;
        case "gl_FragColor": return GL_FRAG_COLOR;
        default: throw new IllegalArgumentException("No Such Predefined Variable: " + name);
        }
    }
}
