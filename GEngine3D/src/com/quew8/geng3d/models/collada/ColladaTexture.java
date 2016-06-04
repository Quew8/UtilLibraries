package com.quew8.geng3d.models.collada;

/**
 *
 * @author Quew8
 */
public class ColladaTexture {
    private final Sampler2D texture;
    private final String texcoord;
    
    public ColladaTexture(Sampler2D texture, String texcoord) {
        this.texture = texture;
        this.texcoord = texcoord;
    }

    public Sampler2D getTexture() {
        return texture;
    }

    public String getTexcoord() {
        return texcoord;
    }
    
    public static class Sampler2D {
        private final Surface surface;

        public Sampler2D(Surface surface) {
            this.surface = surface;
        }

        public Surface getSurface() {
            return surface;
        }
    }
    
    public static class Surface {
        private final String type;
        private final Image initFrom;

        public Surface(String type, Image initFrom) {
            this.type = type;
            this.initFrom = initFrom;
        }

        public String getType() {
            return type;
        }

        public Image getInitFrom() {
            return initFrom;
        }
    }
    
    public static class Image {
        private final String name;
        private final String initFrom;

        public Image(String name, String initFrom) {
            this.name = name;
            this.initFrom = initFrom;
        }

        public String getName() {
            return name;
        }

        public String getInitFrom() {
            return initFrom;
        }
    }
}
