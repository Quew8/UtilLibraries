package com.quew8.codegen.java;

import com.quew8.codegen.Element;

/**
 *
 * @author Quew8
 */
public class Parameter extends Variable<Parameter> {
    private Type type;
    private String name;
    
    public Parameter(Type type, String name) {
        super("<<type>> <<name>>");
        this.type = type;
        this.name = name;
    }

    public Parameter() {
        this(null, null);
    }
    
    @Override
    public Type getType() {
        return type;
    }

    @Override
    public String getNameString() {
        return name;
    }

    public Element<JavaGenData, ?> getName() {
        return wrap(name);
    }

    public Parameter setType(Type type) {
        this.type = type;
        return this;
    }

    public Parameter setName(String name) {
        this.name = name;
        return this;
    }
    
    /*@Override
    protected String getConstructedCode() {
        return CodeGenUtils.getConstruction()
                .add(getType())
                .add(getName())
                .get();
    }*/
}
