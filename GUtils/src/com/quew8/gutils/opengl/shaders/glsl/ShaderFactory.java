package com.quew8.gutils.opengl.shaders.glsl;

import com.quew8.gutils.opengl.shaders.ShaderProgram;
import com.quew8.gutils.opengl.shaders.glsl.formatting.Expansion;
import com.quew8.gutils.opengl.shaders.glsl.formatting.ForLoopExpansion;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Quew8
 */
public class ShaderFactory {
    public final static Expansion[] 
            DEFAULT_EXPANSIONS = {
                ForLoopExpansion.INSTANCE
            },
            NO_EXPANSIONS = {};
    
    private final ArrayList<GLSLVariable> globalIOs = new ArrayList<GLSLVariable>();
    private final ArrayList<GLSLEffect> pipelines = new ArrayList<GLSLEffect>();
    
    public String construct(String[][] constantNameValuePairs, Expansion[] expansions) {
        HashMap<String, GLSLCompileTimeConstant> constants = new HashMap<String, GLSLCompileTimeConstant>();
        for(int i = 0; i < pipelines.size(); i++) {
            GLSLCompileTimeConstant[] effectConstants = pipelines.get(i).getConstants();
            for(int j = 0; j < effectConstants.length; j++) {
                constants.put(effectConstants[j].getName(), effectConstants[j]);
            }
        }
        for(int i = 0; i < constantNameValuePairs.length; i++) {
            if(constants.containsKey(constantNameValuePairs[i][0])) {
                constants.get(constantNameValuePairs[i][0]).setValue(constantNameValuePairs[i][1]);
            }
        }
        
        String shader = "";
        
        GLSLExtra[] extras = pipelines.get(0).getExtras();
        for(int i = 1; i < pipelines.size(); i++) {
            extras = GLSLCodeElement.combineElements(extras, pipelines.get(i).getExtras(), new GLSLExtra[]{});
        }
        for(int i = 0; i < extras.length; i++) {
            shader += extras[i].getFormattedCode(constants, expansions) + "\n";
        }
        shader += "\n";
        
        GLSLStruct[] structs = pipelines.get(0).getStructs();
        for(int i = 1; i < pipelines.size(); i++) {
            structs = GLSLCodeElement.combineElements(structs, pipelines.get(i).getStructs(), new GLSLStruct[]{});
        }
        for(int i = 0; i < structs.length; i++) {
            shader += structs[i].getFormattedCode(constants, expansions) + ";\n";
        }
        shader += "\n";
        
        GLSLMethod[] methods = pipelines.get(0).getMethods();
        for(int i = 1; i < pipelines.size(); i++) {
            methods = GLSLCodeElement.combineElements(methods, pipelines.get(i).getMethods(), new GLSLMethod[]{});
        }
        for(int i = 0; i < methods.length; i++) {
            shader += methods[i].getDeclaration().getFormattedCode(constants, expansions) + ";\n";
        }
        shader += "\n";
        
        GLSLVariable[] globals = globalIOs.toArray(new GLSLVariable[globalIOs.size()]);
        for(int i = 0; i < pipelines.size(); i++) {
            globals = GLSLCodeElement.combineElements(globals, pipelines.get(i).getGlobalVariables(), new GLSLVariable[]{});
        }
        for(int i = 0; i < globals.length; i++) {
            shader += globals[i].getFormattedCode(constants, expansions) + ";\n";
        }
        
        String mainMethodCode = pipelines.get(0).getFormattedCode(constants, expansions) + "\n";
        for(int i = 1; i < pipelines.size(); i++) {
            mainMethodCode += pipelines.get(i).getFormattedCode(constants, expansions) + "\n";
        }
        shader += GLSLMethod.formatMethod("void main(void)", mainMethodCode);
        
        for(int i = 0; i < methods.length; i++) {
            shader += "\n" + methods[i].getFormattedCode(constants, expansions) + "\n";
        }
        
        return shader;
    }
    
    public String construct(String[][] constantNameValuePairs) {
        return construct(constantNameValuePairs, NO_EXPANSIONS);
    }
    
    public String construct(Expansion[] expansions) {
        return construct(new String[][]{}, expansions);
    }
    
    public String construct() {
        return construct(new String[][]{});
    }
    
    public ShaderFactory addPipeline(GLSLVariable in, GLSLEffect effect, GLSLVariable out) {
        effect.setInput(in);
        effect.setOutput(out);
        pipelines.add(effect);
        globalIOs.add(in);
        globalIOs.add(out);
        return this;
    }
    
    public static ShaderProgram createProgram(ShaderFactory vertex, 
            ShaderFactory fragment, String[][] constantNameValuePairs, 
            Expansion[] expansions, String[] attribs) {
        return new ShaderProgram(
                vertex.construct(constantNameValuePairs, expansions),
                fragment.construct(constantNameValuePairs, expansions),
                attribs
        );
    }
    
    public static ShaderProgram createProgram(ShaderFactory vertex, 
            ShaderFactory fragment, Expansion[] expansions, String[] attribs) {
        
        return createProgram(vertex, fragment, new String[][]{}, expansions, attribs);
    }
    
    public static ShaderProgram createProgram(ShaderFactory vertex, 
            ShaderFactory fragment, String[] attribs) {
        
        return createProgram(vertex, fragment, NO_EXPANSIONS, attribs);
    }
    
    public static ShaderProgram createProgram(ShaderFactory vertex, ShaderFactory fragment) {
        return createProgram(vertex, fragment, new String[]{});
    }
}
