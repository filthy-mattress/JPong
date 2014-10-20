package main;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

public class GLColor {
	public static final GLColor WHITE		= new GLColor(Color.WHITE);
	public static final GLColor BLACK		= new GLColor(Color.BLACK);
	public static final GLColor RED			= new GLColor(Color.RED);
	public static final GLColor BLUE		= new GLColor(Color.BLUE);
	public static final GLColor GREEN		= new GLColor(Color.GREEN);
	public static final GLColor ORANGE		= new GLColor(Color.ORANGE);
	public static final GLColor YELLOW		= new GLColor(Color.YELLOW);
	public static final GLColor CYAN		= new GLColor(Color.CYAN);
	public static final GLColor MAGENTA		= new GLColor(Color.MAGENTA);
	public static final GLColor DARK_GRAY	= new GLColor(Color.DARK_GRAY);
	public static final GLColor GRAY		= new GLColor(Color.GRAY);
	public static final GLColor LIGHT_GRAY	= new GLColor(Color.LIGHT_GRAY);
	public static final GLColor PINK		= new GLColor(Color.PINK);
	
	private final float r,g,b,a;
	public GLColor(float r, float g, float b, float a){
		this.r=r;
		this.g=g;
		this.b=b;
		this.a=a;
	}
	public GLColor(float r, float g, float b){
		this(r, g, b, 1);
	}
	public GLColor(Color c){
		this(fixInt(c.getRed()),fixInt(c.getGreen()), fixInt(c.getBlue()), fixInt(c.getAlpha()));
	}
	public GLColor(GLColor copy){
		this(copy.r,copy.g,copy.b,copy.a);
	}
	private static float fixInt(int i){
		i=i%256;
		return i/255.0f;
	}
	public void activate(){
		GL11.glColor4f(r, g, b, a);
	}
	public void clearScreen(){
		GL11.glClearColor(r, g, b, a);
	}
	public float getRed() {
		return r;
	}
	public float getGreen() {
		return g;
	}
	public float getBlue() {
		return b;
	}
	public float getAlpha() {
		return a;
	}
}
