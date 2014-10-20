package game;


import geom.Point;

import main.GLColor;

public abstract class Player extends Paddle{
	Match match = null;
	int score;
	
	protected Player(){
		this(new Point(0,0));
	}
	
	public Player(Point start){
		super(start, 0.5);
		this.setBackgroundColor(GLColor.WHITE);
		
	}
	
}
