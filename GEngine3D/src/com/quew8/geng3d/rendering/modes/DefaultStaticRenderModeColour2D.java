package com.quew8.geng3d.rendering.modes;

import com.quew8.geng.rendering.modes.StaticRenderMode;
import com.quew8.gutils.GeneralUtils;
import static com.quew8.gutils.opengl.OpenGL.GL_FLOAT;
import com.quew8.gutils.opengl.VertexBufferObject;
import com.quew8.gutils.opengl.shaders.ShaderProgram;
import com.quew8.gutils.opengl.shaders.ShaderUtils;
import java.nio.FloatBuffer;

/**
 *
 * @author Quew8
 */
public class DefaultStaticRenderModeColour2D extends StaticRenderMode {
    public static final DefaultStaticRenderModeColour2D INSTANCE = new DefaultStaticRenderModeColour2D();
    private static final String VERT_LOC = "com/quew8/geng3d/rendering/modes/static_colour_2d.vert",
            FRAG_LOC = "com/quew8/geng3d/rendering/modes/colour.frag";
    public final ShaderProgram shader;
    
    private static final int STRIDE = 24;
    
    public DefaultStaticRenderModeColour2D() {
        super(2, STRIDE);
        shader = new ShaderProgram(
                GeneralUtils.readTextIntoDefaultFormatter(GeneralUtils.readFrom(VERT_LOC)).getText(),
                GeneralUtils.readTextIntoDefaultFormatter(GeneralUtils.readFrom(FRAG_LOC)).getText()
        );
    }
    
    @Override
    public void onMadeCurrentStatic() {
        shader.use();
    }

    @Override
    public void onPreRendering(VertexBufferObject vd) {
        vd.vertexAttribPointer(0, 2, GL_FLOAT, false, STRIDE, 0);
        vd.vertexAttribPointer(1, 4, GL_FLOAT, false, STRIDE, 8);
    }
    
    @Override
    public void updateProjectionMatrix(FloatBuffer matrix) {
         ShaderUtils.setUniformMatrix(shader.getId(), "uProjectionMatrix", matrix);
    }

    @Override
    public void onMadeNonCurrent() {
        ShaderUtils.useFixedFunctions();
    }
    
}
