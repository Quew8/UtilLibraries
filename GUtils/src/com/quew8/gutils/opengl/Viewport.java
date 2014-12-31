package com.quew8.gutils.opengl;

import static com.quew8.gutils.opengl.OpenGL.glScissor;
import static com.quew8.gutils.opengl.OpenGL.glViewport;

/**
 * 
 * @author Quew8
 */
public class Viewport {
    private final int xPos, yPos;
    private final int width, height;
    
    public Viewport(int xPos, int yPos, int width, int height) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
    }
    
    public Viewport(int width, int height) {
        this(0, 0, width, height);
    }
    
    public void set() {
        glViewport(xPos, yPos, getWidth(), getHeight());
    }
    
    public void setScissor() {
        glScissor(getX(), getY(), getWidth(), getHeight());
    }
    
    public boolean getIsLandscape() {
    	return width > height;
    }
    
    public float getCurrentOrientationAspectRatio() {
    	return getIsLandscape() ?
    			getLandscapeAspectRatio() :
    				getPortraitAspectRatio();
    }
    
    public float getPortraitAspectRatio() {
        return (float)getHeight() / getWidth();
    }
    
    public float getLandscapeAspectRatio() {
    	return (float)getWidth() / getHeight();
    }
    
    public int getX() {
        return xPos;
    }
    
    public int getY() {
        return yPos;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public float getPixelWidth() {
        return 1f / width;
    }
    
    public float getPixelHeight() {
        return 1f / height;
    }
    
    public Viewport getSubViewport(float left, float right, float bottom, float top) {
        int x0 = (int) ( xPos + ( width * left ) );
        int y0 = (int) ( yPos + ( height * bottom ) );
        int x1 = (int) ( xPos + ( width * right ) );
        int y1 = (int) ( yPos + ( height * top ) );
    	return new Viewport(
                x0,
                y0,
                x1 - x0,
                y1 - y0
        );
    }
    
    public Viewport getCentredSubViewport(float subWidth, float subHeight) {
    	float cx = xPos + ( width / 2 );
    	float cy = yPos + ( height / 2 );
    	return new Viewport(
    			(int) ( cx - ( width * ( subWidth / 2 ) ) ),
    			(int) ( cy - ( height * ( subHeight / 2 ) ) ),
    			(int) ( cx + ( width * ( subWidth / 2 ) ) ),
    			(int) ( cy + ( height * ( subHeight / 2 ) ) )
    			);
    }
    
    @Override
    public String toString() {
        return "Viewport " + xPos + " " + yPos + " : " + width + " x " + height; 
    }
}
