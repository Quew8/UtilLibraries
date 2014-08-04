package com.quew8.codegen.java;

import com.quew8.codegen.Element;

/**
 *
 * @author Quew8
 */
public class ImportStatement extends JavaElement<ImportStatement> {
    private boolean isStatic;
    private String toImport;

    public ImportStatement(boolean isStatic, String toImport) {
        super("import< <mod>> <<import>>;");
        this.isStatic = isStatic;
        this.toImport = toImport;
    }

    public ImportStatement(String toImport) {
        this(false, toImport);
    }

    public ImportStatement() {
        this(null);
    }
    
    public boolean isStatic() {
        return isStatic;
    }

    public ImportStatement setIsStatic(boolean isStatic) {
        this.isStatic = isStatic;
        return this;
    }

    public Element<JavaGenData, ?> getMod() {
        if(isStatic) {
            return wrap("static");
        } else {
            return null;
        }
    }
    
    public String getImportString() {
        return toImport;
    }
    
    public Element<JavaGenData, ?> getImport() {
        return wrap(toImport);
    }

    public ImportStatement setImport(String toImport) {
        this.toImport = toImport;
        return this;
    }

    /*@Override
    protected String getConstructedCode() {
        return CodeGenUtils.getConstruction()
                .add("import") 
                .add(isStatic, "static")
                .add(toImport)
                .addNoGap(";")
                .get(); 
    }*/
}
