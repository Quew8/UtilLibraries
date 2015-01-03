package com.quew8.geng.rendering;

import com.quew8.geng.rendering.modes.DynamicRenderMode;
import com.quew8.geng.rendering.modes.interfaces.GeometricDataInterpreter;
import com.quew8.geng.rendering.modes.DynamicTextureRenderMode;
import com.quew8.geng.rendering.modes.StaticRenderMode;
import com.quew8.geng.rendering.modes.interfaces.TextureFetchable;
import com.quew8.geng.geometry.Texture;
import com.quew8.gmath.Vector;
import com.quew8.gutils.ArrayUtils;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 
 * @author Quew8
 * @param <T> 
 */
public class RenderObjGroupFactory<T> {
    private final GeometricDataInterpreter<T> interpreter;
    private final Class<T> clazz;
    private final ArrayList<T> staticMeshes = new ArrayList<T>();
    private final ArrayList<T> dynamicMeshes = new ArrayList<T>();
    private boolean canAddStatics = true;
    private final ArrayList<RenderObjSectionFactory<?>> sectionFactories = new ArrayList<RenderObjSectionFactory<?>>();

    /**
     * 
     * @param interpreter
     * @param clazz 
     */
    public RenderObjGroupFactory(GeometricDataInterpreter<T> interpreter, Class<T> clazz) {
        this.interpreter = interpreter;
        this.clazz = clazz;
    }

    public RenderObjSectionFactory<?> getSectionFactory(int index) {
        return sectionFactories.get(index);
    }
    
    /**
     * 
     * @return 
     */
    @SuppressWarnings(value="unchecked")
    public RenderObjGroup<T> construct() {
        T[][] ta = (T[][]) Array.newInstance(clazz, 2, 0);
        ta[0] = staticMeshes.toArray(ta[0]);
        ta[1] = dynamicMeshes.toArray(ta[1]);
        RenderObjectGroupSection<?, ?>[] sections = new RenderObjectGroupSection<?, ?>[sectionFactories.size()];
        for (int i = 0; i < sectionFactories.size(); i++) {
            sections[i] = sectionFactories.get(i).contructSection();
        }
        return new RenderObjGroup<T>(ArrayUtils.concatVariableLengthArrays(ta), interpreter, sections);
    }
    
    public RenderObjSectionFactory<Void> createStaticSection(StaticRenderMode instanceMode, Texture image) {
        StaticSectionFactory sf = new StaticSectionFactory(image, instanceMode);
        sectionFactories.add(sf);
        return sf;
    }
    
    public <S> RenderObjGroupFactory<T>.RenderObjSectionFactory<S> createDynamicSection(DynamicRenderMode<S> instanceMode, Texture image) {
        RenderObjSectionFactory<S> sf = new DynamicSectionFactory<S>(image, instanceMode);
        sectionFactories.add(sf);
        return sf;
    }
    
    public <S extends TextureFetchable> RenderObjGroupFactory<T>.RenderObjSectionFactory<S> createDynamicImageSection(DynamicRenderMode<S> instanceMode) {
        return createDynamicSection(new DynamicTextureRenderMode<S>(instanceMode), null);
    }

    public abstract class RenderObjSectionFactory<S> {
        protected final Texture tex;
        
        public RenderObjSectionFactory(Texture tex) {
            this.tex = tex;
        }
        
        /**
         * 
         * @param shl
         * @param ms
         * @return 
         */
        @SuppressWarnings("unchecked")
        public Vector[] add(StaticHandleList shl, T... ms) {
            throw new IllegalStateException("Adding Static Handles to Non Static Section");
        }
        
        /**
         * 
         * @param dhl
         * @param ms
         * @return 
         */
        @SuppressWarnings("unchecked")
        public Vector[] addDynamic(DynamicHandleList<? extends S> dhl, T... ms) {
            throw new IllegalStateException("Adding Dynamic Handles to Non Dynamic Section");
        }
        
        abstract RenderObjectGroupSection<?, ?> contructSection();
        
        public Texture getTexture() {
            return tex;
        }
    }
    
    public class StaticSectionFactory extends RenderObjSectionFactory<Void> {
        private final StaticRenderMode staticInstanceMode;
        private final ArrayList<StaticHandleList.StaticHandle> staticHandles = new ArrayList<StaticHandleList.StaticHandle>();

        public StaticSectionFactory(Texture image, StaticRenderMode staticInstanceMode) {
            super(image);
            this.staticInstanceMode = staticInstanceMode;
        }
        
        @Override
        @SuppressWarnings("unchecked")
        public Vector[] add(StaticHandleList shl, T... ms) {
            if (canAddStatics) {
                int start = staticMeshes.size();
                staticMeshes.addAll(Arrays.asList(ms));
                StaticHandleList.StaticHandle h = shl.new StaticHandle(start, staticMeshes.size());
                staticHandles.add(h);
                return interpreter.toPositions(ms);
            } else {
                throw new IllegalStateException("Cannot Add More Static Meshes");
            }
        }
        
        @Override
        RenderObjectGroupSection<?, ?> contructSection() {
            return new StaticRenderObjGroupSection(staticInstanceMode, tex, staticHandles.toArray(new StaticHandleList.StaticHandle[staticHandles.size()]));
        }
    }
    
    public class DynamicSectionFactory<S> extends RenderObjSectionFactory<S> {
        private final DynamicRenderMode<S> instanceMode;
        private final ArrayList<DynamicHandleList<? extends S>.DynamicHandle> dynamicHandles = new ArrayList<DynamicHandleList<? extends S>.DynamicHandle>();

        public DynamicSectionFactory(Texture image, DynamicRenderMode<S> instanceMode) {
            super(image);
            this.instanceMode = instanceMode;
        }
        
        @Override
        @SuppressWarnings("unchecked")
        public Vector[] addDynamic(DynamicHandleList<? extends S> dhl, T... ms) {
            canAddStatics = false;
            int start = dynamicMeshes.size() + staticMeshes.size();
            dynamicMeshes.addAll(Arrays.asList(ms));
            DynamicHandleList<? extends S>.DynamicHandle h = dhl.new DynamicHandle(start, dynamicMeshes.size() + staticMeshes.size());
            dynamicHandles.add(h);
            return interpreter.toPositions(ms);
        }
        
        @Override
        @SuppressWarnings("unchecked")
        RenderObjectGroupSection<?, ?> contructSection() {
            return new DynamicRenderObjSection<S>(instanceMode, tex, 
                    dynamicHandles.toArray(
                    (DynamicHandleList<S>.DynamicHandle[])
                    Array.newInstance(DynamicHandleList.DynamicHandle.class, dynamicHandles.size())));
        }
        
    }
}
