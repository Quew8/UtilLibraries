package com.quew8.geng.content;

import com.quew8.geng.glslparser.GLSLProgramParser;
import com.quew8.gutils.content.ContentReader;
import com.quew8.gutils.content.Source;
import com.quew8.gutils.opengl.shaders.ShaderProgram;
import com.quew8.gutils.opengl.shaders.ShaderUtils;
import com.quew8.gutils.opengl.shaders.glsl.ShaderFactory;
import java.util.Map.Entry;

/**
 *
 * @author Quew8
 */
public class ShaderReader implements ContentReader<ShaderProgram> {
    private static final String 
            ATRIBS = "attribs",
            REPLACEMENTS = "replacements",
            UNIFORMS = "uniforms";
            
    @Override
    public ShaderProgram read(Source in) {
        String[] attribs = in.getParams().get(ATRIBS).split("[\\s]+");
        String[][] replacements;
        if(in.getParamLists().containsKey(REPLACEMENTS)) {
            Entry<String, String>[] entries = in.getParamLists().get(REPLACEMENTS);
            replacements = new String[entries.length][];
            for(int i = 0; i < replacements.length; i++) {
                replacements[i] = new String[]{entries[i].getKey(), entries[i].getValue()};
            }
        } else {
            replacements = new String[0][];
        }
        
        GLSLProgramParser parser = new GLSLProgramParser();
        parser.read(in.getSource());
        ShaderProgram program = parser.createProgram(
                replacements,
                ShaderFactory.DEFAULT_EXPANSIONS, 
                attribs
        );
        if(in.getParamLists().containsKey(UNIFORMS)) {
            program.use();
            Entry<String, String>[] entries = in.getParamLists().get(UNIFORMS);
            for(int i = 0; i < replacements.length; i++) {
                ShaderUtils.setUniformVari(program.getId(), entries[i].getKey(), Integer.parseInt(entries[i].getValue()));
            }
        }
        return program;
    }
}
