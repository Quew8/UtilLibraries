package com.quew8.gutils.opengl.shaders.glsl.formatting;

import java.util.HashMap;
import java.util.regex.Pattern;

/**
 *
 * @author Quew8
 */
public class ForLoopExpansion extends Expansion {
    public static final ForLoopExpansion INSTANCE = new ForLoopExpansion();
    
    public ForLoopExpansion() {
        super("FORLOOP", new String[]{"var", "init", "nIter", "step"});
    }
    
    @Override
    public String expand(String code, HashMap<String, String> properties) {
        String var = properties.get("var");
        int init = Integer.parseInt(properties.get("init"));
        int nIter = Integer.parseInt(properties.get("nIter"));
        int step = Integer.parseInt(properties.get("step"));
        String expanded = "";
        for (int i = 0, j = init; i < nIter; i++, j += step) {
            String loop = code.replaceAll(Pattern.quote("~" + var + "~"), Integer.toString(j));
            expanded += loop + "\n";
        }
        return expanded;
    }
    
}
