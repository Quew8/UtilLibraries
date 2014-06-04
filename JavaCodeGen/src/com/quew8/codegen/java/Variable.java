package com.quew8.codegen.java;

/**
 *
 * @author Quew8
 */
public abstract class Variable extends JavaElement {

    protected abstract String getName();
    
    protected abstract Type getType();
}
