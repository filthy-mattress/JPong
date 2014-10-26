package game;

import geom.Point;
import geom.Rectangle;
import main.GLColor;

public abstract class Paddle extends Rectangle {
	public static final double PADDLE_WIDTH = 0.05;
	public static final double DEFAULT_MOVE_SPEED = 0.75;
	
	double moveSpeed = DEFAULT_MOVE_SPEED; 
	
	public Paddle(Point topleft, double length){
		super(topleft, PADDLE_WIDTH, length);
		this.setBackgroundColor(GLColor.WHITE);
	}
	public Paddle(Point topleft, double length, double moveSpeed){
		this(topleft, length);
		this.moveSpeed = moveSpeed;
	}
	
	/**
	 * Gets the current move speed based on the keyboard.
	 * @return moveSpeed, -moveSpeed, or zero depending on keyboard input
	 */
	public double currMoveSpeed(){
		if(this.isGoingUp()){
			return moveSpeed;
		}else if(this.isGoingDown()){
			return -moveSpeed;
		}else{
			return 0;
		}
	}
	
	/**
	 * Updates the position of the paddle based on the keyboard input, the move speed, and the delta.
	 */
	public void update(int delta){
		double secs = delta/1000.0;
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
	public double getMoveSpeed() {
		return moveSpeed;
	}
	public void setMoveSpeed(double moveSpeed) {
		this.moveSpeed = moveSpeed;
	}
}
