package com.quew8.geng.rendering;

import com.quew8.geng.rendering.modes.DynamicRenderMode;
import com.quew8.geng.rendering.modes.GeometricDataInterpreter;
import com.quew8.geng.rendering.modes.DynamicImageRenderMode;
import com.quew8.geng.rendering.modes.StaticRenderMode;
import com.quew8.geng.rendering.modes.ImageFetchable;
import com.quew8.geng.geometry.Image;
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
public class MeshGroupFactory<T> {
    private final GeometricDataInterpreter<T> interpreter;
    private final Class<T> clazz;
    private final ArrayList<T> staticMeshes = new ArrayList<T>();
    private final ArrayList<T> dynamicMeshes = new ArrayList<T>();
    private boolean canAddStatics = true;
    private final ArrayList<SectionFactory<?>> sectionFactories = new ArrayList<SectionFactory<?>>();

    /**
     * 
     * @param interpreter
     * @param clazz 
     */
    public MeshGroupFactory(GeometricDataInterpreter<T> interpreter, Class<T> clazz) {
        this.interpreter = interpreter;
        this.clazz = clazz;
    }

    /**
     * 
     * @return 
     */
    @SuppressWarnings(value="unchecked")
    public MeshGroup<T> construct() {
        T[][] ta = (T[][]) Array.newInstance(clazz, 2, 0);
        ta[0] = staticMeshes.toArray(ta[0]);
        ta[1] = dynamicMeshes.toArray(ta[1]);
        AbstractSection<?, ?>[] sections = new AbstractSection<?, ?>[sectionFactories.size()];
        for (int i = 0; i < sectionFactories.size(); i++) {
            sections[i] = sectionFactories.get(i).contructSection();
        }
        return new MeshGroup<T>(ArrayUtils.concatVariableLengthArrays(ta), interpreter, sections);
    }
    
    public SectionFactory<Void> createStaticSection(StaticRenderMode instanceMode, Image image) {
        StaticSectionFactory sf = new StaticSectionFactory(image, instanceMode);
        sectionFactories.add(sf);
        return sf;
    }
    
    public <S> MeshGroupFactory<T>.SectionFactory<S> createDynamicSection(DynamicRenderMode<S> instanceMode, Image image) {
        SectionFactory<S> sf = new DynamicSectionFactory<S>(image, instanceMode);
        sectionFactories.add(sf);
        return sf;
    }
    
    public <S extends ImageFetchable> MeshGroupFactory<T>.SectionFactory<S> createDynamicImageSection(DynamicRenderMode<S> instanceMode) {
        return createDynamicSection(new DynamicImageRenderMode<S>(instanceMode), null);
    }

    public abstract class SectionFactory<S> {
        protected final Image image;
        
        public SectionFactory(Image image) {
            this.image = image;
        }
        
        /**
         * 
         * @param shl
         * @param ms
         * @return 
         */
        public Vector[] add(StaticHandleList shl, T... ms) {
            throw new IllegalStateException("Adding Static Handles to Non Static Section");
        }
        
        /**
         * 
         * @param dhl
         * @param ms
         * @return 
         */
        public Vector[] addDynamic(DynamicHandleList<? extends S> dhl, T... ms) {
            throw new IllegalStateException("Adding Dynamic Handles to Non Dynamic Section");
        }
        
        abstract AbstractSection<?, ?> contructSection();
        
        public Image getImage() {
            return image;
        }
    }
    
    public class StaticSectionFactory extends SectionFactory<Void> {
        private final StaticRenderMode staticInstanceMode;
        private final ArrayList<StaticHandleList.StaticHandle> staticHandles = new ArrayList<StaticHandleList.StaticHandle>();

        public StaticSectionFactory(Image image, StaticRenderMode staticInstanceMode) {
            super(image);
            this.staticInstanceMode = staticInstanceMode;
        }
        
        @Override
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
        AbstractSection<?, ?> contructSection() {
            return new StaticSection(staticInstanceMode, image, staticHandles.toArray(new StaticHandleList.StaticHandle[staticHandles.size()]));
        }
    }
    
    public class DynamicSectionFactory<S> extends SectionFactory<S> {
        private final DynamicRenderMode<S> instanceMode;
        private final ArrayList<DynamicHandleList<? extends S>.DynamicHandle> dynamicHandles = new ArrayList<DynamicHandleList<? extends S>.DynamicHandle>();

        public DynamicSectionFactory(Image image, DynamicRenderMode<S> instanceMode) {
            super(image);
            this.instanceMode = instanceMode;
        }
        
        @Override
        public Vector[] addDynamic(DynamicHandleList<? extends S> dhl, T... ms) {
            canAddStatics = false;
            int start = dynamicMeshes.size() + staticMeshes.size();
            dynamicMeshes.addAll(Arrays.asList(ms));
            DynamicHandleList<? extends S>.DynamicHandle h = dhl.new DynamicHandle(start, dynamicMeshes.size() + staticMeshes.size());
            dynamicHandles.add(h);
            return interpreter.toPositions(ms);
        }
        
        @Override
        @SuppressWarnings (value="unchecked")
        AbstractSection<?, ?> contructSection() {
            return new DynamicSection<S>(instanceMode, image, 
                    dynamicHandles.toArray(
                    (DynamicHandleList<S>.DynamicHandle[])
                    Array.newInstance(DynamicHandleList.DynamicHandle.class, dynamicHandles.size())));
        }
        
    }
}