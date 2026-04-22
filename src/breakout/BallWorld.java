package breakout;

import engine.World;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class BallWorld extends World{
	
	private Score score;
	
	public BallWorld() {
		super();
		setWidth(500);
		setHeight(700);
	}
	
	@Override
	public void act(long now) {
		
	}

	@Override
	public void onDimensionsInitialized() {
		Ball b = new Ball();
		add(b);
		//System.out.println(getWidth()/2+", "+getHeight()/2);
		b.setX(getWidth()/2);
		b.setY(getHeight()/2);
		//System.out.println(b.getX()+", "+b.getY());
		
		Paddle p = new Paddle();
		add(p);
		p.setX(getWidth()/2);
		p.setY(getHeight()*3/5);
		
		// Brick Testing
		int rMax = 6;
		int cMax = 10;
		int space = 2;
		for(int r=0; r<rMax; r++) {
			for(int c=0; c<cMax; c++) {
				System.out.println(c);
				Brick x = new Brick();
				add(x);
				x.setX(getWidth()/2.0-(cMax*x.getWidth()+(cMax-1)*space)/2.0 + r*x.getWidth()+r*space);
				x.setY(getHeight()/4.0-(rMax*x.getHeight()+(rMax-1)*space)/2.0 + c*x.getHeight()+c*space);
				System.out.println(cMax*r+c);
			}
		}
		
		score = new Score();
		score.setX(10);
		score.setY(score.getFont().getSize()+10);
		this.getChildren().add(score);
		
		this.setOnMouseMoved(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				p.setX(event.getX()-p.getWidth()/2);
				if(p.getX()<0) {
					p.setX(0);
				}
				if(p.getX()+p.getWidth()>getWidth()) {
					p.setX(getWidth()-p.getWidth());
				}
			}
		});
		
		
	}
	
	public Score getScore() {
		return score;
	}

}
 