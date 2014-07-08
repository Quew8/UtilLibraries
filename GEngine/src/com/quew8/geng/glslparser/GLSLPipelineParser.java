package com.quew8.geng.glslparser;

import com.quew8.codegen.glsl.Variable;
import com.quew8.geng.glslparser.GLSLEffectParser.GLSLEffect;
import com.quew8.geng.glslparser.GLSLShaderParser.GLSLElements;
import com.quew8.geng.xmlparser.XMLElementParser;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
public class GLSLPipelineParser extends GLSLParser<GLSLPipelineParser> {
    private static final String 
            IN_VAR = "I_VAR",
            OUT_VAR = "O_VAR";
    private GLSLVariableParser inVar;
    private GLSLVariableParser outVar;
    private final ArrayList<GLSLEffectParser> effects = new ArrayList<GLSLEffectParser>();

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
                    inVar = variableParser;
                    hasRequiredElement(IN_VAR);
                } else if (variableParser.isOutputVariable()) {
                    outVar = variableParser;
                    hasRequiredElement(OUT_VAR);
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
        Variable out = outVar.getVariable();
        elements.globals.add(in);
        elements.globals.add(out);
        GLSLEffect effect = 
                effects.isEmpty() ? 
                GLSLEffectParser.getNoEffect(in.getType(), out.getType()) : 
                GLSLEffectParser.getEffect(elements, effects);
        return effect.getCode()
                .replaceAll(Pattern.quote(effect.getInVar().getName()), in.getName())
                .replaceAll(Pattern.quote(effect.getOutVar().getName()), out.getName());
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
}
