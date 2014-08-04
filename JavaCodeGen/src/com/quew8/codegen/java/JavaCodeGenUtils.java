package com.quew8.codegen.java;

import com.quew8.codegen.CodeGenUtils;
import com.quew8.codegen.Generator;
import java.util.HashMap;

/**
 *
 * @author Quew8
 */
public abstract class JavaCodeGenUtils extends CodeGenUtils {
    
    protected JavaCodeGenUtils() {
        
    }
    
    public static Variable[] getVariablesOf(JavaElement[]... elements) {
        Variable[][] methods = new Variable[getSize(elements)][];
        for(int i = 0, pos = 0; i < elements.length; i++) {
            for(int j = 0; j < elements[i].length; j++, pos++) {
                methods[pos] = elements[i][j].getVariables();
            }
        }
        return combineElements(Variable.class, methods);
    }
    
    public static MethodDef[] getMethodsOf(JavaElement[]... elements) {
        MethodDef[][] methods = new MethodDef[getSize(elements)][];
        for(int i = 0, pos = 0; i < elements.length; i++) {
            for(int j = 0; j < elements[i].length; j++, pos++) {
                methods[pos] = elements[i][j].getMethods();
            }
        }
        return combineElements(MethodDef.class, methods);
    }
    
    public static TypeDef[] getTypesOf(JavaElement[]... elements) {
        TypeDef[][] methods = new TypeDef[getSize(elements)][];
        for(int i = 0, pos = 0; i < elements.length; i++) {
            for(int j = 0; j < elements[i].length; j++, pos++) {
                methods[pos] = elements[i][j].getTypes();
            }
        }
        return combineElements(TypeDef.class, methods);
    }
    
    public static String generateJava(JavaGenData data, JavaElement element, HashMap<String, String> replacements) {
        return new Generator<JavaGenData, JavaElement<?>, JavaEvaluator>(data, JavaEvaluator.INSTANCE, element, replacements).generate();
    }
    
    public static String generateJava(JavaGenData data, JavaElement element) {
        return generateJava(data, element, new HashMap<String, String>());
    }
}
