package main;

public interface Renderable {
	/**
	 * Draw the object on the screen.
	 * @param delta The delta in milliseconds
	 */
	void render(int delta);
	/**
	 * Checks if object is visible
	 * @return true if object is currently visible false otherwise
	 */
	boolean isVisible();
	/**
	 * Sets the visibility property
	 * @param visible the new value
	 */
	void setVisible(boolean visible);
}
