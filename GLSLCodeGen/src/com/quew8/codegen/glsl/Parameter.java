package com.quew8.codegen.glsl;

/**
 *
 * @author Quew8
 */
public class Parameter extends GLSLElement {
    private Type type;
    private String name;

    public Parameter(Type type, String name) {
        this.type = type;
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public Parameter setType(Type type) {
        this.type = type;
        return this;
    }

    public String getName() {
        return name;
    }

    public Parameter setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    protected String getConstructedCode() {
        return GLSLCodeGenUtils.getConstruction()
                .add(type)
                .add(name)
                .get();
    }
    
    
}
