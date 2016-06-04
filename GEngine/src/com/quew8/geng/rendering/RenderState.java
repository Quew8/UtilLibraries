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
    private static RenderState CURRENT_RENDER_STATE = new RenderState();
    private final FloatBuffer matrixFB;
    private final FloatBuffer idMatrixFB;
    private final FloatBuffer modelMatrixFB;
    private final FloatBuffer projectionMatrixFB;
    private StaticRenderMode currentRenderMode = new StaticDoNothingRenderMode();
    private DynamicRenderMode<?> currentDynamicRenderMode = new DynamicDoNothingRenderMode();

    public RenderState() {
        idMatrixFB = BufferUtils.createFloatBuffer(16);
        idMatrixFB.put(new float[] {
            1, 0, 0, 0,
            0, 1, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1
        });
        idMatrixFB.flip();
        matrixFB = BufferUtils.createFloatBuffer(16);
        modelMatrixFB = BufferUtils.createFloatBuffer(16);
        projectionMatrixFB = BufferUtils.createFloatBuffer(16);
        projectionMatrixFB.put(new float[] {
            1, 0, 0, 0,
            0, 1, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1
        });
        projectionMatrixFB.flip();
    }
    
    public void makeCurrent() {
        RenderState.CURRENT_RENDER_STATE = this;
    }
    
    private void instanceSetStaticRenderMode(StaticRenderMode renderMode) {
        setCurrentStaticRenderMode(renderMode);
        currentRenderMode.updateProjectionMatrix(projectionMatrixFB);
    }

    private void instanceSetDynamicRenderMode(DynamicRenderMode<?> renderMode) {
        setCurrentDynamicRenderMode(renderMode);
        currentRenderMode.updateProjectionMatrix(projectionMatrixFB);
    }
    
    private void instanceSetModelMatrixIdentity() {
        currentDynamicRenderMode.updateModelMatrix(idMatrixFB);
    }
    
    private void instanceSetModelMatrix(Matrix matrix) {
        modelMatrixFB.put(matrix.getData());
        modelMatrixFB.flip();
        currentDynamicRenderMode.updateModelMatrix(modelMatrixFB);
    }
    
    public FloatBuffer getIdentityMatrix() {
        return idMatrixFB;
    }
    
    public FloatBuffer getMatrix() {
        return matrixFB;
    }
    
    private void instanceSetNextProjectionMatrix(Matrix matrix) {
        projectionMatrixFB.put(matrix.getData());
        projectionMatrixFB.flip();
    }
    
    private void instanceSetProjectionMatrix(Matrix matrix) {
        instanceSetNextProjectionMatrix(matrix);
        currentRenderMode.updateProjectionMatrix(projectionMatrixFB);
    }
    
    private void setCurrentStaticRenderMode(StaticRenderMode renderMode) {
        this.currentRenderMode.onMadeNonCurrent();
        this.currentRenderMode = renderMode;
        this.currentDynamicRenderMode = null;
        this.currentRenderMode.onMadeCurrentStatic();
    }
    
    private void setCurrentDynamicRenderMode(DynamicRenderMode<?> renderMode) {
        this.currentRenderMode.onMadeNonCurrent();
        this.currentRenderMode = renderMode;
        this.currentDynamicRenderMode = renderMode;
        this.currentDynamicRenderMode.onMadeCurrent();
    }
    
    public static void setStaticRenderMode(StaticRenderMode renderMode) {
        CURRENT_RENDER_STATE.instanceSetStaticRenderMode(renderMode);
    }

    public static void setDynamicRenderMode(DynamicRenderMode<?> renderMode) {
        CURRENT_RENDER_STATE.instanceSetDynamicRenderMode(renderMode);
    }
    
    public static void setModelMatrixIdentity() {
        CURRENT_RENDER_STATE.instanceSetModelMatrixIdentity();
    }
    
    public static void setModelMatrix(Matrix matrix) {
        CURRENT_RENDER_STATE.instanceSetModelMatrix(matrix);
    }
    
    public static void setNextProjectionMatrix(Matrix matrix) {
        CURRENT_RENDER_STATE.instanceSetNextProjectionMatrix(matrix);
    }
    
    public static void setProjectionMatrix(Matrix matrix) {
        CURRENT_RENDER_STATE.instanceSetProjectionMatrix(matrix);
    }
    
    private static class StaticDoNothingRenderMode extends StaticRenderMode {
        StaticDoNothingRenderMode() {
            super(0, 0);
        }
        
        @Override
        public void updateProjectionMatrix(FloatBuffer matrix) {}
    }
    
    private static class DynamicDoNothingRenderMode extends DynamicRenderMode<Object> {
        DynamicDoNothingRenderMode() {
            super(0, 0);
        }
        
        @Override
        public void updateProjectionMatrix(FloatBuffer matrix) {}
        @Override
        public void updateModelMatrix(FloatBuffer matrix) {}
    }
 }
