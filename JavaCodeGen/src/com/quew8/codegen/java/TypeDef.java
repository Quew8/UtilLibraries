package com.quew8.codegen.java;

/**
 *
 * @author Quew8
 */
public abstract class TypeDef extends JavaElement {

    protected abstract String getName();

    protected abstract Type getSuperType();
    
    protected abstract String[] getGenericArgs();

    protected abstract Field[] getFields();
    
    protected abstract Method[] getDefinedMethods();
    
    protected Type getType(Type... generics) {
        if(generics.length != getGenericArgs().length) {
            throw new RuntimeException("Wrong Number Of Generic Arguments");
        }
        return new Type(getName(), generics);
    }
}
