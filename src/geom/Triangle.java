package geom;


import java.util.Arrays;

public class Triangle extends Primitive{
	private Point a,b,c;
	public Triangle(Point a, Point b, Point c){
		this.a = a;
		this.b = b;
		this.c = c;
	}
	public double[] getRadianAngles(){
		double[] res = {0,0,0};
		double[] sides = getSides();
		res[0] = lawOfCosines(sides[0],sides[1],sides[2]);
		res[1] = lawOfCosines(sides[0],sides[2],sides[1]);
		res[2] = Math.PI - res[0] - res[1];
		return res;
	}
	public double[] getDegreeAngles(){
		double[] res = this.getRadianAngles();
		for(int i=0;i<res.length;i++){
			res[i] = Math.toDegrees(res[i]);
		}
		return res;
	}
	public boolean isRight(){
		for(double angle : this.getDegreeAngles()){
			if(angle==90){
				return true;
			}
		}
		return false;
	}
	private static double lawOfCosines(double a, double b, double c){
		return Math.acos((a*a+b*b-c*c)/(2*a*b));
	}
	public double[] getSides(){
		return new double[]{
				a.distTo(b),
				b.distTo(c),
				c.distTo(a)
		};
	}
	public double getArea(){
		double[] sides = getSides();
		double s = 0;
		for(double side : sides){
			s+=side;
		}
		s/=2;
		double res = s;
		for(double side : sides){
			res*=(s-side);
		}
		return Math.sqrt(res);
	}
	public boolean similar(Triangle other){
		if(this.getArea()!=other.getArea()){
			return false;
		}
		double[] mysides = this.getSides();
		double[] theirsides = this.getSides();
		Arrays.sort(mysides);
		Arrays.sort(theirsides);
		for(int i=0;i<mysides.length;i++){
			if(mysides[i]!=theirsides[i]){
				return false;
			}
		}
		return true;
	}
	@Override
	public Point[] getPoints() {
		return new Point[]{a,b,c};
	}
}
