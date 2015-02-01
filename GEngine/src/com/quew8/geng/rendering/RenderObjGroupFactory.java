package com.quew8.geng.rendering;

import com.quew8.geng.rendering.modes.DynamicRenderMode;
import com.quew8.geng.rendering.modes.GeometricDataInterpreter;
import com.quew8.geng.rendering.modes.DynamicTextureRenderMode;
import com.quew8.geng.rendering.modes.StaticRenderMode;
import com.quew8.geng.rendering.modes.TextureFetchable;
import com.quew8.geng.geometry.Texture;
import com.quew8.gutils.ArrayUtils;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 
 * @author Quew8
 * @param <T> 
 * @param <S> 
 */
public class RenderObjGroupFactory<T, S> {
    private final GeometricDataInterpreter<T, S> interpreter;
    private final ArrayList<T> staticMeshes = new ArrayList<T>();
    private final ArrayList<T> dynamicMeshes = new ArrayList<T>();
    private boolean canAddStatics = true;
    private final ArrayList<RenderObjSectionFactory<?>> sectionFactories = new ArrayList<RenderObjSectionFactory<?>>();

    /**
     * 
     * @param interpreter
     */
    public RenderObjGroupFactory(GeometricDataInterpreter<T, S> interpreter) {
        this.interpreter = interpreter;
    }

    public RenderObjSectionFactory<?> getSectionFactory(int index) {
        return sectionFactories.get(index);
    }
    
    /**
     * 
     * @return 
     */
    public RenderObjGroup<T> construct() {
        T[][] ta = ArrayUtils.createArray(2);
        ta[0] = staticMeshes.toArray((ta[0] = ArrayUtils.createArray(staticMeshes.size())));
        ta[1] = dynamicMeshes.toArray((ta[1] = ArrayUtils.createArray(dynamicMeshes.size())));
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
    
    public <E> RenderObjGroupFactory<T, S>.RenderObjSectionFactory<E> createDynamicSection(DynamicRenderMode<E> instanceMode, Texture image) {
        RenderObjSectionFactory<E> sf = new DynamicSectionFactory<E>(image, instanceMode);
        sectionFactories.add(sf);
        return sf;
    }
    
    public <E extends TextureFetchable> RenderObjGroupFactory<T, S>.RenderObjSectionFactory<E> createDynamicImageSection(DynamicRenderMode<E> instanceMode) {
        return createDynamicSection(new DynamicTextureRenderMode<E>(instanceMode), null);
    }

    public abstract class RenderObjSectionFactory<E> {
        protected final Texture tex;
        
        public RenderObjSectionFactory(Texture tex) {
            this.tex = tex;
        }
        
        public S[][] addSeperate(StaticHandleList shl, T... objs) {
            throw new IllegalStateException("Adding Static Handles to Non Static Section");
        }
        
        public S[] add(StaticHandleList shl, T... objs) {
            return ArrayUtils.concatArrays(addSeperate(shl, objs));
        }
        
        public S[][] addSeperateDynamic(DynamicHandleList<? extends E> dhl, T... objs) {
            throw new IllegalStateException("Adding Dynamic Handles to Non Dynamic Section");
        }
        
        public S[] addDynamic(DynamicHandleList<? extends E> dhl, T... objs) {
            return ArrayUtils.concatArrays(addSeperateDynamic(dhl, objs));
        }
        
        abstract RenderObjectGroupSection<?, ?> contructSection();
        
        public Texture getTexture() {
            return tex;
        }
    }
    
    private class StaticSectionFactory extends RenderObjSectionFactory<Void> {
        private final StaticRenderMode staticInstanceMode;
        private final ArrayList<StaticHandleList.StaticHandle> staticHandles = new ArrayList<StaticHandleList.StaticHandle>();

        private StaticSectionFactory(Texture image, StaticRenderMode staticInstanceMode) {
            super(image);
            this.staticInstanceMode = staticInstanceMode;
        }
        
        @Override
        public S[][] addSeperate(StaticHandleList shl, T... objs) {
            if(objs.length == 0) {
                throw new IllegalArgumentException("Cannot add 0 objects");
            }
            if (canAddStatics) {
                int start = staticMeshes.size();
                staticMeshes.addAll(Arrays.asList(objs));
                StaticHandleList.StaticHandle h = shl.new StaticHandle(start, staticMeshes.size());
                staticHandles.add(h);
                S[][] array = ArrayUtils.<S[]>createArray(objs.length);
                for(int i = 0; i < objs.length; i++) {
                    array[i] = interpreter.toPositions(objs[i]);
                }
                return array;
            } else {
                throw new IllegalStateException("Cannot Add More Static Meshes");
            }
        }
        
        @Override
        RenderObjectGroupSection<?, ?> contructSection() {
            return new StaticRenderObjGroupSection(staticInstanceMode, tex, staticHandles.toArray(new StaticHandleList.StaticHandle[staticHandles.size()]));
        }
    }
    
    private class DynamicSectionFactory<E> extends RenderObjSectionFactory<E> {
        private final DynamicRenderMode<E> instanceMode;
        private final ArrayList<DynamicHandleList<? extends E>.DynamicHandle> dynamicHandles = new ArrayList<DynamicHandleList<? extends E>.DynamicHandle>();

        private DynamicSectionFactory(Texture image, DynamicRenderMode<E> instanceMode) {
            super(image);
            this.instanceMode = instanceMode;
        }
        
        @Override
        public S[][] addSeperateDynamic(DynamicHandleList<? extends E> dhl, T... objs) {
            if(objs.length == 0) {
                throw new IllegalArgumentException("Cannot add 0 objects");
            }
            canAddStatics = false;
            int start = dynamicMeshes.size() + staticMeshes.size();
            dynamicMeshes.addAll(Arrays.asList(objs));
            DynamicHandleList<? extends E>.DynamicHandle h = dhl.new DynamicHandle(start, dynamicMeshes.size() + staticMeshes.size());
            dynamicHandles.add(h);
            S[][] array = ArrayUtils.<S[]>createArray(objs.length);
            for(int i = 0; i < objs.length; i++) {
                array[i] = interpreter.toPositions(objs[i]);
            }
            return array;
        }
        
        @Override
        RenderObjectGroupSection<?, ?> contructSection() {
            return new DynamicRenderObjSection<E>(instanceMode, tex, 
                    dynamicHandles.toArray(new DynamicHandleList.DynamicHandle[dynamicHandles.size()]));
        }
        
    }
}
