package game;

import geom.Point;
import geom.Rectangle;
import main.GLColor;
import main.Game;

public abstract class Paddle extends Rectangle {
	public static final double PADDLE_WIDTH = 0.05;
	public static final double DEFAULT_MOVE_SPEED = 0.75;
	
	double upSpeed = DEFAULT_MOVE_SPEED; 
	double downSpeed = DEFAULT_MOVE_SPEED;
	
	public Paddle(Point topleft, double length){
		super(topleft, PADDLE_WIDTH, length);
		this.setBackgroundColor(GLColor.WHITE);
	}
	public Paddle(Point topleft, double length, double moveSpeed){
		this(topleft, length);
		this.setMoveSpeed(moveSpeed);
	}
	
	/**
	 * Gets the current move speed based on the keyboard.
	 * @return moveSpeed, -moveSpeed, or zero depending on keyboard input
	 */
	public double currMoveSpeed(){
		final boolean up=this.isGoingUp(), down=this.isGoingDown();
		double res = 0;
		if(up){
			res+= upSpeed;
		}
		if(down){
			res-= downSpeed;
		}
		return res;
	}
	
	/**
	 * Updates the position of the paddle based on the keyboard input, the move speed, and the delta.
	 */
	public void update(int delta){
		double secs = Game.millisToSecs(delta);
		double dy = currMoveSpeed() * secs;
		Point topleft = this.getTopleft();
		double x = topleft.x, y=topleft.y, z=topleft.z;
		y+=dy;
		if(y>1){
			y=1;
		}else if(y-this.getHeight()<-1){
			y=-1+this.getHeight();
		}
		this.setTopleft(new Point(x,y,z));
	}
	public abstract boolean isGoingUp();
	public abstract boolean isGoingDown();
	
	public void setMoveSpeed(double moveSpeed) {
		this.upSpeed = this.downSpeed = Math.abs(moveSpeed);
	}
	public double getUpSpeed() {
		return upSpeed;
	}
	public void setUpSpeed(double upSpeed) {
		this.upSpeed = upSpeed;
	}
	public double getDownSpeed() {
		return downSpeed;
	}
	public void setDownSpeed(double downSpeed) {
		this.downSpeed = downSpeed;
	}
}
