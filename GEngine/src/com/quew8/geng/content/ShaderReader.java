package com.quew8.geng.content;

import com.quew8.geng.glslparser.GLSLProgramParser;
import com.quew8.gutils.content.ContentReader;
import com.quew8.gutils.content.Source;
import com.quew8.gutils.content.SourceSheet;
import com.quew8.gutils.opengl.shaders.ShaderProgram;
import com.quew8.gutils.opengl.shaders.ShaderUtils;
import java.util.HashMap;
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
        HashMap<String, String> constants = new HashMap<String, String>();
        if(in.getParamLists().containsKey(REPLACEMENTS)) {
            Entry<String, String>[] entries = in.getParamLists().get(REPLACEMENTS);
            for(Entry<String, String> entrie: entries) {
                constants.put(entrie.getKey(), entrie.getValue());
            }
        }
        
        GLSLProgramParser parser = new GLSLProgramParser();
        parser.read(in.getSource());
        ShaderProgram program = parser.getProgram(constants, attribs);
        if(in.getParamLists().containsKey(UNIFORMS)) {
            program.use();
            Entry<String, String>[] entries = in.getParamLists().get(UNIFORMS);
            for(Entry<String, String> entrie: entries) {
                ShaderUtils.setUniformVari(program.getId(), entrie.getKey(), Integer.parseInt(entrie.getValue()));
            }
        }
        return program;
    }
}
