package com.quew8.geng.glslparser.constructor;

import com.quew8.codegen.glsl.GLSLElement;
import com.quew8.codegen.glsl.Variable;
import com.quew8.geng.glslparser.constructor.GLSLEffectParser.EffectCode;
import com.quew8.geng.glslparser.constructor.GLSLEffectParser.GLSLEffect;
import com.quew8.geng.glslparser.constructor.GLSLShaderParser.GLSLElements;
import com.quew8.geng.xmlparser.XMLElementParser;
import com.quew8.geng.xmlparser.XMLParseException;
import java.util.ArrayList;
import java.util.HashMap;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
public class GLSLPipelineParser extends GLSLParser<GLSLPipelineParser> {
    private final ArrayList<GLSLVariableParser> inVars = new ArrayList<GLSLVariableParser>();
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
                    inVars.add(variableParser);
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

    public GLSLElement<?> getPipeline(GLSLElements elements) {
        Variable[] inVariables = new Variable[inVars.size()];
        for(GLSLVariableParser v: inVars) {
            Variable vv = v.getVariable();
            inVariables[v.getIndex()] = vv;
            elements.globals.add(vv);
        }
        Variable out = null;
        if(outVar != null) {
            out = outVar.getVariable();
            elements.globals.add(out);
        }
        GLSLEffect effect;
        if(effects.isEmpty()) {
            effect = GLSLEffectParser.getNoEffect(inVariables[0].getType(), out.getType());
        } else {
            effect = GLSLEffectParser.getEffect(elements, effects);
        }
        EffectCode code = effect.getCode();
        for(int i = 0; i < inVariables.length; i++) {
            code.replaceReferences(effect.getInVar(i), inVariables[i]);
        }
        if(outVar != null) {
            code.replaceReferences(effect.getOutVar(0), out);
        }
        return code;
    }
    
    public int getNInVars() {
        return inVars.size();
    }
    
    public GLSLVariableParser getInputVar(int index) {
        for(GLSLVariableParser inVar: inVars) {
            if(inVar.getIndex() == index) {
                return inVar;
            }
        }
        return null;
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
        this.inVars.addAll(source.inVars);
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
            if(effects.get(i).getNOutVars() != effects.get(i + 1).getNInVars()) {
                return new XMLParseException("Number of effect inputs does not match effect outputs: " + i + " " + (i + 1));
            }
            for(int j = 0; j < effects.get(i).getNOutVars(); j++) {
                if(!effects.get(i).getOutVar(j).compatible(effects.get(i+1).getInVar(j))) {
                    return new XMLParseException("Incompatible effects: " + i + ":" + j + " and " + (i+1) + ":" + j);
                }
            }
        }
        GLSLEffectParser.ensureCorrectIndices(inVars);
        if(!effects.isEmpty()) {
            if(effects.get(0).getNInVars() != getNInVars()) {
                System.out.println(effects.get(0).getNInVars() + " " + getNInVars());
                for(int i = 0; i < effects.get(0).getNInVars(); i++) {
                    System.out.println(i);
                    System.out.println(effects.get(0).getInVar(i).getVariable().getName());
                }
                return new XMLParseException("First effect requires multiple inputs");
            }
            for(int i = 0; i < inVars.size(); i++) {
                if(!getInputVar(i).compatible(effects.get(0).getInVar(i))) {
                    return new XMLParseException("First effect incompatible with pipeline input " + i);
                }
            }
            if(effects.get(effects.size() - 1).getNOutVars() > 1) {
                return new XMLParseException("Last effect yields multiple outputs");
            }
            if(outVar != null && !outVar.compatible(effects.get(effects.size() - 1).getOutVar(0))) {
                return new XMLParseException("Last effect incompatible with pipeline output");
            }
        } else if(getNInVars() != 1) {
            return new XMLParseException("No-effect-pipeline with multiple inputs");
        }
        return super.onParsingDone();
    }
}
