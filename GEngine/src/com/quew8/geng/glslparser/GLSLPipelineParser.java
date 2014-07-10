package com.quew8.geng.glslparser;

import com.quew8.codegen.glsl.Variable;
import com.quew8.geng.glslparser.GLSLEffectParser.GLSLEffect;
import com.quew8.geng.glslparser.GLSLShaderParser.GLSLElements;
import com.quew8.geng.xmlparser.XMLElementParser;
import com.quew8.geng.xmlparser.XMLParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
public class GLSLPipelineParser extends GLSLParser<GLSLPipelineParser> {
    private GLSLVariableParser inVar;
    private GLSLVariableParser outVar;
    private final ArrayList<GLSLEffectParser> effects = new ArrayList<GLSLEffectParser>();
    
    @Override
    public HashMap<String, XMLElementParser> addElementParsers(HashMap<String, XMLElementParser> to) {
        to = super.addElementParsers(to);
        to.put(VARIABLE, new XMLElementParser() {
            @Override
            public void parse(Element element) {
                GLSLVariableParser variableParser = GLSLPipelineParser.this.parseWith(element, new GLSLVariableParser());
                if (variableParser.isInputVariable()) {
                    inVar = variableParser;
                } else if (variableParser.isOutputVariable()) {
                    outVar = variableParser;
                }
            }
        });
        to.put(EFFECT, new XMLElementParser() {
            @Override
            public void parse(Element element) {
                effects.add(GLSLPipelineParser.this.parseWith(element, new GLSLEffectParser()));
            }
        });
        return to;
    }

    public String getPipeline(GLSLElements elements) {
        Variable in = inVar.getVariable();
        elements.globals.add(in);
        Variable out = null;
        if(outVar != null) {
            out = outVar.getVariable();
            elements.globals.add(out);
        }
        GLSLEffect effect = 
                effects.isEmpty() ? 
                GLSLEffectParser.getNoEffect(in.getType(), out.getType()) : 
                GLSLEffectParser.getEffect(elements, effects);
        if(outVar == null) {
            return effect.getCode()
                .replaceAll(Pattern.quote(effect.getInVar().getName()), in.getName());
        } else {
            return effect.getCode()
                    .replaceAll(Pattern.quote(effect.getInVar().getName()), in.getName())
                    .replaceAll(Pattern.quote(effect.getOutVar().getName()), out.getName());
        }
    }
    
    public GLSLVariableParser getInputVar() {
        return inVar;
    }

    public GLSLVariableParser getOutputVar() {
        return outVar;
    }
    
    @Override
    public GLSLPipelineParser getInstance() {
        return new GLSLPipelineParser();
    }
    
    @Override
    public void setSource(GLSLPipelineParser source) {
        this.effects.addAll(source.effects);
        this.inVar = source.inVar;
        this.outVar = source.outVar;
    }

    @Override
    public XMLParseException onParsingDone() {
        if(outVar == null) {
            if(effects.isEmpty()) {
                return new XMLParseException("No-effect-dead-end-pipeline");
            } else if(!effects.get(effects.size() - 1).isDeadEnd()) {
                return new XMLParseException("Non-dead-end-effect terminating dead-end-pipeline");
            }
        }
        for(int i = 0; i < effects.size() - 1; i++) {
            if(effects.get(i).isDeadEnd()) {
                return new XMLParseException("Non-pipeline-terminating-dead-end-effect");
            }
            if(!effects.get(i).getOutVar().compatible(effects.get(i+1).getInVar())) {
                return new XMLParseException("Incompatible effects: " + i + "th and " + (i+1) + "th");
            }
        }
        if(!effects.isEmpty()) {
            if(!inVar.compatible(effects.get(0).getInVar())) {
                return new XMLParseException("First effect incompatible with pipeline input");
            }
            if(outVar != null && !outVar.compatible(effects.get(effects.size() - 1).getOutVar())) {
                return new XMLParseException("Last effect incompatible with pipeline output");
            }
        }
        return super.onParsingDone();
    }
}
