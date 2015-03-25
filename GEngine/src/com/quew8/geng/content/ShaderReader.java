package com.quew8.geng.content;

import com.quew8.geng.glslparser.constructor.GLSLProgramParser;
import com.quew8.gutils.FileUtils;
import com.quew8.gutils.GeneralUtils;
import com.quew8.gutils.content.ContentReader;
import com.quew8.gutils.content.Source;
import com.quew8.gutils.opengl.OpenGL;
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
            TYPE = "type",
            SOURCE_FILE_TYPE = "source_files",
            GEN_FILE_TYPE = "gen_file",
            ATRIBS = "attribs",
            REPLACEMENTS = "replacements",
            UNIFORMS = "uniforms";
            
    @Override
    public ShaderProgram read(Source in) {
        String type;
        if(in.hasParam(TYPE)) {
            type = in.getParam(TYPE);
        } else {
            String extension = FileUtils.getExtension(in.getSource(0));
            switch(extension) {
                case "program": type = GEN_FILE_TYPE; break;
                case "txt":
                case "frag":
                case "vert": type = SOURCE_FILE_TYPE; break;
                default: throw new IllegalArgumentException("Unrecognized shader program file extension: \"" + extension + "\"");
            }
        }
        String[] attribs = in.getParams().get(ATRIBS).split("[\\s]+");
        HashMap<String, String> constants = new HashMap<String, String>();
        if(in.getParamLists().containsKey(REPLACEMENTS)) {
            Entry<String, String>[] entries = in.getParamLists().get(REPLACEMENTS);
            for(Entry<String, String> entrie: entries) {
                constants.put(entrie.getKey(), entrie.getValue());
            }
        }
        
        ShaderProgram program;
        switch(type) {
            case GEN_FILE_TYPE: {
                GLSLProgramParser parser = new GLSLProgramParser();
                parser.read(in.getSource());
                program = parser.getProgram(100, OpenGL.getGLSLVersion(), constants, attribs);
                break;
            }
            case SOURCE_FILE_TYPE: {
                String vertex = GeneralUtils.readTextIntoDefaultFormatter(in.getStream(0)).getText();
                String fragment = GeneralUtils.readTextIntoDefaultFormatter(in.getStream(1)).getText();
                program = new ShaderProgram(vertex, fragment, attribs);
                break;
            }
            default: throw new IllegalArgumentException("Unrecognized type to read shader program: \"" + type + "\"");
        }
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
