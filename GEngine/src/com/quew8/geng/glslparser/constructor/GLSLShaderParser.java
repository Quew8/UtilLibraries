package com.quew8.geng.glslparser.constructor;

import com.quew8.codegen.glsl.Block;
import com.quew8.codegen.glsl.GLSLElement;
import com.quew8.codegen.glsl.GLSLGenData;
import com.quew8.codegen.glsl.Method;
import com.quew8.codegen.glsl.SrcFile;
import com.quew8.codegen.glsl.Struct;
import com.quew8.codegen.glsl.Variable;
import com.quew8.geng.xmlparser.XMLAttributeParser;
import com.quew8.geng.xmlparser.XMLElementParser;
import com.quew8.geng.xmlparser.XMLParseException;
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
    
    
    public SrcFile getShader(int minGLSL, int maxGLSL) {
        GLSLElements elements = new GLSLElements();
        final GLSLElement<?>[] pipelineElems = new GLSLElement<?>[pipelines.size()];
        for(int i = 0; i < pipelines.size(); i++) {
            pipelineElems[i] = pipelines.get(i).getPipeline(elements);
        }
        elements.methods.add(new Method("main", new ShaderMainBlock(pipelineElems)));
        elements.removeDuplicates();
        return new SrcFile()
                .setDirectives(DirectiveDesc.organize(minGLSL, maxGLSL, elements.directives))
                .setMethods(elements.methods.toArray(new Method[elements.methods.size()]))
                .setStructs(elements.structures.toArray(new Struct[elements.structures.size()]))
                .setVariables(elements.globals.toArray(new Variable[elements.globals.size()]));
    }

    @Override
    public XMLParseException onParsingDone() {
        if(shaderType == null || shaderType.isEmpty()) {
            return new XMLParseException("Attribute type is empty in shader");
        }
        return super.onParsingDone();
    }
    
    public class GLSLElements {
        final ArrayList<DirectiveDesc> directives = new ArrayList<DirectiveDesc>();
        final ArrayList<Variable> globals = new ArrayList<Variable>();
        final ArrayList<Method> methods = new ArrayList<Method>();
        final ArrayList<Struct> structures = new ArrayList<Struct>();
        
        public void removeDuplicates() {
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
    
    private static boolean match(Variable a, Variable b) {
        return a.getNameString().equals(b.getNameString());
    }
    
    private static boolean match(Method a, Method b) {
        if(!a.getNameString().equals(b.getNameString())) {
            return false;
        }
        if(a.getParameters().length != b.getParameters().length) {
            return false;
        }
        for(int i = 0; i < a.getParameters().length; i++) {
            if(!a.getParameters()[i].getType().getNameString().equals(b.getParameters()[i].getType().getNameString())) {
                return false;
            }
        }
        return true;
    }
    
    private static boolean match(Struct a, Struct b) {
        return a.getNameString().equals(b.getNameString());
    }
    
    public static class ShaderMainBlock extends Block {
        private final GLSLElement<?>[] pipelines;
        
        public ShaderMainBlock(GLSLElement<?>[] pipelines) {
            super("");
            this.pipelines = pipelines;
        }
        
        @Override
        public com.quew8.codegen.Element<GLSLGenData, ?> getBlock() {
            return com.quew8.codegen.Element.<GLSLGenData>wrap("<<\n<pipelines>>>").putDefaultValues("pipelines", pipelines);
        }
    }
}
