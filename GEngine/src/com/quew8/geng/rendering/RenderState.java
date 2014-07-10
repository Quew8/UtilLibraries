package com.quew8.geng.rendering;

import com.quew8.geng.rendering.modes.DynamicRenderMode;
import com.quew8.geng.rendering.modes.StaticRenderMode;
import com.quew8.gmath.Matrix;
import com.quew8.gutils.BufferUtils;
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
    
    public static void setRenderMode(StaticRenderMode renderMode) {
        setCurrentStaticRenderMode(renderMode);
        currentRenderMode.updateProjectionMatrix(projectionMatrixFB);
    }

    public static void setRenderMode(DynamicRenderMode<?> renderMode) {
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
