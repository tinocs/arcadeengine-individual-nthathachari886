package breakout;

import engine.World;

public class BallWorld extends World{
	
	public BallWorld() {
		super();
		setWidth(300);
		setHeight(500);
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
	}

}
 