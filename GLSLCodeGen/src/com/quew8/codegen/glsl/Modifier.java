package com.quew8.codegen.glsl;

/**
 *
 * @author Quew8
 */
public class Modifier extends GLSLElement {
    public static final Modifier 
            ATTRIBUTE = new Modifier("attribute"), 
            VARYING = new Modifier("varying"),
            UNIFORM = new Modifier("uniform"),
            CONST = new Modifier("const"),
            NONE = new Modifier("");
        
    private String mod;
    
    private Modifier(String mod) {
        this.mod = mod;
    }

    protected String getMod() {
        return mod;
    }

    public Modifier setMod(String mod) {
        this.mod = mod;
        return this;
    }

    @Override
    protected String getConstructedCode() {
        return mod;
    }
    
    public static Modifier getModifier(String name) {
        switch(name) {
        case "": return Modifier.NONE;
        case "const": return Modifier.CONST;
        case "uniform": return Modifier.UNIFORM;
        case "attribute": return Modifier.ATTRIBUTE;
        case "varying": return Modifier.VARYING;
        default: throw new IllegalArgumentException("No Such GLSL Modifier: " + name);
        }
    } 
}
