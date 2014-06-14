package com.quew8.geng.rendering;

import com.quew8.geng.rendering.modes.ParticleRenderMode;
import java.util.ArrayList;

/**
 *
 * @author Quew8
 */
public class ParticleEmitterGroup {
    private final ArrayList<Section> sections = new ArrayList<Section>();
    
    public void draw() {
        for(int i = 0; i < sections.size(); i++) {
            sections.get(i).sectionDraw();
        }
    }
    
    public void update() {
        for(int i = 0; i < sections.size(); i++) {
            sections.get(i).sectionUpdate();
        }
    }
    
    public int newSection(ParticleRenderMode renderMode, int size) {
        int i = sections.size();
        sections.add(new Section(renderMode, size));
        return i;
    }
    
    public Section get(int index) {
        return sections.get(index);
    }
    
    public int getIndexOfSize(int size) {
        for(int i = 0; i < sections.size(); i++) {
            if(sections.get(i).size == size) {
                return i;
            }
        }
        return -1;
    } 
    
    /**
     * 
     */
    public class Section {
        private final ArrayList<ParticleEmitter<?, ?, ?>> emitters = new ArrayList<ParticleEmitter<?, ?, ?>>();
        private final ParticleRenderMode renderMode;
        private final int size;
        
        public Section(ParticleRenderMode renderMode, int size) {
            this.size = size;
            this.renderMode = renderMode;
        }
        
        public void sectionDraw() {
            renderMode.onPreRendering(null);
            RenderState.setRenderMode(renderMode);
            for(int i = 0; i < emitters.size(); i++) {
                emitters.get(i).draw(size);
            }
            renderMode.onPostRendering();
        }
        
        public void sectionUpdate() {
            for(int i = 0; i < emitters.size(); i++) {
                emitters.get(i).update();
            }
        }
        
        public void add(ParticleEmitter<?, ?, ?> emitter) {
            emitters.add(emitter);
        }
        
        public void remove(ParticleEmitter<?, ?, ?> emitter) {
            emitters.remove(emitter);
        }
    }
}
