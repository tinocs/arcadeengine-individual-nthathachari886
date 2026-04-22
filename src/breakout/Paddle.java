package breakout;

import engine.Actor;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Paddle extends Actor{
	
	private int dx;
	
	public Paddle() {
		String path = getClass().getClassLoader().getResource("breakoutresources/paddle.png").toString();
		Image img = new Image (path);
		setImage(img);
		
		dx = 3;
	}

	@Override
	public void act(long now) {
		// Movement on key command
		if(getWorld().isKeyPressed(KeyCode.LEFT)) {
			move(-dx, 0);
		}else if(getWorld().isKeyPressed(KeyCode.RIGHT)) {
			move(dx, 0);
		}
		// staying in bounds
		if(getX()<0) {
			setX(0);
		}
		if(getX()+getWidth()>getWorld().getWidth()) {
			setX(getWorld().getWidth()-getWidth());
		}
	}

}
