package com.quew8.codegen.glsl;

import com.quew8.codegen.CodeGenUtils;
import com.quew8.codegen.Generator;
import java.util.HashMap;

/**
 *
 * @author Quew8
 */
public class GLSLCodeGenUtils extends CodeGenUtils {
    
    public static String generateGLSL(GLSLElement element, HashMap<String, String> replacements) {
        return new Generator<GLSLGenData, GLSLElement<?>, GLSLEvaluator>(new GLSLGenData(), GLSLEvaluator.INSTANCE, element, replacements).generate();
    }
    
    public static String generateGLSL(GLSLElement element) {
        return generateGLSL(element, new HashMap<String, String>());
    }
    
}
