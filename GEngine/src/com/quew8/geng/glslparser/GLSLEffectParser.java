package com.quew8.geng.glslparser;

import com.quew8.codegen.glsl.GLSLElement;
import com.quew8.codegen.glsl.Type;
import com.quew8.codegen.glsl.Variable;
import com.quew8.geng.glslparser.GLSLShaderParser.GLSLElements;
import com.quew8.geng.xmlparser.XMLElementParser;
import com.quew8.geng.xmlparser.XMLParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.dom4j.Element;

/**
 *
 * @author Quew8
 */
public class GLSLEffectParser extends GLSLElementStructure<GLSLEffectParser> {
    private String code;
    private boolean inLine = false;
    private final ArrayList<GLSLVariableParser> inVars = new ArrayList<GLSLVariableParser>();
    private final ArrayList<GLSLVariableParser> outVars = new ArrayList<GLSLVariableParser>();
    
    private GLSLEffect getEffect(GLSLElements elements) {
        addTo(elements);
        Variable[] rInVars = new Variable[inVars.size()];
        for(GLSLVariableParser inVar: inVars) {
            rInVars[inVar.getIndex()] = inVar.getVariable();
        }
        Variable[] rOutVars = new Variable[outVars.size()];
        for(GLSLVariableParser outVar: outVars) {
            rOutVars[outVar.getIndex()] = outVar.getVariable();
        }
        if(inLine) {
            return new GLSLEffect(new EffectCode(rOutVars[0].getNameString() + " = " + code + ";"), code, rInVars, rOutVars);
        } else {
            return new GLSLEffect(new EffectCode(code), null, rInVars, rOutVars);
        }
    }
    
    @Override
    public HashMap<String, XMLElementParser> addElementParsers(HashMap<String, XMLElementParser> to) {
        to = super.addElementParsers(to);
        to.put(CODE, new XMLElementParser() {
            @Override
            public void parse(Element element) {
                code = element.getText().trim();
                inLine = false;
            }
        }
        );
        to.put(IN_LINE_CODE, new XMLElementParser() {
            @Override
            public void parse(Element element) {
                code = element.getText().trim();
                inLine = true;
            }
        }
        );
        to.put(VARIABLE, new XMLElementParser() {
            @Override
            public void parse(Element element) {
                GLSLVariableParser variableParser = parseWith(element, new GLSLVariableParser());
                if(variableParser.isInputVariable()) {
                    inVars.add(variableParser);
                } else if(variableParser.isOutputVariable()) {
                    outVars.add(variableParser);
                } else if(variableParser.isGlobalVariable()) {
                    addGlobalVariable(variableParser);
                }
            }
        }
        );
        return to;
    }
    
    @Override
    public void setSource(GLSLEffectParser source) {
        super.setSource(source);
        this.code = source.code;
        this.inLine = source.inLine;
        this.inVars.addAll(source.inVars);
        this.outVars.addAll(source.outVars);
    }
    
    @Override
    public GLSLEffectParser getInstance() {
        return new GLSLEffectParser();
    }
    
    protected GLSLVariableParser getInVar(int index) {
        for(GLSLVariableParser inVar: inVars) {
            if (inVar.getIndex() == index) {
                return inVar;
            }
        }
        return null;
    }
    
    protected GLSLVariableParser getOutVar(int index) {
        for(GLSLVariableParser outVar: outVars) {
            if(outVar.getIndex() == index) {
                return outVar;
            }
        }
        return null;
    }
    
    protected boolean isDeadEnd() {
        return outVars.isEmpty();
    }
        
    protected int getNInVars() {
        return inVars.size();
    }

    protected int getNOutVars() {
        return outVars.size();
    }
    
    @Override
    public XMLParseException onParsingDone() {
        if(code == null || code.isEmpty()) {
            return new XMLParseException("Effect has no code");
        }
        ensureCorrectIndices(inVars);
        ensureCorrectIndices(outVars);
        if(inLine && getNOutVars() != 1) {
            return new XMLParseException("In line code effects does not have one output.");
        }
        return null;
    }
    
    protected static GLSLEffect getEffect(GLSLElements elements, ArrayList<GLSLEffectParser> effects) {
        GLSLEffect effect = effects.get(0).getEffect(elements);
        for(int i = 1; i < effects.size(); i++) {
            effect = combine(effect, effects.get(i).getEffect(elements));
        }
        return effect;
    }
    
    private static GLSLEffect combine(GLSLEffect a, GLSLEffect b) {
        if(a.getNOutVars() != b.getNInVars()) {
            throw new RuntimeException("Effects input do not match");
        }
        if(a.hasInLine()) {
            if(b.hasInLine()) {
                return new GLSLEffect(
                        b.getCode().replaceReferences(b.getInVar(0), a.getInLineCode()),
                        b.getInLineCode().replaceAll(
                                Pattern.quote(b.getInVar(0).getNameString()), 
                                a.getInLineCode()
                        ), 
                        a.inVars, 
                        b.outVars
                );
            } else {
                return new GLSLEffect(
                        b.getCode().replaceReferences(b.getInVar(0), a.getInLineCode()), 
                        null, 
                        a.inVars, 
                        b.outVars
                );
            }
        } else {
            Variable[] aOuts = new Variable[a.getNOutVars()];
            for(int i = 0; i < a.getNOutVars(); i++) {
                aOuts[i] = a.getOutVar(i);
                b.getCode().replaceReferences(b.getInVar(i), aOuts[i]);
            }
            return new GLSLEffect(
                    new EffectCode("<<\n<aOutputs>>>\n<<aCode>>\n<<bCode>>")
                            .putDefaultValue("aCode", a.getCode())
                            .putDefaultValue("bCode", b.getCode())
                            .putDefaultValues("aOutputs", aOuts), 
                    null, 
                    a.inVars, 
                    b.outVars
            );
        }
    }
    
    public static GLSLEffect getNoEffect(Type in, Type out) {
        return new GLSLEffect(
                new EffectCode("noEffectOutVar = noEffectInVar;"), 
                "noEffectInVar",
                new Variable[]{new Variable(in, "noEffectInVar")}, 
                new Variable[]{new Variable(out, "noEffectOutVar")});
    }
    
    public static void ensureCorrectIndices(ArrayList<GLSLVariableParser> vars) {
        boolean[] usedIndices = new boolean[vars.size()];
        Arrays.fill(usedIndices, false);
        for(GLSLVariableParser var: vars) {
            int index = var.getIndex();
            if(index >= usedIndices.length) {
                throw new XMLParseException("Index greater than number of supplied variables: " + index);
            }
            if(usedIndices[index]) {
                throw new XMLParseException("Index used more than once: " + index);
            }
        }
    }
    
    public static class GLSLEffect {
        private final EffectCode code;
        private final String inLineCode;
        private final Variable[] inVars;
        private final Variable[] outVars;

        public GLSLEffect(EffectCode code, String inLineCode, Variable[] inVars, Variable[] outVars) {
            this.code = code;
            this.inLineCode = inLineCode;
            this.inVars = inVars;
            this.outVars = outVars;
        }
        
        public EffectCode getCode() {
            return code;
        }
        
        public String getInLineCode() {
            return inLineCode;
        }
        
        public boolean hasInLine() {
            return inLineCode != null;
        }
        
        public int getNInVars() {
            return inVars.length;
        }
        
        public int getNOutVars() {
            return outVars.length;
        }
        
        public Variable getInVar(int index) {
            return inVars[index];
        }
        
        public Variable getOutVar(int index) {
            return outVars[index];
        }
    }
    
    public static class EffectCode extends GLSLElement<EffectCode> {
        
        public EffectCode(String definition) {
            super(definition);
        }
        
        public EffectCode replaceReferences(Variable v, String with) {
            setDefinition(getDefinition()
                    .replaceAll(
                            Pattern.quote(v.getNameString()), 
                            Matcher.quoteReplacement(with)
                    )
            );
            return this;
        }
        
        public EffectCode replaceReferences(Variable v, Variable with) {
            return replaceReferences(v, with.getNameString());
        }
    }
}
