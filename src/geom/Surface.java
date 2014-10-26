package geom;

public abstract class Surface {
	private boolean visible = true;
	
	public abstract void apply();

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
