package breakout;

import engine.World;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class BallWorld extends World{
	
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

}
 