package game;

import java.util.HashSet;
import java.util.Set;

import geom.Point;
import geom.Primitive;
import geom.Rectangle;

public class Digit extends Primitive {
	private static double[] fracs(double d){
		double[] res = new double[(int) (d+1)];
		for(int i=0;i<d;i++){
			res[i]=i/d;
		}
		return res;
	}
	private static final double[] FIFTHS = fracs(5);
	private static final double[] THIRDS = fracs(3);
	/* 
	 *   +-0-+
	 *   |   |
	 *   1   2
	 *   |   |
	 *   +-3-+
	 *   |   |
	 *   4   5
	 *   |   |
	 *   +-6-+
	 * 
	 */
	private static Rectangle[] genSegs(){
		Rectangle horizontal = new Rectangle(0,0,1,FIFTHS[1]);
		Rectangle vertical = new Rectangle(0,0,THIRDS[1],FIFTHS[3]);
		Rectangle[] res = new Rectangle[7];
		for(int i=0;i<res.length;i++){
			double ydiff = FIFTHS[2*(i/3)];
			if(i%3==0){
				res[i]=new Rectangle(horizontal);
				Point tl = res[i].getTopleft();
				res[i].setTopleft(new Point(tl.x, tl.y-ydiff, tl.z));
			}else{
				res[i]=new Rectangle(vertical);
				final Point tl = res[i].getTopleft();
				double x=tl.x, y=tl.y, z=tl.z;
				y-=ydiff;
				if((i-1)%3!=0){
					x+=THIRDS[2];
				}
				res[i].setTopleft(new Point(x,y,z));
			}
		}
		return res;
	}
	private static final Rectangle[] SEGS = genSegs();
	private static boolean[] makeLine(String s){
		boolean[] res = new boolean[s.length()];
		int i=0;
		for(char c: s.toCharArray()){
			if(c=='1'){
				res[i]=true;
			}
			i++;
		}
		return res;
	}
	private static final boolean[][] DECODER = {
		makeLine("1110111"),// 0
		makeLine("0010010"),// 1
		makeLine("1011101"),// 2
		makeLine("1011011"),// 3
		makeLine("0111010"),// 4
		makeLine("1101011"),// 5
		makeLine("1101111"),// 6
		makeLine("1010010"),// 7
		makeLine("1111111"),// 8
		makeLine("1111010"),// 9
	};
	
	public int digit;
	public double width, height;
	
	public Digit(){
		this(0);
	}
	
	public Digit(int digit){
		this(digit, 1.0, 1.5);
	}
	
	public Digit(int digit, double width, double height){
		assert digit>=0 && digit<10;
		this.digit = digit;
	}

	@Override
	public Point[] getPoints() {
		Set<Point> points = new HashSet<Point>();
		int i = 0;
		for(boolean b: DECODER[digit]){
			if(b){
				Rectangle r = SEGS[i];
				r.setWidth(width);
				
				for(Point p : SEGS[i].getPoints()){
					points.add(p);
				}
			}
			i++;
		}
		return points.toArray(new Point[points.size()]);
	}

}
