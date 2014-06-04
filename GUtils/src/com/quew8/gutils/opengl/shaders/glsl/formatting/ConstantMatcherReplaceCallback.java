package com.quew8.gutils.opengl.shaders.glsl.formatting;

import com.quew8.gutils.opengl.shaders.glsl.GLSLCompileTimeConstant;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Quew8
 */
public class ConstantMatcherReplaceCallback implements MatcherReplaceCallback {
    private final HashMap<String, GLSLCompileTimeConstant> constants;
    
    public ConstantMatcherReplaceCallback(HashMap<String, GLSLCompileTimeConstant> constants) {
        this.constants = constants;
    }
    
    @Override
    public ReplaceStruct callback(Matcher matcher) {
        String constantName = matcher.group(1);
        GLSLCompileTimeConstant constant = constants.get(constantName);
        if (constant == null) {
            return new ReplaceStruct(matcher.group(), matcher.start(), matcher.end());
        }
        return new ReplaceStruct(constant.getValue(), matcher.start(), matcher.end());
    }
    
    @Override
    public Pattern getPattern() {
        return Pattern.compile("~([\\w_]+)~");
    }
    
}
