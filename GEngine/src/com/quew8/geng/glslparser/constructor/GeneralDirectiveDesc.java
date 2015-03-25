package com.quew8.geng.glslparser.constructor;

import com.quew8.codegen.glsl.Directive;


public class GeneralDirectiveDesc extends DirectiveDesc {
    private final String body;

    public GeneralDirectiveDesc(String body) {
        this.body = body;
    }
    
    public String getBody() {
        return body;
    }
    
    @Override
    public Directive getDirective() {
        return new Directive(null, body) {
            
            @Override
            public String getDefinition() {
                return "#<<body>>";
            }
            
        };
    }
    
}
