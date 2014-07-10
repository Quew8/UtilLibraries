package com.quew8.geng.glslparser;

import com.quew8.codegen.CodeGenUtils;
import com.quew8.codegen.glsl.Directive;
import com.quew8.geng.glslparser.GLSLShaderParser.GLSLElements;
import com.quew8.geng.xmlparser.XMLElementParser;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
abstract class GLSLElementStructure<T extends GLSLElementStructure<T>> extends GLSLParser<T> {
    private final ArrayList<GLSLStructParser> structs = new ArrayList<GLSLStructParser>();
    private final ArrayList<GLSLMethodParser> methods = new ArrayList<GLSLMethodParser>();
    private final ArrayList<GLSLVariableParser> globalVariables = new ArrayList<GLSLVariableParser>();
    private final ArrayList<Directive> directives = new ArrayList<Directive>();
    
    public void addGlobalVariable(GLSLVariableParser variable) {
        globalVariables.add(variable);
    }
    
    public void add(Directive[] directives) {
        this.directives.addAll(Arrays.asList(directives));
    }
    
    public ArrayList<GLSLStructParser> getStructs() {
        return structs;
    }
    
    public ArrayList<GLSLMethodParser> getMethods() {
        return methods;
    }
    
    public ArrayList<GLSLVariableParser> getGlobalVariables() {
        return globalVariables;
    }
    
    public ArrayList<Directive> getDirectives() {
        return directives;
    }
    
    public void add(GLSLElementStructure<?> other) {
        structs.addAll(other.structs);
        methods.addAll(other.methods);
        globalVariables.addAll(other.globalVariables);
        directives.addAll(other.directives);
    }
    
    public void addTo(GLSLElements elements) {
        for(Directive directive: directives) {
            elements.directives.add(directive);
        }
        for(GLSLVariableParser globalVariable: globalVariables) {
            elements.globals.add(globalVariable.getVariable());
        }
        for(GLSLMethodParser method: methods) {
            elements.methods.add(method.getMethod(elements));
        }
        for(GLSLStructParser struct: structs) {
            elements.structures.add(struct.getStruct());
        }
    }
    
    @Override
    public HashMap<String, XMLElementParser> addElementParsers(HashMap<String, XMLElementParser> to) {
        super.addElementParsers(to);
        to.put(STRUCT, new XMLElementParser() {
            @Override
            public void parse(Element element) {
                structs.add(GLSLElementStructure.this.parseWith(element, new GLSLStructParser()));
            }
        });
        to.put(METHOD, new XMLElementParser() {
            @Override
            public void parse(Element element) {
                methods.add(GLSLElementStructure.this.parseWith(element, new GLSLMethodParser()));
            }
        });
        to.put(VERSION, new XMLElementParser() {
            @Override
            public void parse(Element element) {
                directives.add(Directive.getVersion(element.getText()));
            }
        }
        );
        to.put(EXTENSION, new XMLElementParser() {
            @Override
            public void parse(Element element) {
                directives.add(Directive.getExtension(
                        element.getText(),
                        Boolean.parseBoolean(element.attributeValue("enable", "true"))
                ));
            }
        }
        );
        to.put(EXTRA, new XMLElementParser() {
            @Override
            public void parse(Element element) {
                directives.add(CodeGenUtils.getElement(Directive.class, element.getText()));
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
        this.directives.addAll(source.getDirectives());
    }
}
