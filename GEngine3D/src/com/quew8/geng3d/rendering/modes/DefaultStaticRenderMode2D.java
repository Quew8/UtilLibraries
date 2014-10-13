package com.quew8.geng3d.rendering.modes;

import com.quew8.geng.rendering.modes.StaticRenderMode;
import static com.quew8.gutils.opengl.OpenGL.GL_FLOAT;
import com.quew8.gutils.opengl.VertexData;
import com.quew8.gutils.opengl.shaders.ShaderProgram;
import com.quew8.gutils.opengl.shaders.ShaderUtils;
import java.nio.FloatBuffer;

/**
 *
 * @author Quew8
 */
public class DefaultStaticRenderMode2D extends StaticRenderMode {
    public final ShaderProgram shader = new ShaderProgram(
            "uniform mat4 projectionMatrix;"
                    + "attribute vec2 position;"
                    + "attribute vec2 texCoords;"
                    + "varying vec2 fInTexCoords;"
                    + "void main(void) {"
                    + "    fInTexCoords = texCoords;"
                    + "    gl_Position = projectionMatrix * vec4(position.xy, 0, 1);"
                    + "}",
            "uniform sampler2D texture;"
                    + "varying vec2 fInTexCoords;"
                    + "void main(void) {"
                    + "    fInTexCoords = texCoords;"
                    + "    gl_FragColor = texture2D(texture, fInTexCoords);"
                    + "}",
            "position",
            "texCoords"
            );
    {
        ShaderUtils.setUniformVari(shader.getId(), "texture", 0);
    }
    
    @Override
    public void onMadeCurrent() {
        ShaderUtils.setAttribIndicesEnabled(true, 0, 1);
        ShaderUtils.setAttribIndicesEnabled(false, 2);
        shader.use();
    }

    @Override
    public void onPreRendering(VertexData vd) {
         vd.vertexAttribPointer(0, 2, GL_FLOAT, false, 16, 0);
         vd.vertexAttribPointer(1, 2, GL_FLOAT, false, 16, 8);
    }
    
    @Override
    public void updateProjectionMatrix(FloatBuffer matrix) {
         ShaderUtils.setUniformMatrix(shader.getId(), "projectionMatrix", matrix);
    }

    @Override
    public void onMadeNonCurrent() {
        ShaderUtils.setAttribIndicesEnabled(true, 2);
        ShaderUtils.useFixedFunctions();
    }
    
}
