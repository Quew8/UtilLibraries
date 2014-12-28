package com.quew8.geng3d.content;

import com.quew8.geng.geometry.Image;
import com.quew8.geng.geometry.Mesh;
import com.quew8.geng.geometry.TextureSheet;
import com.quew8.geng.rendering.DynamicHandleList;
import com.quew8.geng.rendering.RenderObjGroupFactory;
import com.quew8.geng.rendering.modes.DynamicRenderMode;
import com.quew8.gmath.Matrix;
import com.quew8.gmath.Vector;
import com.quew8.gutils.opengl.texture.LoadedImage;
import com.quew8.gutils.opengl.texture.TextureParams;
import com.quew8.gutils.opengl.texture.TextureUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Quew8
 * @param <T>
 */
public class ModelLoader<T> {
    protected final RenderObjGroupFactory<Mesh> factory;
    private RenderObjGroupFactory<Mesh>.RenderObjSectionFactory<T> section;

    private final ArrayList<LoadedImage> images = new ArrayList<LoadedImage>();
    private final ArrayList<ModelFactory> models = new ArrayList<ModelFactory>();
    private Image[] texOuts;

    protected ModelLoader(RenderObjGroupFactory<Mesh> factory) {
        this.factory = factory;
    }

    public ModelFactory loadModel(DynamicHandleList<T> dhl, Model model) {
        int imgIndex = newImage(model.getImage());
        ModelFactory fac = newModel(dhl);
        fac.addPortion(model.getMesh(), imgIndex, false);
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
                        TextureParams.create(),
                        2
                )
        );
        texOuts = new Image[images.size()];
        for(int i = 0; i < texOuts.length; i++) {
            texOuts[i] = imgSheet.getArea(i);
        }
        section = factory.createDynamicSection(instanceMode, imgSheet);
        for(ModelFactory model: models) {
            model.construct();
        }
    }
    
    public Vector[] addPortionToSection(DynamicHandleList<T> dhl, Mesh mesh, boolean dynamicImage) {
        if(dynamicImage) {
            throw new UnsupportedOperationException("No support for dynamic images in this ModelLoader");
        }
        return section.addDynamic(dhl, mesh);
    }
    
    /**
     * 
     */
    public class ModelFactory {
        private final DynamicHandleList<T> dhl;
        private final ArrayList<ModelPortion> sections = new ArrayList<ModelPortion>();
        private Vector[][] vectors;

        private ModelFactory(DynamicHandleList<T> dhl) {
            this.dhl = dhl;
        }

        private ModelFactory() {
            this(new DynamicHandleList<T>());
        }

        public void addPortions(Mesh[] meshes, int[] texIndexs, boolean[] dynamicImgs, Matrix transform, boolean flip) {
            if(meshes.length != texIndexs.length || meshes.length != dynamicImgs.length) {
                throw new IllegalArgumentException("Ill Matching Arrays");
            }
            for(int i = 0; i < meshes.length; i++) {
                addPortion(meshes[i], texIndexs[i], dynamicImgs[i], transform, flip);
            }
        }

        public void addPortions(Mesh[] meshes, int[] texIndexs, boolean dynamicImg, Matrix transform, boolean flip) {
            boolean[] dynamicImgs = new boolean[meshes.length];
            Arrays.fill(dynamicImgs, dynamicImg);
            addPortions(meshes, texIndexs, dynamicImgs, transform, flip);
        }

        public void addPortions(Mesh[] meshes, int[] texIndexs, boolean dynamicImg) {
            addPortions(meshes, texIndexs, dynamicImg, null, false);
        }

        public void addPortion(Mesh mesh, int texIndex, boolean dynamicImg, Matrix transform, boolean flip) {
            sections.add(new ModelPortion(mesh, texIndex, dynamicImg, transform, flip));
        }

        public void addPortion(Mesh mesh, int texIndex, boolean dynamicImg) {
            addPortion(mesh, texIndex, dynamicImg, null, false);
        }

        public void construct() {
            vectors = new Vector[sections.size()][];
            for(int i = 0; i < sections.size(); i++) {
                Mesh m = sections.get(i).mesh;
                if(sections.get(i).texIndex != -1) {
                    m = m.transform(texOuts[sections.get(i).texIndex]);
                }
                if(sections.get(i).transform != null) {
                    m = m.transform(sections.get(i).transform, sections.get(i).flip);
                }
                vectors[i] = addPortionToSection(dhl, m, sections.get(i).dynamicImg);
            }
        }

        public DynamicHandleList<T> getDHL() {
            return dhl;
        }

        public Vector[][] getVectors() {
            return vectors;
        }
    }
    
    /**
     * 
     * @param <T> 
     */
    /*private static class DynamicImageModelLoader<T extends TextureFetchable> extends ModelLoader<T> {
        private RenderObjGroupFactory<Mesh>.RenderObjSectionFactory<T> dynamicImageSection;
        
        public DynamicImageModelLoader(RenderObjGroupFactory<Mesh> factory) {
            super(factory);
        }
        
        @Override
        public void construct(DynamicRenderMode<T> instanceMode) throws IOException {
            dynamicImageSection = factory.createDynamicImageSection(instanceMode);
            super.construct(instanceMode);
        }
        
        @Override
        public Vector[] addPortionToSection(DynamicHandleList<T> dhl, Mesh mesh, boolean dynamicImage) {
            if(dynamicImage) {
                return dynamicImageSection.addDynamic(dhl, mesh);
            } else {
                return super.addPortionToSection(dhl, mesh, dynamicImage);
            }
        }
        
    }*/
    
    /**
     * 
     */
    private static class ModelPortion {
        final Mesh mesh;
        final int texIndex;
        final boolean dynamicImg;
        final Matrix transform;
        final boolean flip;
        
        ModelPortion(Mesh mesh, int texIndex, boolean dynamicImg, Matrix transform, boolean flip) {
            this.mesh = mesh;
            this.texIndex = texIndex;
            this.dynamicImg = dynamicImg;
            this.transform = transform;
            this.flip = flip;
        }

    }

    public static <T> ModelLoader<T> getModelLoader(RenderObjGroupFactory<Mesh> factory) {
        return new ModelLoader<T>(factory);
    }

    /*public static <T extends TextureFetchable> ModelLoader<T> getDynamicImageModelLoader(RenderObjGroupFactory<Mesh> factory) {
        return new DynamicImageModelLoader<T>(factory);
    }*/
    
}

