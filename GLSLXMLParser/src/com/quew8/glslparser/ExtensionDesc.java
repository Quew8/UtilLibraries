package com.quew8.glslparser;

import com.quew8.codegen.glsl.Directive;

public class ExtensionDesc extends DirectiveDesc {
    private final String extension;
    private final boolean enable;
    private final int ifBelow;

    public ExtensionDesc(String extension, boolean enable, int ifBelow) {
        this.extension = extension;
        this.enable = enable;
        this.ifBelow = ifBelow;
    }
    
    public boolean isApplicable(int version) {
        return version < ifBelow;
    }
    
    public String getExtension() {
        return extension;
    }
    
    public boolean isEnable() {
        return enable;
    }
    
    @Override
    public Directive getDirective() {
        return Directive.getExtension(extension, enable);
    }
    
}
