package com.quew8.geng.glslparser;

import com.quew8.geng.xmlparser.XMLElementParser;
import com.quew8.geng.xmlparser.XMLAttributeParser;
import com.quew8.gutils.opengl.shaders.glsl.ShaderFactory;
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
    
    public void addPipelines(ShaderFactory shaderFactory) {
        finalized();
        for (int i = 0; i < pipelines.size(); i++) {
            shaderFactory.addPipeline(pipelines.get(i).getInputVar(), pipelines.get(i).getEffect(), pipelines.get(i).getOutputVar());
        }
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
    
}
