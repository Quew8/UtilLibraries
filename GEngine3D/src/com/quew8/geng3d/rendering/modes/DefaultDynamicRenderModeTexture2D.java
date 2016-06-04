package com.quew8.geng3d.rendering.modes;

import com.quew8.geng.rendering.modes.DynamicRenderMode;
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
public class DefaultDynamicRenderModeTexture2D extends DynamicRenderMode<Void> {
    public static final DefaultDynamicRenderModeTexture2D INSTANCE = new DefaultDynamicRenderModeTexture2D();
    private static final String VERT_LOC = "com/quew8/geng3d/rendering/modes/tex_2d.vert",
            FRAG_LOC = "com/quew8/geng3d/rendering/modes/tex.frag";
    public final ShaderProgram shader;

    private static final int STRIDE = 16;

    public DefaultDynamicRenderModeTexture2D() {
        super(2, STRIDE);
        shader = new ShaderProgram(
                GeneralUtils.readTextIntoDefaultFormatter(GeneralUtils.readFrom(VERT_LOC)).getText(),
                GeneralUtils.readTextIntoDefaultFormatter(GeneralUtils.readFrom(FRAG_LOC)).getText()
        );
        //ShaderUtils.setUniformVari(shader.getId(), "texture", 0);
    }

    @Override
    public void onMadeCurrent() {
        shader.use();
    }

    @Override
    public void onPreRendering(VertexBufferObject vd) {
        vd.vertexAttribPointer(0, 2, GL_FLOAT, false, STRIDE, 0);
        vd.vertexAttribPointer(1, 2, GL_FLOAT, false, STRIDE, 8);
    }

    @Override
    public void updateProjectionMatrix(FloatBuffer matrix) {
        ShaderUtils.setUniformMatrix(shader.getId(), "uProjectionMatrix", matrix);
    }

    @Override
    public void updateModelMatrix(FloatBuffer matrix) {
        ShaderUtils.setUniformMatrix(shader.getId(), "uModelMatrix", matrix);
    }

    @Override
    public void onMadeNonCurrent() {
        ShaderUtils.useFixedFunctions();
    }

}
