package com.quew8.geng.glslparser;

import com.quew8.geng.xmlparser.XMLElementParser;
import com.quew8.gutils.opengl.shaders.glsl.GLSLEffect;
import com.quew8.gutils.opengl.shaders.glsl.GLSLVariable;
import java.util.HashMap;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
public class GLSLPipelineParser extends GLSLParser<GLSLPipelineParser> {
    private static final String 
            IN_VAR = "I_VAR",
            OUT_VAR = "O_VAR";
    private GLSLVariable inVar;
    private GLSLVariable outVar;
    private GLSLEffect effect = null;

    public GLSLPipelineParser() {
        super(new String[]{IN_VAR, EFFECT, OUT_VAR}, new String[]{});
    }
    
    @Override
    public HashMap<String, XMLElementParser> addElementParsers(HashMap<String, XMLElementParser> to) {
        to = super.addElementParsers(to);
        to.put(VARIABLE, new XMLElementParser() {
            @Override
            public void parse(Element element) {
                GLSLVariableParser variableParser = GLSLPipelineParser.this.parseWith(element, new GLSLVariableParser());
                if (variableParser.isInputVariable()) {
                    inVar = variableParser.getVariable();
                    hasRequiredElement(IN_VAR);
                } else if (variableParser.isOutputVariable()) {
                    outVar = variableParser.getVariable();
                    hasRequiredElement(OUT_VAR);
                }
            }
        });
        to.put(EFFECT, new XMLElementParser() {
            @Override
            public void parse(Element element) {
                GLSLEffectParser effectParserIn = new GLSLEffectParser();
                effectParserIn = GLSLPipelineParser.this.parseWith(element, effectParserIn);
                GLSLEffect thisEffect = effectParserIn.getEffect();
                effect = effect == null ? thisEffect : effect.combine(thisEffect);
            }
        });
        return to;
    }

    public GLSLVariable getInputVar() {
        finalized();
        return inVar;
    }

    public GLSLVariable getOutputVar() {
        finalized();
        return outVar;
    }

    public GLSLEffect getEffect() {
        finalized();
        return effect;
    }
    
    @Override
    public GLSLPipelineParser getInstance() {
        return new GLSLPipelineParser();
    }
    
    @Override
    public void setSource(GLSLPipelineParser source) {
        this.effect = 
                this.effect == null ? 
                source.effect : 
                source.effect.combine(this.effect);
        this.inVar = source.inVar;
        this.outVar = source.outVar;
    }
    
}
