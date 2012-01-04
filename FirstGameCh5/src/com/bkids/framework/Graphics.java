package com.bkids.framework;

public interface Graphics {
	
	public static enum PixmapFormat{
		ARGB8888, ARGB4444, RGB565
	}
	
	/**
	 * Loads an image given either in JPEG or PNG format.
	 * 
	 * @param fileName
	 * @param format - specifies the format as a "hint" to the loading system to control memory footprint.
	 * @return Pixmap
	 */
	public Pixmap newPixmap(String fileName, PixmapFormat format);
	
	/**
	 * Clears the framebuffer with the given color.
	 * Colors specified in ARGB8888 format.
	 * 
	 * @param color
	 */
	public void clear(int color);
	
	public void drawPixel(int x, int y, int color);
	
	/**
	 * Draws a line from the first  x, y coordinate to the second x, y
	 * coordinate of the given color.
	 * Will ignore coordinates outside of screen area.
	 * 
	 * @param x
	 * @param y
	 * @param x2
	 * @param y2
	 * @param color
	 */
	public void drawLine(int x, int y, int x2, int y2, int color);
	
	/**
	 * Draws a rectangle with the rectangles top left corner being at
	 * the given x, y coordinates and having the width and height in pixels
	 * as passed in.
	 * 
	 * @param x
	 * @param y
	 * @param width - pixel width
	 * @param height - pixel height
	 * @param color - ARGB8888 value of color
	 */
	public void drawRect(int x, int y, int width, int height, int color);
	
	public void drawPixmap(Pixmap pixmap, int x, int y, int srcX, int srcY, int srcWidth, int srcHeight);
	
	public void drawPixmap(Pixmap pixmap, int x, int y);
	
	/** 
	 * 
	 * @return Pixel width of framebuffer(screen)
	 */
	public int getWidth();
	
	/**
	 * 
	 * @return Pixel height of framebuffer(screen)
	 */
	public int getHeight();

}
