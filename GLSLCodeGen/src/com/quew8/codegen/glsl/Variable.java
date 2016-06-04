package com.quew8.codegen.glsl;

import com.quew8.codegen.Element;

/**
 *
 * @author Quew8
 */
public class Variable extends GLSLElement<Variable> {
    public static final Variable
            GL_POSITION = new Variable(Modifier.VARYING, Type.VEC4, "gl_Position"),
            GL_POINT_SIZE = new Variable(Modifier.VARYING, Type.FLOAT, "gl_PointSize"),
            GL_FRAG_COLOR = new Variable(Modifier.NONE, Type.VEC4, "gl_FragColor");
    
    private Modifier mod;
    private Type type;
    private String name;

    public Variable(Modifier mod, Type type, String name) {
        super("<<modifier> ><<type>> <<name>>;");
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

    public String getNameString() {
        return name;
    }

    public Element<GLSLGenData, ?> getName() {
        return Element.<GLSLGenData>wrap(name);
    }

    public Variable setName(String name) {
        this.name = name;
        return this;
    }
    
    public boolean isCoreVariable() {
        return name.startsWith("gl_");
    }
    
    public static Variable getBuiltInVariable(String name) {
        switch(name) {
        case "gl_Position": return GL_POSITION;
        case "gl_PointSize": return GL_POINT_SIZE;
        case "gl_FragColor": return GL_FRAG_COLOR;
        default: throw new IllegalArgumentException("No Such Predefined Variable: " + name);
        }
    }
}
