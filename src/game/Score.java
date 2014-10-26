package game;

import main.RenderableCollection;

public class Score extends RenderableCollection{
	private final Digit tens, ones;
	private final Player owner;
	private int lastScore = -1;
	public Score(Player owner, Digit tens, Digit ones) {
		this.owner=owner;
		this.tens=tens;
		this.ones=ones;
		this.objs.add(tens);
		this.objs.add(ones);
	}
	
	@Override
	public void render(int delta){
		int score = this.owner.score;
		if(score == lastScore){
			int onesdig = score%10;
			int tensdig = (score-onesdig)/10;
			tens.setDigit(tensdig);
			ones.setDigit(onesdig);
			ones.setVisible(true);
			tens.setVisible(tensdig>0);
			lastScore = score;
		}
		super.render(delta);
	}

}
