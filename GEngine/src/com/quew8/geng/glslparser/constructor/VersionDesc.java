package com.quew8.geng.glslparser.constructor;

import com.quew8.codegen.glsl.Directive;


public class VersionDesc extends DirectiveDesc {
    private final int min, max;
    
    public VersionDesc(int min, int max) {
        this.min = min;
        this.max = max;
    }
    
    @Override
    public Directive getDirective() {
        return Directive.getVersion(Integer.toString(max));
    }

    public int getMin() {
        return min;
    }
    
    public int getMax() {
        return max;
    }
    
}
