package com.quew8.codegen.java;

/**
 *
 * @author Quew8
 * @param <T>
 */
public abstract class TypeDef<T extends TypeDef<T>> extends JavaElement<T> {

    public TypeDef(String definition) {
        super(definition);
    }

    public abstract String getNameString();

    public abstract Type getSuperType();
    
    public abstract String[] getGenericArgStrings();

    public abstract Field[] getFields();
    
    public abstract Method[] getDefinedMethods();
    
    public Type getType(Type... generics) {
        if(generics.length != getGenericArgStrings().length) {
            throw new RuntimeException("Wrong Number Of Generic Arguments");
        }
        return new Type(getNameString(), generics);
    }
}
