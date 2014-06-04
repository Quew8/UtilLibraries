package com.quew8.codegen.java;

import com.quew8.codegen.Element;

/**
 *
 * @author Quew8
 */
public abstract class JavaElement extends Element {
    
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
