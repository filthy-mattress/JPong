package game;

import main.GLColor;
import main.RenderableCollection;
import geom.Point;
import geom.Rectangle;

public class Digit extends RenderableCollection {
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
	 *   +--0--+
	 *   |     |
	 *   1     2
	 *   |     |
	 *   +--3--+
	 *   |     |
	 *   4     5
	 *   |     |
	 *   +--6--+
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
	
	private int digit;
	private boolean dirty = true;
	private Point topleft = new Point(0,0);
	private double width, height;
	private GLColor backgroundColor = GLColor.WHITE;
	
	public Digit(){
		this(0);
	}
	
	public Digit(int digit){
		this(digit, 1.0, 1.5);
	}
	
	public Digit(int digit, double width, double height){
		assert digit>=0 && digit<DECODER.length;
		this.digit = digit;
	}

	@Override
	public void render(int delta){
		if(!this.dirty){
			super.render(delta);
			return;
		}
		this.objs.removeAll(this.objs);
		assert this.objs.isEmpty();
		for(int i=0; i<DECODER[digit].length; i++){
			if(DECODER[digit][i]){
				Rectangle r = new Rectangle(SEGS[i]);
				r.setLineColor(null);
				r.setBackgroundColor(backgroundColor);
				r.setWidth(r.getWidth()*width);
				r.setHeight(r.getHeight()*height);
				r.setTopleft(r.getTopleft().add(topleft));
				this.objs.add(r);
			}
		}
		
		super.render(delta);
		this.dirty=false;
	}

	public int getDigit() {
		return digit;
	}

	public void setDigit(int digit) {
		this.digit = digit;
		this.dirty=true;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
		this.dirty=true;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
		this.dirty=true;
	}

	public GLColor getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(GLColor backgroundColor) {
		this.backgroundColor = backgroundColor;
		this.dirty=true;
	}

	public Point getTopleft() {
		return topleft;
	}

	public void setTopleft(Point topleft) {
		this.topleft = topleft;
		this.dirty=true;
	}

	public boolean isDirty() {
		return dirty;
	}
}
