package com.quew8.codegen.java;

/**
 *
 * @author Quew8
 */
public class Block extends JavaElement {
    private String code;
    
    public Block(String code) {
        this.code = code;
    }
    
    public Block() {
        this(null);
    }
    
    public Block setCode(String code) {
        this.code = code;
        return this;
    }
    
    @Override
    protected String getConstructedCode() {
        return JavaCodeGenUtils.getConstruction()
                .add("{")
                .addNewline(JavaCodeGenUtils.shiftRight(code))
                .addNewline("}")
                .get();
    }
}
