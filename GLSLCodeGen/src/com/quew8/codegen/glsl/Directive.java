package com.quew8.codegen.glsl;

/**
 *
 * @author Quew8
 */
public class Directive extends GLSLElement {
    private String name;
    private String body;

    public Directive(String name, String body) {
        this.name = name;
        this.body = body;
    }

    public String getName() {
        return name;
    }

    public Directive setName(String name) {
        this.name = name;
        return this;
    }

    public String getBody() {
        return body;
    }

    public Directive setBody(String body) {
        this.body = body;
        return this;
    }
    
    @Override
    protected String getConstructedCode() {
        return GLSLCodeGenUtils.getConstruction()
                .add("#" + name, body)
                .get();
    }
    
    public static Directive getVersion(String version) {
        return new Directive("version", version);
    }
    
    public static Directive getExtension(String extension, boolean enable) {
        return new Directive("extension ", extension + (enable ? " : enable" : " : disable"));
    }
    
}
