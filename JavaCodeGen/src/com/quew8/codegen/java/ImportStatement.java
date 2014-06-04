package com.quew8.codegen.java;

import com.quew8.codegen.CodeGenUtils;

/**
 *
 * @author Quew8
 */
public class ImportStatement extends JavaElement {
    private boolean isStatic;
    private String toImport;

    public ImportStatement(boolean isStatic, String toImport) {
        this.isStatic = isStatic;
        this.toImport = toImport;
    }

    public ImportStatement(String toImport) {
        this(false, toImport);
    }

    public ImportStatement() {
        this(null);
    }
    
    protected boolean isStatic() {
        return isStatic;
    }

    public ImportStatement setIsStatic(boolean isStatic) {
        this.isStatic = isStatic;
        return this;
    }

    protected String getImport() {
        return toImport;
    }

    public ImportStatement setImport(String toImport) {
        this.toImport = toImport;
        return this;
    }

    @Override
    protected String getConstructedCode() {
        return CodeGenUtils.getConstruction()
                .add("import") 
                .add(isStatic, "static")
                .add(toImport)
                .addNoGap(";")
                .get(); 
    }
}
