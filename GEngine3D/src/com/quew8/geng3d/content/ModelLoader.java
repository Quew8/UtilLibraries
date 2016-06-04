package com.quew8.geng3d.content;

import com.quew8.geng.geometry.Image;
import com.quew8.geng3d.geometry.Mesh3D;
import com.quew8.geng.geometry.TextureSheet;
import com.quew8.geng.rendering.DynamicHandleList;
import com.quew8.geng.rendering.DynamicObjGroupRenderer;
import com.quew8.geng.rendering.modes.DynamicRenderMode;
import com.quew8.geng.rendering.modes.GeometricDataInterpreter;
import com.quew8.geng3d.geometry.Vertex3D;
import com.quew8.gmath.Matrix;
import com.quew8.gutils.opengl.texture.LoadedImage;
import com.quew8.gutils.opengl.texture.TextureParams;
import com.quew8.gutils.opengl.texture.TextureUtils;
import java.util.ArrayList;

/**
 *
 * @author Quew8
 * @param <T>
 */
public class ModelLoader<T> {
    private final DynamicObjGroupRenderer.Factory<T> factory;
    private final GeometricDataInterpreter<Mesh3D, Vertex3D> interpreter;
    //private RenderObjGroupFactory<Mesh3D, Vertex3D>.RenderObjSectionFactory<T> section;
    private int group;
    
    private final ArrayList<LoadedImage> images = new ArrayList<LoadedImage>();
    private final ArrayList<ModelFactory> models = new ArrayList<ModelFactory>();
    private Image[] texOuts;

    protected ModelLoader(DynamicObjGroupRenderer.Factory<T> factory, 
            GeometricDataInterpreter<Mesh3D, Vertex3D> interpreter) {
        
        this.factory = factory;
        this.interpreter = interpreter;
    }

    public ModelFactory loadModel(DynamicHandleList<T> dhl, Model model) {
        int imgIndex = newImage(model.getImage());
        ModelFactory fac = newModel(dhl);
        fac.addPortion(model.getMesh(), imgIndex);
        return fac;
    }
    
    public ModelFactory newModel(DynamicHandleList<T> dhl) {
        ModelFactory mf = new ModelFactory(dhl);
        models.add(mf);
        return mf;
    }

    public ModelFactory newModel() {
        ModelFactory mf = new ModelFactory();
        models.add(mf);
        return mf;
    }

    public int newImage(LoadedImage img) {
        images.add(img);
        return images.size() - 1;
    }

    public void construct(DynamicRenderMode<T> instanceMode) {
        TextureSheet imgSheet = new TextureSheet(
                TextureUtils.createTextureSheet(
                        TextureUtils.getImageLoaders(images.toArray(new LoadedImage[images.size()])), 
                        -1, 
                        new TextureParams(),
                        2
                )
        );
        texOuts = new Image[images.size()];
        for(int i = 0; i < texOuts.length; i++) {
            texOuts[i] = imgSheet.getArea(i);
        }
        group = factory.addGroup(instanceMode, imgSheet);
        models.stream().forEach((model) -> {
            model.construct();
        });
    }
    
    public Vertex3D[] addPortionToSection(DynamicHandleList<T> dhl, Mesh3D mesh) {
        return factory.addSingleObject(group, dhl, interpreter, mesh);
    }
    
    /**
     * 
     */
    public class ModelFactory {
        private final DynamicHandleList<T> dhl;
        private final ArrayList<ModelPortion> sections = new ArrayList<ModelPortion>();
        private Vertex3D[][] vectors;

        private ModelFactory(DynamicHandleList<T> dhl) {
            this.dhl = dhl;
        }

        private ModelFactory() {
            this(new DynamicHandleList<T>());
        }

        public void addPortions(Mesh3D[] meshes, int[] texIndexs, Matrix transform, boolean flip) {
            if(meshes.length != texIndexs.length) {
                throw new IllegalArgumentException("Ill Matching Arrays");
            }
            for(int i = 0; i < meshes.length; i++) {
                addPortion(meshes[i], texIndexs[i], transform, flip);
            }
        }

        public void addPortions(Mesh3D[] meshes, int[] texIndexs) {
            addPortions(meshes, texIndexs, null, false);
        }

        public void addPortion(Mesh3D mesh, int texIndex, Matrix transform, boolean flip) {
            sections.add(new ModelPortion(mesh, texIndex, transform, flip));
        }

        public void addPortion(Mesh3D mesh, int texIndex) {
            addPortion(mesh, texIndex, null, false);
        }

        public void construct() {
            vectors = new Vertex3D[sections.size()][];
            for(int i = 0; i < sections.size(); i++) {
                Mesh3D m = sections.get(i).mesh;
                if(sections.get(i).texIndex != -1) {
                    m = m.transform(texOuts[sections.get(i).texIndex]);
                }
                if(sections.get(i).transform != null) {
                    m = m.transform(sections.get(i).transform, sections.get(i).flip);
                }
                vectors[i] = addPortionToSection(dhl, m);
            }
        }

        public DynamicHandleList<T> getDHL() {
            return dhl;
        }

        public Vertex3D[][] getVectors() {
            return vectors;
        }
    }
    
    private static class ModelPortion {
        final Mesh3D mesh;
        final int texIndex;
        final Matrix transform;
        final boolean flip;
        
        ModelPortion(Mesh3D mesh, int texIndex, Matrix transform, boolean flip) {
            this.mesh = mesh;
            this.texIndex = texIndex;
            this.transform = transform;
            this.flip = flip;
        }

    }
    
}