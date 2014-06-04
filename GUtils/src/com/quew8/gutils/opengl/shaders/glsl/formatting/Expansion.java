package com.quew8.gutils.opengl.shaders.glsl.formatting;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Quew8
 */
public abstract class Expansion implements MatcherReplaceCallback {
    private final String name;
    private final String[] properties;
    
    public Expansion(String name, String[] properties) {
        this.name = name;
        this.properties = properties;
    }
    
    @Override
    public ReplaceStruct callback(Matcher matcher) {
        System.err.println("Callback");
        HashMap<String, String> propertiesMapping = new HashMap<String, String>();
        for (int i = 0; i < properties.length; i++) {
            propertiesMapping.put(properties[i], matcher.group(i + 1));
        }
        return new ReplaceStruct(expand(matcher.group(properties.length + 1), propertiesMapping), matcher.start(), matcher.end());
    }
    
    @Override
    public Pattern getPattern() {
        String regex = "\\Q[?\\E" + name + "\\Q?\\E";
        for (int i = 0; i < properties.length; i++) {
            regex += " " + properties[i] + "\\Q=\\E" + "([^\\s=\\Q]\\E]+)";
        }
        regex += "\\Q]\\E(.+)\\Q[/?\\E" + name + "\\Q?]\\E";
        return Pattern.compile(regex, Pattern.DOTALL);
    }
    
    public abstract String expand(String code, HashMap<String, String> properties);
    
}
