package com.quew8.geng.rendering;

import com.quew8.geng.rendering.modes.DynamicRenderMode;
import com.quew8.geng.rendering.modes.StaticRenderMode;
import com.quew8.gmath.Matrix;
import com.quew8.gutils.BufferUtils;
import static com.quew8.gutils.opengl.OpenGL.GL_ARRAY_BUFFER;
import static com.quew8.gutils.opengl.OpenGL.GL_ELEMENT_ARRAY_BUFFER;
import com.quew8.gutils.opengl.VertexBuffer;
import java.nio.FloatBuffer;

/**
 *
 * @author Quew8
 */
public class RenderState {
    private static final FloatBuffer matrixFB;
    private static final FloatBuffer idMatrixFB;
    private static final FloatBuffer modelMatrixFB;
    private static final FloatBuffer projectionMatrixFB;
    private static boolean immediateMode = false;
    private static StaticRenderMode currentRenderMode = new StaticDoNothingRenderMode();
    private static DynamicRenderMode<?> currentDynamicRenderMode = new DynamicDoNothingRenderMode();
    
    static {
        idMatrixFB = BufferUtils.createFloatBuffer(16);
        new Matrix().putIn(idMatrixFB);
        matrixFB = BufferUtils.createFloatBuffer(16);
        modelMatrixFB = BufferUtils.createFloatBuffer(16);
        projectionMatrixFB = BufferUtils.createFloatBuffer(16);
    }

    private RenderState() {
        
    }
    
    public static void setRenderMode(boolean immediateMode, StaticRenderMode renderMode) {
        if(immediateMode && !RenderState.immediateMode) {
            VertexBuffer.unbind(GL_ARRAY_BUFFER);
        }
        RenderState.immediateMode = immediateMode;
        setCurrentStaticRenderMode(renderMode);
        currentRenderMode.updateProjectionMatrix(projectionMatrixFB);
    }

    public static void setRenderMode(DynamicRenderMode<?> renderMode) {
        RenderState.immediateMode = false;
        setCurrentDynamicRenderMode(renderMode);
        currentRenderMode.updateProjectionMatrix(projectionMatrixFB);
    }
    
    public static void setModelMatrixIdentity() {
        currentDynamicRenderMode.updateModelMatrix(idMatrixFB);
    }
    
    public static void setModelMatrix(Matrix matrix) {
        matrix.putIn(modelMatrixFB);
        currentDynamicRenderMode.updateModelMatrix(modelMatrixFB);
    }
    
    public static FloatBuffer getIdentityMatrix() {
        return idMatrixFB;
    }
    
    public static FloatBuffer getMatrix() {
        return matrixFB;
    }
    
    public static void setNextProjectionMatrix(Matrix matrix) {
        matrix.putIn(projectionMatrixFB);
    }
    
    public static void setProjectionMatrix(Matrix matrix) {
        setNextProjectionMatrix(matrix);
        currentRenderMode.updateProjectionMatrix(projectionMatrixFB);
    }
    
    private static void setCurrentStaticRenderMode(StaticRenderMode renderMode) {
        RenderState.currentRenderMode.onMadeNonCurrent();
        RenderState.currentRenderMode = renderMode;
        RenderState.currentRenderMode.onMadeCurrent();
    }
    
    private static void setCurrentDynamicRenderMode(DynamicRenderMode<?> renderMode) {
        RenderState.currentRenderMode.onMadeNonCurrent();
        RenderState.currentRenderMode = renderMode;
        RenderState.currentDynamicRenderMode = renderMode;
        RenderState.currentRenderMode.onMadeCurrent();
    }
    
    private static class StaticDoNothingRenderMode extends StaticRenderMode {
        @Override
        public void updateProjectionMatrix(FloatBuffer matrix) {}
    }
    
    private static class DynamicDoNothingRenderMode extends DynamicRenderMode<Object> {
        @Override
        public void updateProjectionMatrix(FloatBuffer matrix) {}
        @Override
        public void updateModelMatrix(FloatBuffer matrix) {}
    }
 }
