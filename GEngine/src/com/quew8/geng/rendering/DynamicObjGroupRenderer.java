package com.quew8.geng.rendering;

import com.quew8.geng.rendering.modes.DynamicRenderMode;
import com.quew8.gmath.Matrix;
import com.quew8.gutils.opengl.VertexBufferObject;
import java.nio.ByteBuffer;

/**
 *
 * @author Quew8
 * @param <T>
 */
public class DynamicObjGroupRenderer<T> extends ObjGroupRenderer<DynamicHandleList<T>.DynamicHandle, DynamicRenderMode<T>> {
    
    DynamicObjGroupRenderer(ByteBuffer data, int[][] indices, 
            ObjGroup<DynamicHandleList<T>.DynamicHandle, 
                    DynamicRenderMode<T>>[] groups) {
        
        super(data, indices, groups);
    }
    
    @Override
    public void drawGroup(ObjGroup<DynamicHandleList<T>.DynamicHandle, DynamicRenderMode<T>> group,
            VertexBufferObject vbo) {
        
        RenderState.setDynamicRenderMode(group.getRenderMode());
        group.getRenderMode().bindGeneralTexture(group.getTexture());
        for(int i = 0; i < group.getNHandles(); i++) {
            while(group.getHandle(i).hasNext()) {
                if(group.getHandle(i).shouldDrawNext()) {
                    Matrix m = group.getHandle(i).getNextMatrixForDraw();
                    RenderState.setModelMatrix(m);
                    group.getRenderMode().onPreDraw(group.getHandle(i).getNextData());
                    drawElements(group.getHandle(i).getStart(), group.getHandle(i).getEnd());
                    group.getRenderMode().onPostDraw(group.getHandle(i).getNextData());
                }
            }
        }
    }
    
    public static class Factory<T> extends AbstractFactory<DynamicObjGroupRenderer, 
            DynamicHandleList<T>.DynamicHandle, DynamicRenderMode<T>, DynamicHandleList<T>> {
        
        @Override
        public DynamicObjGroupRenderer construct(ByteBuffer bb, int[][] indices, 
                ObjGroup<DynamicHandleList<T>.DynamicHandle, DynamicRenderMode<T>>[] groups) {
            
            return new DynamicObjGroupRenderer(bb, indices, groups);
        }

        @Override
        public DynamicHandleList<T>.DynamicHandle getHandle(DynamicHandleList<T> list, 
                int start, int end) {
            
            return list.new DynamicHandle(start, end);
        }
    }
}
