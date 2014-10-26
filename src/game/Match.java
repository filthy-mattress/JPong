package game;

import main.GLColor;
import main.RenderableCollection;
import geom.Point;

public abstract class Match extends RenderableCollection{
	public static final double XSPEED = 0.75;
	public static final double SCOREBOARD_WIDTHS = 0.25;
	public static final double SCOREBOARD_HEIGHTS = SCOREBOARD_WIDTHS*1.5;
	Player left;
	Player right;
	Ball ball;
	Point ballstart;
	protected Match(Player left, Player right){
		this(new Point(0,0),left,right);
	}
	protected Match(Point ballstart, Player left, Player right){
		this.ballstart = ballstart;
		this.ball=new Ball(this, new Point(ballstart));
		this.resetBall();
		this.ball.setBackgroundColor(GLColor.WHITE);
		this.left = left;
		this.right = right;
		this.objs.add(left);
		this.objs.add(right);
		this.objs.add(ball);
		this.left.setTopleft(new Point(-0.9,left.getHeight()/2));
		this.right.setTopleft(new Point(0.9-right.getWidth(),right.getHeight()/2));
		this.left.scoreboard.setSizes(SCOREBOARD_WIDTHS, SCOREBOARD_HEIGHTS);
		this.right.scoreboard.setSizes(SCOREBOARD_WIDTHS, SCOREBOARD_HEIGHTS);
		this.left.scoreboard.setTopleft(new Point(-0.75, 0.9));
		this.right.scoreboard.setTopleft(new Point(0.5-SCOREBOARD_WIDTHS, 0.9));
	}
	private static int genSign(){
		if(Math.random()<0.5){
			return -1;
		}else{
			return 1;
		}
	}
	public void resetBall(){
		this.ball.setTopleft(new Point(ballstart));
		this.ball.xVelocity=genSign()*XSPEED;
		this.ball.yVelocity=genSign()*this.ball.xVelocity*Math.random();
		this.ball.xAcceleration = 0;
		this.ball.yAcceleration = 0;
//		System.out.println("Ball reset!");
	}
}
