package geom;

import org.lwjgl.opengl.GL11;

public class Point {
	public final double x,y,z;
	public Point(double x, double y, double z){
		this.x=x;
		this.y=y;
		this.z=z;
	}
	public Point(double x, double y){
		this(x,y,0);
	}
	public Point(Point copy){
		this(copy.x,copy.y,copy.z);
	}
	public void activate(){
		GL11.glVertex2d(x, y);
	}
	public double distTo(Point other){
		return Math.sqrt(sqr(x-other.x)+sqr(y-other.y)+sqr(z-other.z));
	}
	private static double sqr(double d){
		return d*d;
	}
	public String toString(){
		return "("+x+','+y+','+z+')';
	}
	public boolean equals(Point other){
		return this.x==other.x && this.y==other.y && this.z == other.z;
	}
	public boolean equals(Object other){
		if(other instanceof Point){
			return this.equals((Point) other);
		}
		return super.equals(other);
	}
	public Point setX(double x){
		return new Point(x,y,z);
	}
	public Point setY(double y){
		return new Point(x,y,z);
	}
	public Point setZ(double z){
		return new Point(x,y,z);
	}
	public Point add(Point other){
		return this.add(other.x, other.y, other.z);
	}
	public Point add(double x, double y){
		return this.add(x, y, 0);
	}
	public Point add(double x, double y, double z){
		return new Point(this.x+x,this.y+y, this.z+z);
	}
}
