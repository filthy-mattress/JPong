package game;

import geom.Point;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;

public class LocalPlayer extends Player {
	private static void checkKeyboard(){
		if(!Keyboard.isCreated()){
			try {
				Keyboard.create();
			} catch (LWJGLException e) {
				e.printStackTrace();
				System.exit(e.getMessage().hashCode());
			}
		}
	}
	public static final KeyMode WASD = KeyMode.WASD;
	public static final KeyMode ARROWS = KeyMode.ARROWS;
	
	protected KeyMode mode;
	public LocalPlayer(Point start, KeyMode mode){
		super(start);
		this.mode = mode;
		checkKeyboard();
	}
	public LocalPlayer(KeyMode mode){
		this(new Point(0,0), mode);
	}
	public int getUpKey(){
		switch(this.mode){
		case WASD: return Keyboard.KEY_W;
		case ARROWS: return Keyboard.KEY_UP;
		}
		return 0;
	}
	public int getDownKey(){
		switch(this.mode){
		case WASD: return Keyboard.KEY_S;
		case ARROWS: return Keyboard.KEY_DOWN;
		}
		return 0;
	}
	
	
	@Override
	public boolean isGoingUp() {
		return Keyboard.isKeyDown(this.getUpKey());
	}

	@Override
	public boolean isGoingDown() {
		return Keyboard.isKeyDown(this.getDownKey());
	}

}
