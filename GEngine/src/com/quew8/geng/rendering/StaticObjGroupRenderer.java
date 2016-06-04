package com.quew8.geng.rendering;

import com.quew8.geng.rendering.StaticHandleList.StaticHandle;
import com.quew8.geng.rendering.modes.StaticRenderMode;
import com.quew8.gutils.opengl.VertexBufferObject;
import java.nio.ByteBuffer;

/**
 *
 * @author Quew8
 */
public class StaticObjGroupRenderer extends ObjGroupRenderer<StaticHandle, StaticRenderMode> {
    
    StaticObjGroupRenderer(ByteBuffer data, int[][] indices, 
            ObjGroup<StaticHandle, StaticRenderMode>[] groups) {
        
        super(data, indices, groups);
    }
    
    @Override
    public void drawGroup(ObjGroup<StaticHandle, StaticRenderMode> group,
            VertexBufferObject vbo) {
        
        RenderState.setStaticRenderMode(group.getRenderMode());
        int i = 0;
        group.getTexture().bind();
        while(i < group.getNHandles()) {
            int start = group.getHandle(i).getStart();
            if(group.getHandle(i).shouldDraw()) {
                do {
                    group.getHandle(i).drawn();
                    i++;
                } while (i < group.getNHandles() && group.getHandle(i).shouldDraw());
                drawElements(group.getOffset() + start, group.getOffset() + group.getHandle(i - 1).getEnd());
            }
        }
    }
    
    public static class Factory extends AbstractFactory<StaticObjGroupRenderer, 
            StaticHandleList.StaticHandle, StaticRenderMode, StaticHandleList> {
        
        @Override
        public StaticObjGroupRenderer construct(ByteBuffer bb, int[][] indices, 
                ObjGroup<StaticHandle, StaticRenderMode>[] groups) {
            
            return new StaticObjGroupRenderer(bb, indices, groups);
        }

        @Override
        public StaticHandle getHandle(StaticHandleList list, int start, int end) {
            return list.new StaticHandle(start, end);
        }
        
    }
}
