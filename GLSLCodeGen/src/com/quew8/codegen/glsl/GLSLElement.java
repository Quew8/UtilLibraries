package com.quew8.codegen.glsl;

import com.quew8.codegen.Element;

/**
 *
 * @author Quew8
 * @param <T>
 */
public class GLSLElement<T extends GLSLElement<T>> extends Element<GLSLGenData, T> {
    
    public GLSLElement(String definition) {
        super(definition);
    }
    
}
