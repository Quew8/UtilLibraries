package com.quew8.codegen.java;

import com.quew8.codegen.CodeGenUtils;

/**
 *
 * @author Quew8
 */
public class Parameter extends Variable {
    private Type type;
    private String name;
    
    public Parameter(Type type, String name) {
        this.type = type;
        this.name = name;
    }

    public Parameter() {
        this(null, null);
    }
    
    @Override
    protected Type getType() {
        return type;
    }

    @Override
    protected String getName() {
        return name;
    }

    public Parameter setType(Type type) {
        this.type = type;
        return this;
    }

    public Parameter setName(String name) {
        this.name = name;
        return this;
    }
    
    @Override
    protected String getConstructedCode() {
        return CodeGenUtils.getConstruction()
                .add(getType())
                .add(getName())
                .get();
    }
}
