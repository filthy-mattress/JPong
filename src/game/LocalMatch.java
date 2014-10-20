package game;

import geom.Point;

public class LocalMatch extends Match {

	public LocalMatch(Point ballstart) {
		super(ballstart, new LocalPlayer(LocalPlayer.WASD), new LocalPlayer(LocalPlayer.ARROWS));
	}
	public LocalMatch(){
		this(new Point(0,0));
	}

}
