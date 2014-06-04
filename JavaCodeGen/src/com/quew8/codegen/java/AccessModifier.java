package com.quew8.codegen.java;

/**
 *
 * @author Quew8
 */
public class AccessModifier extends JavaElement {
    public static final AccessModifier 
            PUBLIC = new AccessModifier("public"), 
            PROTECTED = new AccessModifier("protected"),
            PACKAGE_PRIVATE = new AccessModifier(""),
            PRIVATE = new AccessModifier("private");
    
    private final String code;
    
    private AccessModifier(String code) {
        this.code = code;
    }
    
    public AccessModifier() {
        this(null);
    }
    
    @Override
    protected String getConstructedCode() {
        return code;
    }
}
