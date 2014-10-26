package game;

import geom.Point;
import main.RenderableCollection;

public class Score extends RenderableCollection{
	public static final double SPACER_SIZE = 0.01; 
	private final Digit[] digits;
	private final Player owner;
	private int lastScore = -1;
	private boolean showLeadingZeroes = true;
	private boolean dirty = true;
	
	public Score(Player owner, int digitcount){
		this(owner, digitcount,0);
	}
	public Score(Player owner, int digitcount, int fillwith){
		assert digitcount>0;
		this.owner=owner;
		this.digits=new Digit[digitcount];
		for(int i=0;i<digitcount;i++){
			this.digits[i]=new Digit(fillwith);
		}
		this.fixToplefts();
	}
	
	@Override
	public void render(int delta){
		int score = this.owner.score;
		if(score != lastScore || dirty){
			lastScore = score;
			this.objs.removeAll(this.objs);
			for(int i=digits.length-1;i>=0;i--){
				int dig = score%10;
				digits[i].setDigit(dig);
				score/=10;
				digits[i].setVisible(this.showLeadingZeroes || (dig>0));
				this.objs.add(digits[i]);
			}
			dirty=false;
		}
		super.render(delta);
	}
	public void setWidths(double width){
		for(Digit d: digits){
			d.setWidth(width);
		}
		this.fixToplefts();
	}
	private void fixToplefts(){
		Digit last = digits[0];
		for(int i=1;i<digits.length;i++){
			digits[i].setTopleft(last.getTopleft().add(last.getWidth()+SPACER_SIZE,0));
			last = digits[i];
		}
	}
	public void setTopleft(Point topleft){
		digits[0].setTopleft(topleft);
		this.fixToplefts();
	}
	public void setHeights(double height){
		for(Digit d: digits){
			d.setHeight(height);
		}
	}
	public void setSizes(double width, double height){
		this.setWidths(width);
		this.setHeights(height);
	}
	public double getWidth(){
		double res = digits[0].getWidth();
		for(int i=1;i<digits.length;i++){
			res+=SPACER_SIZE+digits[i].getWidth();
		}
		return res;
	}
	public boolean doesShowLeadingZeroes() {
		return showLeadingZeroes;
	}
	public void setShowLeadingZeroes(boolean showLeadingZeroes) {
		this.showLeadingZeroes = showLeadingZeroes;
		this.dirty = true;
	}
}
