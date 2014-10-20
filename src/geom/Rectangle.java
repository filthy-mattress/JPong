package geom;

import java.awt.geom.Rectangle2D;


public class Rectangle extends Quad {
	public static boolean isRectangle(Point a, Point b, Point c, Point d){
		Triangle trig1 = new Triangle(a,b,c);
		Triangle trig2 = new Triangle(b,c,d);
		return trig1.similar(trig2) && trig1.isRight() && trig2.isRight();
	}
	
	private Point topleft;
	private double width, height;
	
	public Rectangle(double x, double y, double width, double height){
		this(new Point(x,y), width, height);
	}
	public Rectangle(Point topleft, double width, double height){
		super(null,null,null,null);
		this.topleft = topleft;
		this.width = width;
		this.height = height;
		this.fixPoints();
	}
	public Rectangle(Rectangle copy){
		this(copy.topleft.x, copy.topleft.y, copy.width, copy.height);
	}
	public void fixPoints(){
		this.a = topleft;
		this.b = new Point(topleft.x+width,	topleft.y,			topleft.z);
		this.c = new Point(topleft.x+width,	topleft.y-height,	topleft.z);
		this.d = new Point(topleft.x,		topleft.y-height,	topleft.z);
		this.assertRectangularity();
	}
	public void assertRectangularity(){
		assert isRectangle(a,b,c,d);
	}
	private static void warn(){
		System.err.println("Warning: setting individual points in Rectangle does nothing!");
	}
	@Deprecated
	public void setA(Point a){
		warn();
	}
	@Deprecated
	public void setB(Point b){
		warn();
	}
	@Deprecated
	public void setC(Point c){
		warn();
	}
	@Deprecated
	public void setD(Point d){
		warn();
	}
	public Point getTopleft() {
		return topleft;
	}
	public void setTopleft(Point topleft) {
		this.topleft = topleft;
		this.fixPoints();
	}
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
		this.fixPoints();
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
		this.fixPoints();
	}
	public Rectangle2D getRect(){//#rekt
		return new Rectangle2D.Double(topleft.x, topleft.y-height, width, height);
	}
	public boolean intersects(Rectangle other){
		return this.getRect().intersects(other.getRect());
	}
	public Point getCenter(){
		return new Point(topleft.x+(width/2),topleft.y-(height/2), topleft.z);
	}
}
