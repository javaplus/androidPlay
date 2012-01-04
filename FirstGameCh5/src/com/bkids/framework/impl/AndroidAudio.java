package com.bkids.framework.impl;

import java.io.IOException;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;

import com.bkids.framework.Audio;
import com.bkids.framework.Music;
import com.bkids.framework.Sound;

public class AndroidAudio implements Audio {
	
	AssetManager assetManager;
	SoundPool soundPool;
	
	
	public AndroidAudio(Activity activity){
		activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		this.assetManager = activity.getAssets();
		this.soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
	}

	public Music newMusic(String filename) {
		try{
			AssetFileDescriptor assetDescriptor = assetManager.openFd(filename);
			return new AndroidMusic(assetDescriptor);
		}catch (IOException e) {
			throw new RuntimeException("Couldn't load music '" + filename + "'");
		}
	}

	public Sound newSound(String filename) {
		try {
			AssetFileDescriptor assetDescriptor = assetManager.openFd(filename);
			int soundId = soundPool.load(assetDescriptor, 0);
			return new AndroidSound(soundPool, soundId);
		} catch (IOException e) {
			throw new RuntimeException("Couldn't load sound '" + filename + "'");
		}
		
	}

}
