package com.quew8.codegen.java;

/**
 *
 * @author Quew8
 * @param <T>
 */
public abstract class Variable<T extends Variable<T>> extends JavaElement<T> {
    
    public Variable(String definition) {
        super(definition);
    }
    
    public abstract String getNameString();
    
    public abstract Type getType();
}
