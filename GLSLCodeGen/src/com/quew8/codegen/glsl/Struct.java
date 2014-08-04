package com.quew8.codegen.glsl;

import com.quew8.codegen.Element;

/**
 *
 * @author Quew8
 */
public class Struct extends GLSLElement<Struct> {
    private String name;
    private Variable[] variables;

    public Struct(String name, Variable[] variables) {
        super("<<name>> {\n<<\n<variables>>>\n};");
        this.name = name;
        this.variables = variables != null ? variables : new Variable[]{};
    }

    public Struct(String name) {
        this(name, null);
    }

    public String getNameString() {
        return name;
    }

    public Element<GLSLGenData, ?> getName() {
        return Element.<GLSLGenData>wrap(name);
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
    
}
