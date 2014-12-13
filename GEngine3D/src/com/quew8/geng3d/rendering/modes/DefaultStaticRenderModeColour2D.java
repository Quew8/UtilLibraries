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
public class DefaultStaticRenderModeColour2D extends StaticRenderMode {
    public final ShaderProgram shader;
    
    public DefaultStaticRenderModeColour2D() {
        super(2);
        shader = new ShaderProgram(
            "uniform mat4 projectionMatrix;"
                    + "attribute vec2 position;"
                    + "attribute vec3 colour;"
                    + "varying vec3 fInColour;"
                    + "void main(void) {"
                    + "    fInColour = colour;"
                    + "    gl_Position = projectionMatrix * vec4(position.xy, 0, 1);"
                    + "}",
            "varying vec3 fInColour;"
                    + "void main(void) {"
                    + "    gl_FragColor = vec4(1, 1, 1, 1);/*vec4(fInColour.rgb, 1);*/"
                    + "}",
            "position",
            "colour"
            );
    }
    
    @Override
    public void onMadeCurrent() {
        shader.use();
    }

    @Override
    public void onPreRendering(VertexData vd) {
         vd.vertexAttribPointer(0, 2, GL_FLOAT, false, 20, 0);
         vd.vertexAttribPointer(1, 3, GL_FLOAT, false, 20, 8);
    }
    
    @Override
    public void updateProjectionMatrix(FloatBuffer matrix) {
         ShaderUtils.setUniformMatrix(shader.getId(), "projectionMatrix", matrix);
    }

    @Override
    public void onMadeNonCurrent() {
        ShaderUtils.useFixedFunctions();
    }
    
}
