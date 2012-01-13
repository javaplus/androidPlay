package com.bkids.androidgames.mrnom;

import com.bkids.framework.GameDrawable;
import com.bkids.framework.GameObject;
import com.bkids.framework.Pixmap;

public class SnakePartDrawable extends SnakePart implements GameDrawable {

	public SnakePartDrawable(int x, int y, int type) {
		super(x, y, type);
		// TODO Auto-generated constructor stub
	}

	public Pixmap draw(float deltaTime) {
		Pixmap image = null;
		switch (type) {
		case Stain.TYPE_1:
			image = Assets.body1;
			break;
		case Stain.TYPE_2:
			image = Assets.body2;
			break;
		case Stain.TYPE_3:
			image = Assets.body3;
			break;
		}
		
		return image;
	}

	public void initialize(GameObject gameObject) {
		SnakePart part = (SnakePart)gameObject;
		this.type = part.type;
		this.x = part.x;
		this.y = part.y;
	}

}
