package com.quew8.gutils.opengl.shaders.glsl.formatting;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Quew8
 */
public interface MatcherReplaceCallback {
    
    Pattern getPattern();
    
    ReplaceStruct callback(Matcher matcher);
    
}
