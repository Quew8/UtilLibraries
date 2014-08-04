package com.quew8.codegen.java;

import com.quew8.codegen.Element;

/**
 *
 * @author Quew8
 */
public class PackageStatement extends JavaElement<PackageStatement> {
    private String inPackage;
    
    public PackageStatement(String inPackage) {
        super("import <<package>>;");
        this.inPackage = inPackage;
    }
    
    public PackageStatement() {
        this(null);
    }
    
    public String getPackageString() {
        return inPackage;
    }
    
    public Element<JavaGenData, ?> getPackage() {
        return wrap(inPackage);
    }
    
    public PackageStatement setPackage(String inPackage) {
        this.inPackage = inPackage;
        return this;
    }
    
    /*@Override
    protected String getConstructedCode() {
        return CodeGenUtils.getConstruction()
                .add("package", inPackage)
                .addNoGap(";")
                .get();
    }*/
}
