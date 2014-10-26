package geom;

import main.GLColor;
import main.Renderable;

import org.lwjgl.opengl.GL11;

public abstract class Primitive implements Renderable {
	private boolean visible = true;
	private Surface background = null;
	private GLColor lineColor = null;
	
	public abstract Point[] getPoints();
	
	/**
	 * Called at the start of the render(int) method. Does nothing.
	 * @param delta the delta in milliseconds
	 */
	public void update(int delta){
		
	}
	
	@Override
	public void render(int delta) {
		update(delta);
		if(background!=null){
			background.apply();
			runPoints(GL11.GL_POLYGON);
		}
		if(lineColor!=null){
			lineColor.activate();
			runPoints(GL11.GL_LINE_LOOP);
		}
	}
	
	private void runPoints(int mode){
		GL11.glBegin(mode);
		for(Point p : getPoints()){
			p.activate();
		}
		GL11.glEnd();
	}

	@Override
	public boolean isVisible() {
		return visible && (lineColor!=null || background!=null);
	}

	@Override
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public void setBackgroundColor(GLColor backgroundColor) {
		this.setBackground(new ColorSurface(backgroundColor));
	}

	public GLColor getLineColor() {
		return lineColor;
	}

	public void setLineColor(GLColor lineColor) {
		this.lineColor = lineColor;
	}

	public Surface getBackground() {
		return background;
	}

	public void setBackground(Surface background) {
		this.background = background;
	}

}
