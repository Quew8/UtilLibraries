package com.quew8.gutils;

/**
 * 
 * @author Will
 */
public class Colour {
    public static final Colour BLACK = new Colour(0f, 0f, 0f);
    public static final Colour WHITE = new Colour(1f, 1f, 1f);
    public static final Colour RED = new Colour(1f, 0f, 0f);
    public static final Colour GREEN = new Colour(0f, 1f, 0f);
    public static final Colour BLUE = new Colour(0f, 0f, 1f);
    public static final Colour GREY = new Colour(0.5f, 0.5f, 0.5f);
    public static final Colour YELLOW = new Colour(1f, 1f, 0f);
    public static final Colour CYAN = new Colour(0f, 1f, 1f);
    public static final Colour MAGENTA = new Colour(1f, 0f, 1f);
    public static final Colour BROWN = new Colour(0.6f, 0.3f, 0.1f);
    public static final Colour PINK = new Colour(1f, 0.4f, 0.8f);
    public static final Colour ORANGE = new Colour(1f, 0.5f, 0f);
    
    private float red;
    private float green;
    private float blue;
    private float alpha;
    
    /**
     * 
     * @param red
     * @param green
     * @param blue
     * @param alpha
     */
    public Colour(float red, float green, float blue, float alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }
    
    /**
     * 
     * @param red
     * @param green
     * @param blue
     */
    public Colour(float red, float green, float blue) {
        this(red, green, blue, 1);
    }
    
    /**
     * 
     * @param colour
     * @param alpha
     */
    public Colour(Colour colour, float alpha) {
        this(colour.getRed(), colour.getGreen(), colour.getBlue(), alpha);
    }
    
    public Colour(Colour colour) {
        this(colour.getRed(), colour.getGreen(), colour.getBlue(), colour.getAlpha());
    }
    
    public Colour() {
        this(1, 1, 1, 1);
    }
    
    public float[] getRGB() {
        return new float[]{red, green, blue};
    }
    
    public float[] getRGBA() {
        return new float[]{red, green, blue, alpha};
    }
    
    /**
     * 
     * @return
     */
    public float getBlue() {
        return blue;
    }
    
    /**
     * 
     * @param blue
     */
    public void setBlue(float blue) {
        this.blue = blue;
    }
    
    /**
     * 
     * @return
     */
    public float getGreen() {
        return green;
    }
    
    /**
     * 
     * @param green
     */
    public void setGreen(float green) {
        this.green = green;
    }
    
    /**
     * 
     * @return
     */
    public float getRed() {
        return red;
    }
    
    /**
     * 
     * @param red
     */
    public void setRed(float red) {
        this.red = red;
    }
    
    /**
     * 
     * @return
     */
    public float getAlpha() {
        return alpha;
    }
    
    /**
     * 
     * @param alpha
     */
    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }
    
    public void clamp(float min, float max) {
        if(red < min) {
            red = min;
        } else if(red > max) {
            red = max;
        }
        if(green < min) {
            green = min;
        } else if(green > max) {
            green = max;
        }
        if(blue < min) {
            blue = min;
        } else if(blue > max) {
            blue = max;
        }
        if(alpha < min) {
            alpha = min;
        } else if(alpha > max) {
            alpha = max;
        }
    }
    
    public void clamp() {
        clamp(0, 1);
    }
    
    @Override
    public String toString() {
        return "Colour:" + red + ", " + green + ", " + blue + ", " + alpha;
    }
    
    public static Colour addRGB(Colour result, Colour c1, Colour c2) {
        result.setRed(c1.getRed() + c2.getRed());
        result.setGreen(c1.getGreen() + c2.getGreen());
        result.setBlue(c1.getBlue() + c2.getBlue());
        return result;
    }
    
    public static Colour addRGBA(Colour result, Colour c1, Colour c2) {
        addRGB(result, c1, c2);
        result.setAlpha(c1.getAlpha() + c2.getAlpha());
        return result;
    }
    
    public static Colour scaleRGB(Colour result, Colour c1, float f) {
        result.setRed(c1.getRed() * f);
        result.setGreen(c1.getGreen() * f);
        result.setBlue(c1.getBlue() * f);
        return result;
    }
    
    public static Colour scaleRGBA(Colour result, Colour c1, float f) {
        scaleRGB(result, c1, f);
        result.setAlpha(c1.getAlpha() * f);
        return result;
    }
    
    public static Colour blendRGB(Colour result, Colour c1, Colour c2) {
        result.setRed( ( c1.getRed() + c2.getRed() ) / 2);
        result.setGreen( ( c1.getGreen() + c2.getGreen() ) / 2);
        result.setBlue( ( c1.getBlue() + c2.getBlue() ) / 2);
        return result;
    }
    
    public static Colour blendRGBA(Colour result, Colour c1, Colour c2) {
        blendRGB(result, c1, c2);
        result.setAlpha( ( c1.getAlpha() + c2.getAlpha() ) / 2);
        return result;
    }
    
    public static Colour blendRGB(Colour result, Colour c1, float weight, Colour c2) {
        float w2 = 1 - weight;
        result.setRed( ( c1.getRed() * weight ) + ( c2.getRed() * w2 ) );
        result.setGreen( ( c1.getGreen() * weight ) + ( c2.getGreen() * w2 ) );
        result.setBlue( ( c1.getBlue() * weight ) + ( c2.getBlue() * w2 ) );
        return result;
    }
    
    public static Colour blendRGBA(Colour result, Colour c1, float weight, Colour c2) {
        float w2 = 1 - weight;
        blendRGB(result, c1, weight, c2);
        result.setAlpha( ( c1.getAlpha() * weight ) + ( c2.getAlpha() * w2 ) );
        return result;
    }
    
    public static Colour getComplimentRGB(Colour result, Colour c) {
        result.setRed(1 - c.getRed());
        result.setGreen(1 - c.getGreen());
        result.setBlue(1 - c.getBlue());
        return result;
    }
    
    public class ByteColour {
        private final byte red, green, blue, alpha;
        
        public ByteColour() {
            this.red = (byte)(Colour.this.red * 255);
            this.green = (byte)(Colour.this.green * 255);
            this.blue = (byte)(Colour.this.blue * 255);
            this.alpha = (byte)(Colour.this.alpha * 255);
        }
        
        public byte getRed() {
            return red;
        }
        
        public byte getGreen() {
            return green;
        }
        
        public byte getBlue() {
            return blue;
        }
        
        public byte getAlpha() {
            return alpha;
        }
        
        public int getRGBA() {
            return (red << 3) | (green << 2) | (blue << 1) | alpha;
        }
        
        @Override
        public String toString() {
            return "Byte Colour:" + (red&0xFF) + ", " + (green&0xFF) + ", " + (blue&0xFF) + ", " + (alpha&0xFF);
        }
    }
}
