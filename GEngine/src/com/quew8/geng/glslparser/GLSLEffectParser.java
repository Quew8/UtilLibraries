package com.quew8.geng.glslparser;

import com.quew8.codegen.glsl.GLSLCodeGenUtils;
import com.quew8.codegen.glsl.Type;
import com.quew8.codegen.glsl.Variable;
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
public class GLSLEffectParser extends GLSLElementStructure<GLSLEffectParser> {
    private static final String 
            IN_VAR = "IN_VARIABLE", 
            OUT_VAR = "OUT_VARIABLE";
    
    private String code;
    private GLSLVariableParser inVar;
    private GLSLVariableParser outVar;
    
    public GLSLEffectParser() {
        super(new String[]{CODE, IN_VAR, OUT_VAR}, new String[]{});
    }
    
    private GLSLEffect getEffect(GLSLElements elements) {
        finalized();
        addTo(elements);
        return new GLSLEffect(
                code, inVar.getVariable(), outVar.getVariable()
        );
    }
    
    @Override
    public HashMap<String, XMLElementParser> addElementParsers(HashMap<String, XMLElementParser> to) {
        to = super.addElementParsers(to);
        to.put(CODE, new XMLElementParser() {
            @Override
            public void parse(Element element) {
                code = element.getText().trim();
            }
        }
        );
        to.put(VARIABLE, new XMLElementParser() {
            @Override
            public void parse(Element element) {
                GLSLVariableParser variableParser = parseWith(element, new GLSLVariableParser());
                if(variableParser.isInputVariable()) {
                    inVar = variableParser;
                    hasRequiredElement(IN_VAR);
                } else if(variableParser.isOutputVariable()) {
                    outVar = variableParser;
                    hasRequiredElement(OUT_VAR);
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
        this.inVar = source.inVar;
        this.outVar = source.outVar;
    }
    
    @Override
    public GLSLEffectParser getInstance() {
        return new GLSLEffectParser();
    }
    
    public static GLSLEffect getEffect(GLSLElements elements, ArrayList<GLSLEffectParser> effects) {
        GLSLEffect effect = effects.get(0).getEffect(elements);
        for(int i = 1; i < effects.size(); i++) {
            effect = combine(effect, effects.get(i).getEffect(elements));
        }
        return effect;
    }
    
    private static GLSLEffect combine(GLSLEffect a, GLSLEffect b) {
        String code = GLSLCodeGenUtils.getConstruction()
                .add(a.outVar)
                .addNewline(a.code, b.code.replaceAll(Pattern.quote(b.inVar.getName()), a.outVar.getName()))
                .get();
        return new GLSLEffect(code, a.inVar, b.outVar);
    }
    
    public static GLSLEffect getNoEffect(Type in, Type out) {
        return new GLSLEffect("noEffectOutVar = noEffectInVar;", new Variable(in, "noEffectInVar"), new Variable(out, "noEffectOutVar"));
    }
    
    public static class GLSLEffect {
        private final String code;
        private final Variable inVar;
        private final Variable outVar;

        public GLSLEffect(String code, Variable inVar, Variable outVar) {
            this.code = code;
            this.inVar = inVar;
            this.outVar = outVar;
        }
        
        public String getCode() {
            return code;
        }
        
        public Variable getInVar() {
            return inVar;
        }
        
        public Variable getOutVar() {
            return outVar;
        }
    }
}
