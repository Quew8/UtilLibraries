package com.quew8.codegen.glsl;

/**
 *
 * @author Quew8
 */
public class Block extends GLSLElement {
    private String block;

    public Block(String block) {
        this.block = block;
    }

    protected String getBlock() {
        return block;
    }
    
    public Block setBlock(String block) {
        this.block = block;
        return this;
    }
    
    @Override
    protected String getConstructedCode() {
        return GLSLCodeGenUtils.getConstruction()
                .add("{")
                .addNewline(GLSLCodeGenUtils.shiftRight(block))
                .addNewline("}")
                .get();
    }
}
