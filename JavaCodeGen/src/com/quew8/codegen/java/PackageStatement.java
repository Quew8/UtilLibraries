package com.quew8.codegen.java;

import com.quew8.codegen.CodeGenUtils;

/**
 *
 * @author Quew8
 */
public class PackageStatement extends JavaElement {
    private String inPackage;
    
    public PackageStatement(String inPackage) {
        this.inPackage = inPackage;
    }
    
    public PackageStatement() {
        this(null);
    }
    
    protected String getPackage() {
        return inPackage;
    }
    
    public PackageStatement setPackage(String inPackage) {
        this.inPackage = inPackage;
        return this;
    }
    
    @Override
    protected String getConstructedCode() {
        return CodeGenUtils.getConstruction()
                .add("package", inPackage)
                .addNoGap(";")
                .get();
    }
}
