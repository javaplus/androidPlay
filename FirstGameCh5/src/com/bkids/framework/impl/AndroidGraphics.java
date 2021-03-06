package com.bkids.framework.impl;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.bkids.framework.Graphics;
import com.bkids.framework.Pixmap;

public class AndroidGraphics implements Graphics {

	AssetManager assetManager;
	Bitmap frameBuffer;
	Canvas canvas;
	Paint paint;
	Rect srcRect = new Rect();
	Rect dstRect = new Rect();
	
	
	public AndroidGraphics(AssetManager assets, Bitmap frameBuffer){
		
		this.assetManager = assets;
		this.frameBuffer = frameBuffer;
		this.canvas = new Canvas(frameBuffer);
		this.paint = new Paint();
	}
	
	public Pixmap newPixmap(String fileName, PixmapFormat format) {
		
		Config config = null;
		if(format == PixmapFormat.RGB565){
			config = Config.RGB_565;
		}else if(format ==PixmapFormat.ARGB4444){
			config = Config.ARGB_4444;
		}else{
			config = Config.ARGB_8888;
		}
		Options options = new Options();
		options.inPreferredConfig = config;
		InputStream in = null;
		Bitmap bitmap = null;
		try {
			in = assetManager.open(fileName);
			bitmap = BitmapFactory.decodeStream(in,null, options);
			if(bitmap==null){
				throw new RuntimeException("Couldn't load bitmap from asset '" + fileName + "'");
			}
			
		} catch (IOException e) {
			Log.e("AndroidGraphics", "Couldn't load bitmap from asset '" + fileName + "'" + e.getMessage(), e);
			throw new RuntimeException("Couldn't load bitmap from asset '" + fileName + "'", e);
		}finally{
			if(in!=null){
				try {
					in.close();	
				} catch (IOException e2) {
					// TODO: handle exception
				}
				
			}
		}
		if(bitmap.getConfig() == Config.RGB_565){
			format = PixmapFormat.RGB565;
		}else if(bitmap.getConfig() == Config.ARGB_4444){
			format = PixmapFormat.ARGB4444;
		}else{
			format = PixmapFormat.ARGB8888;
		} 
		
		return new AndroidPixmap(bitmap, format);
		
	}

	/**
	 * Paint the entire frameBuffer with the specified color.
	 */
	public void clear(int color) {
		// extract our individual R G and B color values from our color value
		canvas.drawRGB((color & 0xff0000) >> 16, (color & 0xff00)>>8, (color & 0xff));

	}

	public void drawPixel(int x, int y, int color) {
		paint.setColor(color);
		canvas.drawPoint(x, y, paint);
	}

	public void drawLine(int x, int y, int x2, int y2, int color) {
		paint.setColor(color);
		canvas.drawLine(x, y, x2, y2, paint);
	}

	public void drawRect(int x, int y, int width, int height, int color) {
		paint.setColor(color);
		canvas.drawRect(x, y, x+width-1, y+height-1, paint);

	}

	/**
	 * Draw a section of the Pixmap bound by the srcX, srcY, and srcWidht, and srcHeight
	 * to the canvas at the x, y location.
	 * 
	 * Does not do scaling.
	 * 
	 */
	public void drawPixmap(Pixmap pixmap, int x, int y, int srcX, int srcY,
			int srcWidth, int srcHeight) {
		
		Rect src = new Rect(); // what part of pixmap to show.
		src.left = srcX;
		src.top = srcY;
		src.right = srcX + srcWidth -1;
		src.bottom = srcY + srcHeight -1;
		
		Rect dst = new Rect(); // where to put it on canvas
		dst.left = x;
		dst.top = y;
		dst.right = x + srcWidth -1;
		dst.bottom = y+ srcHeight -1;
		
		canvas.drawBitmap(((AndroidPixmap)pixmap).bitmap, src, dst, null);		
		

	}

	public void drawPixmap(Pixmap pixmap, int x, int y) {
		canvas.drawBitmap(((AndroidPixmap)pixmap).bitmap, x, y, null);

	}

	public int getWidth() {
		return frameBuffer.getWidth();
	}

	public int getHeight() {
		return frameBuffer.getHeight();
	}
	
	public static Pixmap getPixmapPart(int x, int y, int width, int height, Pixmap pixmap){
		AndroidPixmap aPixmap = (AndroidPixmap) pixmap;
		
		Bitmap newBitMap = Bitmap.createBitmap(aPixmap.bitmap, x, y, width, height);
		
		return new AndroidPixmap(newBitMap, PixmapFormat.ARGB4444);
	}

}
