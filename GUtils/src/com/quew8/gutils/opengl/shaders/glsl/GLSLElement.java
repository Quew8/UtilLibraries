package com.quew8.gutils.opengl.shaders.glsl;

import com.quew8.gutils.opengl.shaders.glsl.formatting.ConstantMatcherReplaceCallback;
import com.quew8.gutils.opengl.shaders.glsl.formatting.Expansion;
import com.quew8.gutils.opengl.shaders.glsl.formatting.ReplaceStruct;
import com.quew8.gutils.opengl.shaders.glsl.formatting.MatcherReplaceCallback;
import java.util.HashMap;
import java.util.regex.Matcher;

/**
 *
 * @author Quew8
 */
public abstract class GLSLElement {
    
    abstract String getCode();
    
    String getFormattedCode(HashMap<String, GLSLCompileTimeConstant> constants, Expansion[] expansions) {
        String code = getCode();
        code = format(code, new ConstantMatcherReplaceCallback(constants));
        for(int i = 0; i < expansions.length; i++) {
            code = format(code, expansions[i]);
        }
        return code;
    }
    
    static String format(String original, MatcherReplaceCallback callback) {
        int lastReadIndex = 0;
        String formattedCode = "";
        Matcher matcher = callback.getPattern().matcher(original);
        while(matcher.find()) {
            ReplaceStruct replace = callback.callback(matcher);
            formattedCode += original.substring(lastReadIndex, replace.startIndex) + replace.replace;
            lastReadIndex = replace.endIndex;
        }
        formattedCode += original.substring(lastReadIndex);
        return formattedCode;
    }
}
