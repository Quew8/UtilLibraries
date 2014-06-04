package com.quew8.codegen.java;

import com.quew8.codegen.CodeGenUtils;

/**
 *
 * @author Quew8
 */
public class Type extends JavaElement {
    public static final Type 
            INT = new Type("int"),
            FLOAT = new Type("float"),
            DOUBLE = new Type("double"),
            BYTE = new Type("byte"),
            SHORT = new Type("short"),
            LONG = new Type("long"),
            CHAR = new Type("char"),
            STRING = new Type("String");
    
    private String name;
    private Type[] generics;
    
    public Type(String name, Type[] generics) {
        this.name = name;
        this.generics = generics != null ? generics : new Type[]{};
    }
    
    public Type(String name) {
        this(name, null);
    }
    
    public Type() {
        this(null);
    }
    
    protected String getName() {
        return name;
    }
    
    protected Type[] getGenerics() {
        return generics;
    }
    
    public Type setName(String name) {
        this.name = name;
        return this;
    }
    
    public Type setGenerics(Type... generics) {
        this.generics = generics;
        return this;
    }
    
    @Override
    protected String getConstructedCode() {
        return CodeGenUtils.getConstruction()
                .addNoGap(name, getGenericsString())
                .get();
    }
    
    private String getGenericsString() {
        if(generics.length != 0) {
            return "<" + CodeGenUtils.getCommaSeperatedList(generics) + ">";
        } else {
            return "";
        }
    }
    
    public static Type arrayOf(Type type) {
        return new Type(type.getCode() + "[]");
    }
    
    public static Type makeGeneric(Type baseType, Type... genericTypes) {
        return new Type(baseType.getCode() + "<" + CodeGenUtils.getCommaSeperatedList(genericTypes) + ">");
    }
}
