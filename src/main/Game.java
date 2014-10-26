package main;

import game.LocalMatch;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class Game extends RenderableCollection implements Runnable{
	public static double millisToSecs(long millis){
		return millis/100.0;
	}
	private long lastFrame=getTime();
	private int fps=0;
	private long lastFPS = getTime();
	private int fpsCap=60;
	public Game() throws LWJGLException{
		this(800, 600, false);
	}
	public Game(int width, int height, boolean fullscreen) throws LWJGLException{
		Display.setDisplayMode(new DisplayMode(800,600));
		Display.setFullscreen(fullscreen);
		Display.create();
	}
	
	public void run(){
		while(!Display.isCloseRequested()){
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			updateFPS();
			renderAll();
			Display.update();
			if(fpsCap>0)
				Display.sync(fpsCap);
		}
		Display.destroy();
		System.exit(0);
	}
	
	private void renderAll(){
		int delta = getDelta();
		this.render(delta);
	}
	
	/**
	 * Get accurate system time
	 * 
	 * @return System time in milliseconds
	 */
	public static long getTime(){
		return (Sys.getTime()*1000)/Sys.getTimerResolution();
	}
	
	/**
	 * Time since last frame
	 * @return milliseconds since last frame
	 */
	public int getDelta(){
		long time = getTime();
		int delta = (int) (time-lastFrame);
		lastFrame = time;
		return delta;
	}
	
	private void updateFPS(){
		if(getTime()-lastFPS > 1000){
			Display.setTitle("FPS: "+fps);
			fps=0;
			lastFPS+=1000;
		}
		fps++;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Game g = null;
		try {
			g = new Game();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		g.fpsCap = -1;
		LocalMatch match = new LocalMatch();
		g.addItem(match);
		g.run();
	}

}
