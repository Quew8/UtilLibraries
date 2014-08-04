package com.quew8.codegen.glsl;

import com.quew8.codegen.Element;

/**
 *
 * @author Quew8
 */
public class Block extends GLSLElement<Block> {
    private String block;

    public Block(String block) {
        super("{<<!block>>}");
        this.block = block;
    }

    public String getBlockString() {
        return block;
    }
    
    public Element<GLSLGenData, ?> getBlock() {
        return Element.<GLSLGenData>wrap(block);
    }
    
    public Block setBlock(String block) {
        this.block = block;
        return this;
    }
    
    /*@Override
    protected String getConstructedCode() {
        return GLSLCodeGenUtils.getConstruction()
                .add("{")
                .addNewline(GLSLCodeGenUtils.shiftRight(block))
                .addNewline("}")
                .get();
    }*/
}
