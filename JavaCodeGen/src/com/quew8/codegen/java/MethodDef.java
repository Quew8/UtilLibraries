package com.quew8.codegen.java;

/**
 *
 * @author Quew8
 * @param <T>
 */
public abstract class MethodDef<T extends MethodDef<T>> extends JavaElement<T> {

    public MethodDef(String definition) {
        super(definition);
    }
    
    public abstract String getNameString();

    public abstract Parameter[] getParameters();

    public abstract Type getReturnType();

    protected boolean matchesDef(String name, Type... params) {
        if (!getNameString().matches(name)) {
            return false;
        }
        if (getParameters().length != params.length) {
            return false;
        }
        for (int i = 0; i < params.length; i++) {
            if (!getParameters()[i].getType().getNameString().matches(params[i].getNameString())) {
                return false;
            }
        }
        return true;
    }
}
