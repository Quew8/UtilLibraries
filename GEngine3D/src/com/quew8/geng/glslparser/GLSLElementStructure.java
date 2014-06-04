package com.quew8.geng.glslparser;

import com.quew8.geng.xmlparser.XMLElementParser;
import com.quew8.gutils.opengl.shaders.glsl.GLSLCompileTimeConstant;
import com.quew8.gutils.opengl.shaders.glsl.GLSLExtra;
import com.quew8.gutils.opengl.shaders.glsl.GLSLMethod;
import com.quew8.gutils.opengl.shaders.glsl.GLSLStruct;
import com.quew8.gutils.opengl.shaders.glsl.GLSLVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
abstract class GLSLElementStructure<T extends GLSLElementStructure<T>> extends GLSLParser<T> {
    private final ArrayList<GLSLStruct> structs = new ArrayList<GLSLStruct>();
    private final ArrayList<GLSLMethod> methods = new ArrayList<GLSLMethod>();
    private final ArrayList<GLSLVariable> globalVariables = new ArrayList<GLSLVariable>();
    private final ArrayList<GLSLCompileTimeConstant> constants = new ArrayList<GLSLCompileTimeConstant>();
    private final ArrayList<GLSLExtra> extras = new ArrayList<GLSLExtra>();
    
    public GLSLElementStructure(String[] requiredElements, String[] requiredAttributes) {
        super(requiredElements, requiredAttributes);
    }
    
    public GLSLElementStructure() {
        
    }
    
    public void addGlobalVariable(GLSLVariable variable) {
        globalVariables.add(variable);
    }
    
    public void add(GLSLVariable[] variables) {
        this.globalVariables.addAll(Arrays.asList(variables));
    }
    
    public void add(GLSLCompileTimeConstant[] constants) {
        this.constants.addAll(Arrays.asList(constants));
    }
    
    public void add(GLSLExtra[] extras) {
        this.extras.addAll(Arrays.asList(extras));
    }
    
    public ArrayList<GLSLStruct> getStructs() {
        return structs;
    }
    
    public ArrayList<GLSLMethod> getMethods() {
        return methods;
    }
    
    public ArrayList<GLSLVariable> getGlobalVariables() {
        return globalVariables;
    }
    
    public ArrayList<GLSLCompileTimeConstant> getConstants() {
        return constants;
    }
    
    public ArrayList<GLSLExtra> getExtras() {
        return extras;
    }
    
    @Override
    public HashMap<String, XMLElementParser> addElementParsers(HashMap<String, XMLElementParser> to) {
        super.addElementParsers(to);
        to.put(STRUCT, new XMLElementParser() {
            @Override
            public void parse(Element element) {
                GLSLStructParser parser = GLSLElementStructure.this.parseWith(element, new GLSLStructParser());
                structs.add(parser.getStruct());
            }
        });
        to.put(METHOD, new XMLElementParser() {
            @Override
            public void parse(Element element) {
                GLSLMethodParser parser = GLSLElementStructure.this.parseWith(element, new GLSLMethodParser());
                methods.add(parser.getMethod());
            }
        });
        to.put(VERSION, new XMLElementParser() {
            @Override
            public void parse(Element element) {
                extras.add(GLSLExtra.getVersion(element.getText()));
            }
        }
        );
        to.put(EXTENSION, new XMLElementParser() {
            @Override
            public void parse(Element element) {
                extras.add(GLSLExtra.getExtension(
                        element.getText(),
                        Boolean.parseBoolean(element.attributeValue("enable", "true"))
                ));
            }
        }
        );
        to.put(CONSTANT, new XMLElementParser() {
            @Override
            public void parse(Element element) {
                GLSLCompileTimeConstantParser parser = GLSLElementStructure.this.parseWith(element, new GLSLCompileTimeConstantParser());
                constants.add(parser.getConstant());
            }
        }
        );
        to.put(EXTRA, new XMLElementParser() {
            @Override
            public void parse(Element element) {
                extras.add(new GLSLExtra(element.getText()));
            }
        }
        );
        return to;
    }
    
    @Override
    public void setSource(T source) {
        this.structs.addAll(source.getStructs());
        this.methods.addAll(source.getMethods());
        this.globalVariables.addAll(source.getGlobalVariables());
        this.constants.addAll(source.getConstants());
        this.extras.addAll(source.getExtras());
    }
}
