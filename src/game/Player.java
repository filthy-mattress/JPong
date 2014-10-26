package game;


import geom.Point;

import main.GLColor;

public abstract class Player extends Paddle{
	Match match = null;
	Score scoreboard = new Score(this, new Digit(), new Digit());
	int score = 0;
	
	protected Player(){
		this(new Point(0,0));
	}
	
	public Player(Point start){
		super(start, 0.5);
		this.setBackgroundColor(GLColor.WHITE);
	}
	
	public void render(int delta){
		super.render(delta);
		if(scoreboard!=null){
			scoreboard.render(delta);
		}
	}
	
}
