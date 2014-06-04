package com.quew8.codegen.java;

/**
 *
 * @author Quew8
 */
public abstract class MethodDef extends JavaElement {
    
    protected abstract String getName();

    protected abstract Parameter[] getParameters();

    protected abstract Type getReturnType();

    protected boolean matchesDef(String name, Type... params) {
        if (!getName().matches(name)) {
            return false;
        }
        if (getParameters().length != params.length) {
            return false;
        }
        for (int i = 0; i < params.length; i++) {
            if (!getParameters()[i].getType().getName().matches(params[i].getName())) {
                return false;
            }
        }
        return true;
    }
}
