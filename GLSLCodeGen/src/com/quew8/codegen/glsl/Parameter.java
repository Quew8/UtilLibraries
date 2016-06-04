package com.quew8.codegen.glsl;

import com.quew8.codegen.Element;

/**
 *
 * @author Quew8
 */
public class Parameter extends GLSLElement<Parameter> {
    private Type type;
    private String name;

    public Parameter(Type type, String name) {
        super("<<type>> <<name>>");
        this.type = type;
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public Parameter setType(Type type) {
        this.type = type;
        return this;
    }

    public String getNameString() {
        return name;
    }

    public Element<GLSLGenData, ?> getName() {
        return Element.<GLSLGenData>wrap(name);
    }

    public Parameter setName(String name) {
        this.name = name;
        return this;
    }
}
