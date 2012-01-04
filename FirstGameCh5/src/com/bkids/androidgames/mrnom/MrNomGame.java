package com.bkids.androidgames.mrnom;

import android.util.Log;

import com.bkids.framework.Screen;
import com.bkids.framework.impl.AndroidGame;

public class MrNomGame extends AndroidGame {
	
	
	public Screen getStartScreen(){
		Log.d("MrNomGame", "calling start Screen");
		return new LoadingScreen(this);
	}

}
