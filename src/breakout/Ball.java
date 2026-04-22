package breakout;

import engine.Actor;
import javafx.scene.image.Image;

public class Ball extends Actor{
	
	private double dx;
	private double dy;
	
	public Ball() {
		String path = getClass().getClassLoader().getResource("breakoutresources/ball.png").toString();
		Image img = new Image (path);
		setImage(img);
		
		dx = 5;
		dy = 5;
	}

	@Override
	public void act(long now) {
		move(dx, dy);
		
		// bounce off top and bottom
		if(getY()-getHeight()/2 <=0 || getY()+getHeight()/2 >= getWorld().getHeight()) {
			dy = -dy;
		}
		
		// bounce off left and right
		if(getX()-getWidth()/2 <=0 || getX()+getWidth()/2 >= getWorld().getWidth()) {
			dx = -dx;
		}
	}

}
