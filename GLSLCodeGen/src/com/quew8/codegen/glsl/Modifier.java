package com.quew8.codegen.glsl;

import com.quew8.codegen.Element;

/**
 *
 * @author Quew8
 */
public class Modifier extends GLSLElement<Modifier> {
    public static final Modifier 
            ATTRIBUTE = new Modifier("attribute"), 
            VARYING = new Modifier("varying"),
            UNIFORM = new Modifier("uniform"),
            CONST = new Modifier("const"),
            NONE = new Modifier("");
        
    private String mod;
    
    private Modifier(String mod) {
        super("<<mod>>");
        this.mod = mod;
    }

    public String getModString() {
        return mod;
    }

    public Element<GLSLGenData, ?> getMod() {
        return Element.<GLSLGenData>wrap(mod);
    }

    public Modifier setMod(String mod) {
        this.mod = mod;
        return this;
    }

    /*@Override
    protected String getConstructedCode() {
        return mod;
    }*/
    
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
