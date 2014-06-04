package com.quew8.gmath;

/**
 * 
 * @author Quew8
 */
public class Quad2 {
	private final float minX, minY, maxX, maxY;
	
	public Quad2(float minX, float minY, float maxX, float maxY) {
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
	}
	
	public Quad2(Vector2 min, Vector2 max) {
		this(min.getX(), min.getY(), max.getX(), max.getY());
	}
	
	public boolean isInside(float x, float y) {
		return 
				x >= minX && 
				y >= minY && 
				x <= maxX && 
				y <= maxY;
	}
	
	public boolean isInside(Vector2 point) {
		return isInside(point.getX(), point.getY());
	}
}
