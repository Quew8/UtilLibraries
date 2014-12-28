package com.quew8.codegen.java;

import com.quew8.codegen.Element;

/**
 *
 * @author Quew8
 */
public class Modifier extends JavaElement<Modifier> {
    private static final int 
            STATIC_BIT = 1,
            FINAL_BIT = 2,
            ABSTRACT_BIT = 4;
    
    public static final Modifier 
            NONE = new Modifier(0),
            STATIC = new Modifier(STATIC_BIT), 
            FINAL = new Modifier(FINAL_BIT), 
            ABSTRACT = new Modifier(ABSTRACT_BIT);
    
    private int mask;
    
    private Modifier(int mask) {
        super("<<mods>>");
        this.mask = mask;
    }
    
    public Modifier(boolean isStatic, boolean isFinal, boolean isAbstract) {
        this(getModifierMask(isStatic, isFinal, isAbstract));
    }
    
    public Modifier() {
        this(false, false, false);
    }
    
    public Modifier setIsStatic(boolean isStatic) {
        if(isStatic) {
            mask |= STATIC_BIT;
        } else {
            mask ^= STATIC_BIT;
        }
        return this;
    }
    
    public Modifier setIsFinal(boolean isFinal) {
        if(isFinal) {
            mask |= FINAL_BIT;
        } else {
            mask ^= FINAL_BIT;
        }
        return this;
    }
    
    public Modifier setIsAbstract(boolean isAbstract) {
        if(isAbstract) {
            mask |= ABSTRACT_BIT;
        } else {
            mask ^= ABSTRACT_BIT;
        }
        return this;
    }
    
    public boolean isStatic() {
        return (STATIC_BIT & mask) != 0;
    }
    
    public boolean isFinal() {
        return (FINAL_BIT & mask) != 0;
    }
    
    public boolean isAbstract() {
        return (ABSTRACT_BIT & mask) != 0;
    }
    
    public Element<JavaGenData, ?> getMods() {
        String s = "";
        if(isStatic()) {
            s += "static";
            if(isFinal() || isAbstract()) {
                s += " ";
            }
        }
        if(isFinal()) {
            s += "final";
            if(isAbstract()) {
                s += " ";
            }
        }
        if(isAbstract()) {
            s += "abstract";
        }
        return wrap(s);
    }
    
    private static int getModifierMask(boolean isStatic, boolean isFinal, boolean isAbstract) {
        int mask = 0;
        if(isStatic) {
            mask |= STATIC_BIT; 
        }
        if(isFinal) {
            mask |= FINAL_BIT; 
        }
        if(isAbstract) {
            mask |= ABSTRACT_BIT; 
        }
        return mask;
    }
}
