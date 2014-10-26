package geom;

import main.GLColor;

public class ColorSurface extends Surface {
	
	public GLColor color;
	
	public ColorSurface(GLColor color){
		this.color = color;
	}

	@Override
	public void apply() {
		this.color.activate();
	}

}
