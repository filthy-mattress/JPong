package main;

import java.util.ArrayList;
import java.util.List;

public class RenderableCollection implements Renderable {
	protected final List<Renderable> objs;
	private boolean visible = true;
	
	public RenderableCollection(List<Renderable> objs){
		this.objs=objs;
	}
	
	public void addItem(Renderable r){
		this.objs.add(r);
	}
	
	public RenderableCollection(){
		this(new ArrayList<Renderable>());
	}
	
	@Override
	public void render(int delta) {
		for(Renderable r : objs){
			if(r.isVisible())
				r.render(delta);
		}
	}

	@Override
	public boolean isVisible() {
		if(!visible)
			return false;
		for(Renderable r : objs){
			if(r.isVisible()){
				return true;
			}
		}
		return true;
	}

	@Override
	public void setVisible(boolean visible) {
		this.visible=visible;
	}

}
