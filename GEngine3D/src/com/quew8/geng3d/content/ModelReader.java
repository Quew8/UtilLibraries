package com.quew8.geng3d.content;

import com.quew8.geng.geometry.Image;
import com.quew8.geng3d.geometry.Mesh3D;
import com.quew8.geng3d.models.collada.InstanceGeometry;
import com.quew8.geng3d.models.MeshDataFactoryImpl;
import com.quew8.geng3d.models.collada.Scene;
import com.quew8.geng3d.models.collada.parser.ColladaParser;
import com.quew8.geng3d.models.obj.parser.WavefrontObject;
import com.quew8.geng3d.models.obj.parser.WavefrontParser;
import com.quew8.gutils.FileUtils;
import com.quew8.gutils.content.ContentReader;
import com.quew8.gutils.content.Source;
import com.quew8.gutils.opengl.texture.TextureUtils;

/**
 *
 * @author Quew8
 */
public class ModelReader implements ContentReader<Model> {
    public static final String 
            TYPE = "type",
            NAME = "name",
            WAVEFRONT_TYPE = "wavefront", 
            COLLADA_TYPE = "collada";
    
    @Override
    public Model read(Source in) {
        String type;
        if(in.hasParam(TYPE)) {
            type = in.getParam(TYPE);
        } else {
            String extension = FileUtils.getExtension(in.getSource(0));
            switch(extension) {
                case "dae": type = COLLADA_TYPE; break;
                case "obj": type = WAVEFRONT_TYPE; break;
                default: throw new IllegalArgumentException("Unrecognized model file extension: \"" + extension + "\"");
            }
        }
        Mesh3D m;
        switch(type) {
            case COLLADA_TYPE: {
                ColladaParser parser = new ColladaParser();
                parser.read(in.getSource(0), in.getLoader());
                Scene scene = parser.getScene();
                System.out.println(scene.toString());
                if(in.hasParam(NAME)) {
                    String modelName = in.getParam(NAME);
                    //System.out.println("Looking for: \"" + modelName + "\"");
                    InstanceGeometry ig = scene.findGeometry(modelName);
                    if(ig == null) {
                        throw new RuntimeException("No geometry with name: \"" + modelName + "\"");
                    }
                    m = ig.getGeometry(MeshDataFactoryImpl.INSTANCE, Image.WHOLE);
                } else {
                    //System.out.println("Looking for 0th geometry");
                    m = scene.getGeometry()[0].getGeometry(MeshDataFactoryImpl.INSTANCE, Image.WHOLE);
                }
                m = m.transform(
                        /*Matrix.transpose(new Matrix(), */scene.getAsset().getUpAxis().getMatrix()/*)*/,
                        scene.getAsset().getUpAxis().isFlip()
                );
                break;
            }
            case WAVEFRONT_TYPE: {
                WavefrontParser parser = new WavefrontParser();
                parser.parse(in.getStream(0));
                WavefrontObject obj;
                if(in.hasParam(NAME)) {
                    obj = parser.getObject(in.getParam(NAME));
                } else {
                    obj = parser.getObject();
                }
                m = obj.getGeometry(MeshDataFactoryImpl.INSTANCE, Image.WHOLE);
                break;
            }
            default: throw new IllegalArgumentException("Unrecognized model type: \"" + type + "\"");
        }
        return new Model(m, TextureUtils.loadImage(in.getStream(1), true));
    }
    
}
