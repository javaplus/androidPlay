package com.bkids.framework.impl;

import java.io.IOException;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

import com.bkids.framework.Music;

public class AndroidMusic implements Music, OnCompletionListener {

	MediaPlayer mediaPlayer;
	boolean isPrepared = false;
	
	public AndroidMusic(AssetFileDescriptor assetDescriptor){
		mediaPlayer = new MediaPlayer();
		try {
			mediaPlayer.setDataSource(assetDescriptor.getFileDescriptor(), assetDescriptor.getStartOffset(), assetDescriptor.getLength());
			mediaPlayer.prepare();
			isPrepared = true;
			mediaPlayer.setOnCompletionListener(this);
		} catch (Exception e) {
			throw new RuntimeException("Couldn't load music");
		}
	}
	
	public void onCompletion(MediaPlayer arg0) {
		synchronized (this) {
			isPrepared = false;
		}

	}

	public void play() {
		if(mediaPlayer.isPlaying()){
			return;
		}
		try {
			synchronized (this) {
				if(!isPrepared){
					mediaPlayer.prepare();
				}
				mediaPlayer.start();
			}
			
		} catch (IllegalStateException ise) {
			ise.printStackTrace();
		}catch (IOException ioe) {
			ioe.printStackTrace();
		}

	}

	public void stop() {
		mediaPlayer.stop();
		synchronized (this) {
			isPrepared = false;
		}

	}

	public void pause() {
		mediaPlayer.pause();

	}

	public void setLooping(boolean looping) {
		mediaPlayer.setLooping(looping);

	}

	public void setVolume(float volume) {
		mediaPlayer.setVolume(volume, volume);

	}

	public boolean isPlaying() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isStopped() {
		
		return !mediaPlayer.isPlaying();
	}

	public boolean isLooping() {
		return mediaPlayer.isLooping();
	}

	public void dispose() {
		if(mediaPlayer.isPlaying()){
			mediaPlayer.stop();
			mediaPlayer.release();
		}

	}

}
