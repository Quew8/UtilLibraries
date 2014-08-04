package com.quew8.codegen.java;

import com.quew8.codegen.Element;

/**
 *
 * @author Quew8
 */
public class AccessModifier extends JavaElement<AccessModifier> {
    public static final AccessModifier 
            PUBLIC = new AccessModifier("public"), 
            PROTECTED = new AccessModifier("protected"),
            PACKAGE_PRIVATE = new AccessModifier(""),
            PRIVATE = new AccessModifier("private");
    
    private final String mod;
    
    private AccessModifier(String mod) {
        super("<<mod>>");
        this.mod = mod;
    }
    
    public AccessModifier() {
        this(null);
    }
    
    public Element<JavaGenData, ?> getMod() {
        return Element.wrap(mod);
    }
}
