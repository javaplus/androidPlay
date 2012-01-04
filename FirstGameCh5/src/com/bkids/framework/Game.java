package com.bkids.framework;

public interface Game {
	
	public Input getInput();
	
	public Graphics getGraphics();
	
	public Audio getAudio();
	
	public void setScreen(Screen screen);
	
	public Screen getCurrentScreen();
	
	public Screen getStartScreen();
	
	public FileIO getFileIO();

}
