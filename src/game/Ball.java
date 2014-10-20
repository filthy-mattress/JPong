package game;

import java.awt.geom.Rectangle2D;

import geom.Point;
import geom.Rectangle;

public class Ball extends Rectangle {
	public static final double BALL_SIZE = 0.05;
	final Match owner;
	double xVelocity = 0.05;
	double yVelocity = 0.05;
	
	public Ball(Match owner, Point topleft){
		super(topleft, BALL_SIZE, BALL_SIZE);
		this.owner = owner;
	}
	
	private void move(int delta){
		double secs = delta/1000.0;
		Point topleft = this.getTopleft();
		double x = topleft.x, y=topleft.y, z=topleft.z;
		x+= this.xVelocity * secs;
		y+= this.yVelocity * secs;
		if(y>=1 || y-this.getHeight()<=-1){
			yVelocity*=-1;
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
	
	public void update(int delta){
		move(delta);
		checkPaddles(delta);
		final double x = this.getTopleft().x;
		if(x<=-1){
			owner.right.score++;
			owner.resetBall();
		}else if(x+this.getWidth()>=1){
			owner.left.score++;
			owner.resetBall();
		}
	}
}
