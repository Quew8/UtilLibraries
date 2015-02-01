package com.quew8.geng.rendering;

import com.quew8.geng.rendering.modes.DynamicRenderMode;
import com.quew8.geng.rendering.modes.StaticRenderMode;
import com.quew8.gmath.Matrix;
import com.quew8.gutils.ArrayUtils;
import com.quew8.gutils.BufferUtils;
import static com.quew8.gutils.opengl.OpenGL.GL_ARRAY_BUFFER;
import com.quew8.gutils.opengl.VertexBuffer;
import com.quew8.gutils.opengl.shaders.ShaderUtils;
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
    private boolean immediateMode = false;
    private StaticRenderMode currentRenderMode = new StaticDoNothingRenderMode();
    private DynamicRenderMode<?> currentDynamicRenderMode = new DynamicDoNothingRenderMode();

    public RenderState() {
        idMatrixFB = BufferUtils.createFloatBuffer(16);
        new Matrix().putIn(idMatrixFB);
        matrixFB = BufferUtils.createFloatBuffer(16);
        modelMatrixFB = BufferUtils.createFloatBuffer(16);
        projectionMatrixFB = BufferUtils.createFloatBuffer(16);
        new Matrix().putIn(projectionMatrixFB);
    }
    
    public void makeCurrent() {
        RenderState.CURRENT_RENDER_STATE = this;
    }
    
    private void instanceSetRenderMode(boolean immediateMode, StaticRenderMode renderMode) {
        if(immediateMode && !this.immediateMode) {
            VertexBuffer.unbind(GL_ARRAY_BUFFER);
        }
        this.immediateMode = immediateMode;
        setCurrentStaticRenderMode(renderMode);
        currentRenderMode.updateProjectionMatrix(projectionMatrixFB);
    }

    private void instanceSetRenderMode(DynamicRenderMode<?> renderMode) {
        this.immediateMode = false;
        setCurrentDynamicRenderMode(renderMode);
        currentRenderMode.updateProjectionMatrix(projectionMatrixFB);
    }
    
    private void instanceSetModelMatrixIdentity() {
        currentDynamicRenderMode.updateModelMatrix(idMatrixFB);
    }
    
    private void instanceSetModelMatrix(Matrix matrix) {
        matrix.putIn(modelMatrixFB);
        currentDynamicRenderMode.updateModelMatrix(modelMatrixFB);
    }
    
    public FloatBuffer getIdentityMatrix() {
        return idMatrixFB;
    }
    
    public FloatBuffer getMatrix() {
        return matrixFB;
    }
    
    private void instanceSetNextProjectionMatrix(Matrix matrix) {
        matrix.putIn(projectionMatrixFB);
    }
    
    private void instanceSetProjectionMatrix(Matrix matrix) {
        instanceSetNextProjectionMatrix(matrix);
        currentRenderMode.updateProjectionMatrix(projectionMatrixFB);
    }
    
    private void setCurrentStaticRenderMode(StaticRenderMode renderMode) {
        this.currentRenderMode.onMadeNonCurrent();
        setCurrentAttribs(renderMode);
        this.currentRenderMode = renderMode;
        this.currentRenderMode.onMadeCurrent();
    }
    
    private void setCurrentDynamicRenderMode(DynamicRenderMode<?> renderMode) {
        this.currentRenderMode.onMadeNonCurrent();
        setCurrentAttribs(renderMode);
        this.currentRenderMode = renderMode;
        this.currentDynamicRenderMode = renderMode;
        this.currentRenderMode.onMadeCurrent();
    }
    
    private void setCurrentAttribs(StaticRenderMode renderMode) {
        if(renderMode.getNAttribs() != this.currentRenderMode.getNAttribs()) {
            if(renderMode.getNAttribs() < this.currentRenderMode.getNAttribs()) {
                ShaderUtils.setAttribIndicesEnabled(
                        false, 
                        ArrayUtils.list(
                                renderMode.getNAttribs(),
                                this.currentRenderMode.getNAttribs(),
                                1
                        )
                );
            } else {
                ShaderUtils.setAttribIndicesEnabled(
                        true, 
                        ArrayUtils.list(
                                this.currentRenderMode.getNAttribs(),
                                renderMode.getNAttribs(),
                                1
                        )
                );
            }
        } 
    }
    
    public static void setRenderMode(boolean immediateMode, StaticRenderMode renderMode) {
        CURRENT_RENDER_STATE.instanceSetRenderMode(immediateMode, renderMode);
    }

    public static void setRenderMode(DynamicRenderMode<?> renderMode) {
        CURRENT_RENDER_STATE.instanceSetRenderMode(renderMode);
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
            super(0);
        }
        
        @Override
        public void updateProjectionMatrix(FloatBuffer matrix) {}
    }
    
    private static class DynamicDoNothingRenderMode extends DynamicRenderMode<Object> {
        DynamicDoNothingRenderMode() {
            super(0);
        }
        
        @Override
        public void updateProjectionMatrix(FloatBuffer matrix) {}
        @Override
        public void updateModelMatrix(FloatBuffer matrix) {}
    }
 }
