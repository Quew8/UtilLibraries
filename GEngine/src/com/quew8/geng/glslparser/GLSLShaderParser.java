package com.quew8.geng.glslparser;

import com.quew8.codegen.glsl.Block;
import com.quew8.codegen.glsl.Directive;
import com.quew8.codegen.glsl.Method;
import com.quew8.codegen.glsl.SrcFile;
import com.quew8.codegen.glsl.Struct;
import com.quew8.codegen.glsl.Variable;
import com.quew8.geng.xmlparser.XMLAttributeParser;
import com.quew8.geng.xmlparser.XMLElementParser;
import java.util.ArrayList;
import java.util.HashMap;
import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
public class GLSLShaderParser extends GLSLParser<GLSLShaderParser> {
    private String shaderType;
    private final ArrayList<GLSLPipelineParser> pipelines = new ArrayList<GLSLPipelineParser>();
    
    public GLSLShaderParser() {
        super(new String[]{}, new String[]{TYPE});
    }
    
    @Override
    public HashMap<String, XMLElementParser> addElementParsers(HashMap<String, XMLElementParser> to) {
        to = super.addElementParsers(to);
        to.put(PIPELINE, new XMLElementParser() {
            @Override
            public void parse(Element element) {
                pipelines.add(GLSLShaderParser.this.parseWith(element, new GLSLPipelineParser()));
            }
        });
        return to;
    }
    
    @Override
    public HashMap<String, XMLAttributeParser> addAttributeParsers(HashMap<String, XMLAttributeParser> to) {
        to = super.addAttributeParsers(to);
        to.put(TYPE, new XMLAttributeParser() {
            @Override
            public void parse(Attribute attribute, Element parent) {
                shaderType = attribute.getValue();
            }
        });
        return to;
    }
    
    public String getShaderType() {
        finalized();
        return shaderType;
    }
    
    @Override
    public GLSLShaderParser getInstance() {
        return new GLSLShaderParser();
    }
    
    @Override
    public void setSource(GLSLShaderParser source) {
        this.shaderType = source.shaderType;
        this.pipelines.addAll(source.pipelines);
    }
    
    
    public SrcFile getShader() {
        GLSLElements elements = new GLSLElements();
        String mainBlock = pipelines.get(0).getPipeline(elements);
        for(int i = 1; i < pipelines.size(); i++) {
            mainBlock += "\n" + pipelines.get(i).getPipeline(elements);
        }
        elements.methods.add(new Method("main", new Block(mainBlock)));
        elements.removeDuplicates();
        return new SrcFile()
                .setDirectives(elements.directives.toArray(new Directive[elements.directives.size()]))
                .setMethods(elements.methods.toArray(new Method[elements.methods.size()]))
                .setStructs(elements.structures.toArray(new Struct[elements.structures.size()]))
                .setVariables(elements.globals.toArray(new Variable[elements.globals.size()]));
    }
    
    public class GLSLElements {
        final ArrayList<Directive> directives = new ArrayList<Directive>();
        final ArrayList<Variable> globals = new ArrayList<Variable>();
        final ArrayList<Method> methods = new ArrayList<Method>();
        final ArrayList<Struct> structures = new ArrayList<Struct>();
        
        public void removeDuplicates() {
            for(int i = 0; i < directives.size(); i++) {
                for(int j = i + 1; j < directives.size(); j++) {
                    if(match(directives.get(i), directives.get(j))) {
                        directives.remove(j--);
                    }
                }
            }
            for(int i = 0; i < globals.size(); i++) {
                if(globals.get(i).isCoreVariable()) {
                    globals.remove(i--);
                } else {
                    for(int j = i + 1; j < globals.size(); j++) {
                        if(match(globals.get(i), globals.get(j))) {
                            globals.remove(j--);
                        }
                    }
                }
            }
            for(int i = 0; i < methods.size(); i++) {
                for(int j = i + 1; j < methods.size(); j++) {
                    if(match(methods.get(i), methods.get(j))) {
                        methods.remove(j--);
                    }
                }
            }
            for(int i = 0; i < structures.size(); i++) {
                for(int j = i + 1; j < structures.size(); j++) {
                    if(match(structures.get(i), structures.get(j))) {
                        structures.remove(j--);
                    }
                }
            }
        }
    }
    
    private static boolean match(Directive a, Directive b) {
        return a.getName().equals(b.getName()) && a.getBody().equals(b.getBody());
    }
    
    private static boolean match(Variable a, Variable b) {
        return a.getName().equals(b.getName());
    }
    
    private static boolean match(Method a, Method b) {
        if(!a.getName().equals(b.getName())) {
            return false;
        }
        if(a.getParameters().length != b.getParameters().length) {
            return false;
        }
        for(int i = 0; i < a.getParameters().length; i++) {
            if(!a.getParameters()[i].getType().getName().equals(b.getParameters()[i].getType().getName())) {
                return false;
            }
        }
        return true;
    }
    
    private static boolean match(Struct a, Struct b) {
        return a.getName().equals(b.getName());
    }
}
