package com.quew8.codegen;

/**
 *
 * @author Quew8
 */
public abstract class Element {
    private String overrideCode = null;
    
    protected final void setOverrideCode(String overrideCode) {
        this.overrideCode = overrideCode;
    }
    
    protected final String getCode() {
        return overrideCode != null ? overrideCode : getConstructedCode();
    }
    
    protected abstract String getConstructedCode();
}
