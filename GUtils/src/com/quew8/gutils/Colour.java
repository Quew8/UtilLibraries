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
    
    public Colour() {
        this(1, 1, 1, 1);
    }
    
    public float[] getRGB(float[] out, int offset) {
        out[offset] = red;
        out[offset + 1] = green;
        out[offset + 2] = blue;
        return out;
    }
    
    public float[] getRGBA(float[] out, int offset) {
        out[offset] = red;
        out[offset + 1] = green;
        out[offset + 2] = blue;
        out[offset + 3] = alpha;
        return out;
    }
    
    public float getBlue() {
        return blue;
    }
    
    public Colour setBlue(float blue) {
        this.blue = blue;
        return this;
    }
    
    public float getGreen() {
        return green;
    }
    
    public Colour setGreen(float green) {
        this.green = green;
        return this;
    }
    
    public float getRed() {
        return red;
    }
    
    public Colour setRed(float red) {
        this.red = red;
        return this;
    }
    
    public float getAlpha() {
        return alpha;
    }
    
    public Colour setAlpha(float alpha) {
        this.alpha = alpha;
        return this;
    }
    
    public Colour setRGB(Colour src) {
        this.red = src.red;
        this.green = src.green;
        this.blue = src.blue;
        return this;
    }
    
    public Colour setRGB(float red, float green, float blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        return this;
    }
    
    public Colour setRGBA(Colour src) {
        this.red = src.red;
        this.green = src.green;
        this.blue = src.blue;
        this.alpha = src.alpha;
        return this;
    }
    
    public Colour setRGBA(float red, float green, float blue, float alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
        return this;
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

    public boolean equals(Colour other) {
        if(this == other) {
            return true;
        }
        if(other == null) {
            return false;
        }
        if(Float.floatToIntBits(this.red) != Float.floatToIntBits(other.red)) {
            return false;
        }
        if(Float.floatToIntBits(this.green) != Float.floatToIntBits(other.green)) {
            return false;
        }
        if(Float.floatToIntBits(this.blue) != Float.floatToIntBits(other.blue)) {
            return false;
        }
        return Float.floatToIntBits(this.alpha) == Float.floatToIntBits(other.alpha);
    }
    
    public static Colour addRGB(Colour result, Colour c1, Colour c2) {
        result.red = c1.red + c2.red;
        result.green = c1.green + c2.green;
        result.blue = c1.blue + c2.blue;
        return result;
    }
    
    public static Colour addRGBA(Colour result, Colour c1, Colour c2) {
        result.red = c1.red + c2.red;
        result.green = c1.green + c2.green;
        result.blue = c1.blue + c2.blue;
        result.alpha = c1.alpha + c2.alpha;
        return result;
    }
    
    public static Colour scaleRGB(Colour result, Colour c1, float f) {
        result.red = c1.red * f;
        result.green = c1.green * f;
        result.blue = c1.blue * f;
        return result;
    }
    
    public static Colour scaleRGBA(Colour result, Colour c1, float f) {
        result.red = c1.red * f;
        result.green = c1.green * f;
        result.blue = c1.blue * f;
        result.alpha = c1.alpha * f;
        return result;
    }
    
    public static Colour blendRGB(Colour result, Colour c1, Colour c2) {
        result.red =  (c1.red + c2.red) / 2;
        result.green =  (c1.green + c2.green) / 2;
        result.blue =  (c1.blue + c2.blue) / 2;
        return result;
    }
    
    public static Colour blendRGBA(Colour result, Colour c1, Colour c2) {
        result.red = (c1.red + c2.red) / 2;
        result.green = (c1.green + c2.green) / 2;
        result.blue = (c1.blue + c2.blue) / 2;
        result.alpha = (c1.alpha + c2.alpha) / 2;
        return result;
    }
    
    public static Colour blendRGB(Colour result, Colour c1, float weight, Colour c2) {
        float w2 = 1 - weight;
        result.red =  ( c1.red * weight ) + ( c2.red * w2 );
        result.green =  ( c1.green * weight ) + ( c2.green * w2 );
        result.blue =  ( c1.blue * weight ) + ( c2.blue * w2 );
        return result;
    }
    
    public static Colour blendRGBA(Colour result, Colour c1, float weight, Colour c2) {
        float w2 = 1 - weight;
        result.red =  ( c1.red * weight ) + ( c2.red * w2 );
        result.green =  ( c1.green * weight ) + ( c2.green * w2 );
        result.blue =  ( c1.blue * weight ) + ( c2.blue * w2 );
        result.alpha =  (c1.getAlpha() * weight) + (c2.getAlpha() * w2);
        return result;
    }
    
    public static Colour getComplimentRGB(Colour result, Colour c) {
        result.red = 1 - c.red;
        result.green = 1 - c.green;
        result.blue = 1 - c.blue;
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
