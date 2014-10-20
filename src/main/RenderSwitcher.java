package main;

import java.util.HashMap;

public class RenderSwitcher<E> implements Renderable{
	private HashMap<E, Renderable> map = new HashMap<E, Renderable>();
	private boolean visible = true;
	private E current = null;
	
	public Renderable getRenderable(){
		return map.get(current);
	}
	
	public void add(E key, Renderable r){
		map.put(key, r);
	}
	
	public void addAndSet(E key, Renderable r){
		this.add(key, r);
		this.setCurrent(key);
	}
	
	public void setCurrent(E key){
		this.current = key;
	}
	
	@Override
	public void render(int delta) {
		getRenderable().render(delta);
	}

	@Override
	public boolean isVisible() {
		return visible && getRenderable().isVisible();
	}

	@Override
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	
}
