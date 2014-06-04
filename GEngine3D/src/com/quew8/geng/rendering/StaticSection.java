package com.quew8.geng.rendering;

import com.quew8.geng.rendering.modes.StaticRenderMode;
import com.quew8.geng.geometry.Image;
import com.quew8.gutils.debug.DebugLogger;

/**
 *
 * @author Quew8
 */
class StaticSection extends AbstractSection<StaticHandleList.StaticHandle, StaticRenderMode> {

    public StaticSection(StaticRenderMode instanceRenderMode, Image image, StaticHandleList.StaticHandle[] handles) {
        super(instanceRenderMode, image, handles);
    }

    @Override
    public void sectionDraw() {
        int i = 0;
        sectionBind();
        instanceRenderMode.onPreRendering();
        RenderState.setRenderMode(instanceRenderMode);
        while (i < handles.length) {
            int start = handles[i].getStart();
            if (handles[i].shouldDraw()) {
                do {
                    handles[i].drawn();
                    i++;
                } while (i < handles.length && handles[i].shouldDraw());
                DebugLogger.d("Rendering", "Drawing Elements: " + start + " " + handles[i - 1].getEnd());
                outer.drawElements(start, handles[i - 1].getEnd());
            }
        }
        instanceRenderMode.onPostRendering();
    }
    
}
