package com.bkids.androidgames.mrnom;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;

import com.bkids.framework.GameObject;
import com.bkids.framework.Pixmap;
import com.bkids.framework.impl.AndroidGraphics;

public class Snake implements GameObject{

	public static final int UP=0;
	public static final int LEFT = 1;
	public static final int DOWN = 2;
	public static final int RIGHT = 3;
	
	public List<SnakePart> parts = new ArrayList<SnakePart>();
	public int direction;

	private int spriteWidth;    // the width of the sprite to calculate the cut out rectangle
	private int spriteHeight;   // the height of the sprite

	private int currentFrame;   // the current frame
    private long frameTicker;   // the time of the last frame update
	private int framePeriod;    // milliseconds between each frame (1000/fps)

	private Pixmap pixmap;      // the animation sequence

	private int frameCount; // how many frames in our animation.
	
	private Pixmap currentPixmap;
	
	public Snake(int numberOfFrames){
		frameCount = numberOfFrames;
		pixmap = Assets.headAnimRight;
	    spriteWidth = pixmap.getWidth() / frameCount;
	    spriteHeight = pixmap.getHeight();
		direction = UP;
		parts.add(new SnakePart(5, 6, Stain.TYPE_1));
		parts.add(new SnakePart(5, 7, Stain.TYPE_1 ));
		parts.add(new SnakePart(5, 8, Stain.TYPE_1));
		
		frameTicker = 0l;
		framePeriod = 1000/5; // 1 frame every 1/5 of a second
		currentFrame = 0; // start with 1st frame
		currentPixmap = AndroidGraphics.getPixmapPart(spriteWidth * currentFrame, 0, spriteWidth, spriteHeight , pixmap);
		
	}
	
	
	public void turnLeft(){
		direction +=1;
		if(direction > RIGHT){
			direction = UP;
		}
	}
	
	
	public void turnRight(){
		direction -=1;
		if(direction < UP){
			direction = RIGHT;
		}
	}
	
	public void eat(int type){
		SnakePart head = parts.get(0); // get head and add piece to where head was
		parts.add(1, new SnakePart(head.x, head.y, type));
	}
	
	public void advance(){
		SnakePart head = parts.get(0);
		
		int len = parts.size() -1;
		for (int i = len; i > 0; i--) {
			SnakePart beforePart = parts.get(i-1);
			SnakePart currentPart = parts.get(i);
			currentPart.x = beforePart.x;
			currentPart.y = beforePart.y;
		}

		// move head
		
		switch (direction) {
		case UP:
			head.y -=1;
			break;

		case DOWN:
			head.y +=1;
			break;
		case RIGHT:
			head.x +=1;
			break;
		case LEFT:
			head.x -=1;
			break;
		default:
			break;
		}
		
		// if move caused him to go off screen... loop around
		if(head.x < 0)
			head.x = 9;
		if(head.x > 9)
			head.x = 0;
		if(head.y < 0)
			head.y = 12;
		if(head.y > 12)
			head.y = 0;
		
	}
	
	public boolean checkBitten(){
		int len = parts.size();
		SnakePart head = parts.get(0);
		for (int i = 1; i < len; i++) {
			SnakePart part = parts.get(i);
			if(part.x == head.x && part.y == head.y){
				return true;
			}
		}
		return false;
	}
	
	public Pixmap draw(long gameTime) {
		
		//Log.d(Snake.class.toString(), "gameTime=)" + gameTime + " frameTicker =" + frameTicker + " framePeriod=" + framePeriod);
		
		if(gameTime > frameTicker + framePeriod){
			// if enough time has passed move to the next frame
			frameTicker = gameTime;
			// advance the frame
			currentFrame++;
			if(currentFrame>=frameCount){
				currentFrame =0;
			}
			currentPixmap = AndroidGraphics.getPixmapPart(spriteWidth * currentFrame, 0, spriteWidth, spriteHeight , pixmap);
		}
		
		
		
		
		// get correct head picture
//		Pixmap headPixmap = null;
//		if(direction == Snake.UP)
//			headPixmap = Assets.headUp;
//		if(direction == Snake.LEFT)
//			headPixmap = Assets.headLeft;
//		if(direction == Snake.DOWN)
//			headPixmap = Assets.headDown;
//		if(direction == Snake.RIGHT)
//			headPixmap = Assets.headRight;
//		
		return currentPixmap;
	}
	
}
