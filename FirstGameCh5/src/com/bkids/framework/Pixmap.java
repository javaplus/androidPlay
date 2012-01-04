package com.bkids.framework;

import com.bkids.framework.Graphics.PixmapFormat;

public interface Pixmap {
	
	public int getWidth();
	public int getHeight();
	
	public PixmapFormat getFormat();
	
	public void dispose();

}
