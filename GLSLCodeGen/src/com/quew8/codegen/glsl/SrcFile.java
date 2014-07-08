package com.quew8.codegen.glsl;

/**
 *
 * @author Quew8
 */
public class SrcFile extends GLSLElement {
    private Directive[] directives;
    private Struct[] structs;
    private Variable[] variables;
    private Method[] methods;

    public SrcFile(Directive[] directives, Struct[] structs, Variable[] variables, Method[] methods) {
        this.directives = directives != null ? directives : new Directive[]{};
        this.structs = structs != null ? structs : new Struct[]{};
        this.variables = variables != null ? variables : new Variable[]{};
        this.methods = methods != null ? methods : new Method[]{};
    }

    public SrcFile() {
        this(null, null, null, null);
    }
    
    public Directive[] getDirectives() {
        return directives;
    }

    public SrcFile setDirectives(Directive... directives) {
        this.directives = directives;
        return this;
    }
    
    public Struct[] getStructs() {
        return structs;
    }

    public SrcFile setStructs(Struct... structs) {
        this.structs = structs;
        return this;
    }
    
    public Variable[] getVariables() {
        return variables;
    }

    public SrcFile setVariables(Variable... variables) {
        this.variables = variables;
        return this;
    }

    public Method[] getMethods() {
        return methods;
    }

    public SrcFile setMethods(Method... methods) {
        this.methods = methods;
        return this;
    }
    
    @Override
    protected String getConstructedCode() {
        return GLSLCodeGenUtils.getConstruction()
                .addLineSeparated(GLSLCodeGenUtils.getNewlineList(directives))
                .addLineSeparated(structs)
                .addLineSeparated(GLSLCodeGenUtils.getNewlineList(variables))
                .addLineSeparated(methods)
                .get();
    }
    
}
