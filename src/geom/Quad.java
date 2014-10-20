package geom;

import main.GLColor;

public class Quad extends Primitive{
	Point a,b,c,d;
	GLColor lineColor = null;
	GLColor backgroundColor = null;
	boolean visible = true;
	
	public Quad(Point a, Point b, Point c, Point d){
		this.a=a;
		this.b=b;
		this.c=c;
		this.d=d;
	}
	
	public Quad(Point[] pts){
		assert pts.length>=4;
		a=pts[0];
		b=pts[1];
		c=pts[2];
		d=pts[3];
	}
	private static Point[] convert(double[] xs, double[] ys, double[] zs){
		assert xs.length>=4 && ys.length>=4 && zs.length>=4;
		Point[] res = new Point[4];
		for(int i=0;i<4;i++){
			res[i]=new Point(xs[i],ys[i],zs[i]);
		}
		return res;
	}
	public Quad(double[] xs, double[] ys, double[] zs){
		this(convert(xs,ys,zs));
	}
	public Quad(double[] xs, double[] ys){
		this(xs,ys,new double[4]);
	}
	public Quad(Quad copy){
		this(copy.a,copy.b,copy.c,copy.d);
		this.lineColor = new GLColor(copy.lineColor);
		this.backgroundColor = new GLColor(copy.backgroundColor);
		this.visible = copy.visible;
	}

	public Point getA() {
		return a;
	}

	public void setA(Point a) {
		this.a = a;
	}

	public Point getB() {
		return b;
	}

	public void setB(Point b) {
		this.b = b;
	}

	public Point getC() {
		return c;
	}

	public void setC(Point c) {
		this.c = c;
	}

	public Point getD() {
		return d;
	}

	public void setD(Point d) {
		this.d = d;
	}

	@Override
	public Point[] getPoints() {
		return new Point[]{a,b,c,d};
	}

}
