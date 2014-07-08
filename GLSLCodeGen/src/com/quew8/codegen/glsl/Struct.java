package com.quew8.codegen.glsl;

/**
 *
 * @author Quew8
 */
public class Struct extends GLSLElement {
    private String name;
    private Variable[] variables;

    public Struct(String name, Variable[] variables) {
        this.name = name;
        this.variables = variables != null ? variables : new Variable[]{};
    }

    public Struct(String name) {
        this(name, null);
    }

    public String getName() {
        return name;
    }

    public Struct setName(String name) {
        this.name = name;
        return this;
    }

    public Variable[] getVariables() {
        return variables;
    }

    public Struct setVariables(Variable[] variables) {
        this.variables = variables;
        return this;
    }
    
    @Override
    protected String getConstructedCode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
