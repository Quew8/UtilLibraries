package com.quew8.codegen.java;

import com.quew8.codegen.Element;

/**
 *
 * @author Quew8
 */
public class Block extends JavaElement<Block> {
    private String code;
    
    public Block(String code) {
        super("{<<!code>>}");
        this.code = code;
    }
    
    public Block() {
        this(null);
    }
    
    public Block setCode(String code) {
        this.code = code;
        return this;
    }
    
    public String getCodeString() {
        return code;
    }
    
    public Element<JavaGenData, ?> getCode() {
        return Element.wrap(code);
    }
}
