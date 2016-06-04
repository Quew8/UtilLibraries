package com.quew8.glslparser;

import com.quew8.glslparser.GLSLShaderParser.GLSLElements;
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
    private final ArrayList<DirectiveDesc> directives = new ArrayList<DirectiveDesc>();
    
    public void addGlobalVariable(GLSLVariableParser variable) {
        globalVariables.add(variable);
    }
    
    public void add(DirectiveDesc[] directives) {
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
    
    public ArrayList<DirectiveDesc> getDirectives() {
        return directives;
    }
    
    public void add(GLSLElementStructure<?> other) {
        structs.addAll(other.structs);
        methods.addAll(other.methods);
        globalVariables.addAll(other.globalVariables);
        directives.addAll(other.directives);
    }
    
    public void addTo(GLSLElements elements) {
        directives.stream().forEach((directive) -> {
            elements.directives.add(directive);
        });
        globalVariables.stream().forEach((globalVariable) -> {
            elements.globals.add(globalVariable.getVariable());
        });
        methods.stream().forEach((method) -> {
            elements.methods.add(method.getMethod(elements));
        });
        structs.stream().forEach((struct) -> {
            elements.structures.add(struct.getStruct());
        });
    }
    
    @Override
    public HashMap<String, XMLElementParser> addElementParsers(HashMap<String, XMLElementParser> to) {
        super.addElementParsers(to);
        to.put(STRUCT, (XMLElementParser) (Element element) -> {
            structs.add(GLSLElementStructure.this.parseWith(element, new GLSLStructParser()));
        });
        to.put(METHOD, (XMLElementParser) (Element element) -> {
            methods.add(GLSLElementStructure.this.parseWith(element, new GLSLMethodParser()));
        });
        to.put(VERSION, (Element element) -> {
            directives.add(GLSLElementStructure.this.parseWith(element, new GLSLVersionParser()).getDirective());
        });
        to.put(EXTENSION, (XMLElementParser) (Element element) -> {
            directives.add(GLSLElementStructure.this.parseWith(element, new GLSLExtensionParser()).getDirective());
        });
        to.put(EXTRA, (XMLElementParser) (Element element) -> {
            directives.add(GLSLElementStructure.this.parseWith(element, new GLSLExtraParser()).getDirective());
        });
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
