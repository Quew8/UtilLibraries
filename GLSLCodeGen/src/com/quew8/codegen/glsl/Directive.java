package com.quew8.codegen.glsl;

import com.quew8.codegen.Element;

/**
 *
 * @author Quew8
 */
public class Directive extends GLSLElement<Directive> {
    private String name;
    private String body;

    public Directive(String name, String body) {
        super("#<<name>>< <body>>");
        this.name = name;
        this.body = body;
    }

    public String getNameString() {
        return name;
    }

    public Element<GLSLGenData, ?> getName() {
        return Element.<GLSLGenData>wrap(name);
    }

    public Directive setName(String name) {
        this.name = name;
        return this;
    }

    public String getBodyString() {
        return body;
    }

    public Element<GLSLGenData, ?> getBody() {
        return Element.<GLSLGenData>wrap(body);
    }

    public Directive setBody(String body) {
        this.body = body;
        return this;
    }
    
    public static Directive getVersion(String version) {
        return new Directive("version", version);
    }
    
    public static Directive getExtension(String extension, boolean enable) {
        return new Directive("extension ", extension + (enable ? " : enable" : " : disable"));
    }
    
}
