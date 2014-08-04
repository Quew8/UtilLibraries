package com.quew8.codegen.java;

import com.quew8.codegen.Element;

/**
 *
 * @author Quew8
 * @param <T>
 */
public abstract class JavaElement<T extends JavaElement<T>> extends Element<JavaGenData, T> {

    public JavaElement(String definition) {
        super(definition);
    }
    
    protected TypeDef[] getTypes() {
        return null;
    }
    
    protected Variable[] getVariables() {
        return null;
    }
    
    protected MethodDef[] getMethods() {
        return null;
    }
}
