package game;

import java.awt.geom.Rectangle2D;

import main.Game;
import geom.Point;
import geom.Rectangle;

public class Ball extends Rectangle {
	public static final double DEFAULT_X_SPEED = 0.05;
	public static final double DEFAULT_Y_SPEED = 0.05;
	public static final double BALL_SIZE = 0.05;
	final Match owner;
	double xVelocity = DEFAULT_X_SPEED;
	double yVelocity = DEFAULT_Y_SPEED;
	double xAcceleration = 0;
	double yAcceleration = 0;
	
	public Ball(Match owner, Point topleft){
		super(topleft, BALL_SIZE, BALL_SIZE);
		this.owner = owner;
	}
	
	private void move(int delta){
		double secs = Game.millisToSecs(delta);
		Point topleft = this.getTopleft();
		double x = topleft.x, y=topleft.y, z=topleft.z;
		x+= this.xVelocity * secs;
		y+= this.yVelocity * secs;
		boolean hitbot = y-this.getHeight()<=-1;
		if(y>=1 || hitbot){
			yVelocity*=-1;
			if(hitbot){
				y=-1+this.getHeight();
			}else{
				y=1;
			}
		}
		this.setTopleft(new Point(x,y,z));
	}
	
	private void checkPaddles(int delta){
		Point topleft = this.getTopleft();
		double x = topleft.x, y=topleft.y, z=topleft.z;
		Rectangle2D myRect = this.getRect();
		boolean hitleft = myRect.intersects(owner.left.getRect());
		boolean hitright = myRect.intersects(owner.right.getRect());
		if(hitleft || hitright){
			Point myCenter = this.getCenter(), other=null;
			xVelocity*=-1;
			if(hitleft){
				other = owner.left.getCenter();
				x = owner.left.getTopleft().x + owner.left.getWidth();
			}else if(hitright){
				other = owner.right.getCenter();
				x = owner.right.getTopleft().x - this.getWidth();
			}
//			yVelocity+=Math.sin(myCenter.y-other.y);
			yVelocity+=myCenter.y-other.y;
		}
		this.setTopleft(new Point(x,y,z));
	}
	
	private void checkGoals(){
		final double x = this.getTopleft().x;
		if(x<=-1){
			owner.right.score++;
			owner.resetBall();
		}else if(x+this.getWidth()>=1){
			owner.left.score++;
			owner.resetBall();
		}
	}
	
	private void accelerate(int delta){
		double secs = Game.millisToSecs(delta);
		this.xVelocity += this.xAcceleration * secs;
		this.yVelocity += this.yAcceleration * secs;
	}
	
	public void update(int delta){
		accelerate(delta);
		move(delta);
		checkPaddles(delta);
		checkGoals();
	}
}
