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
		
		dx = 4;
		dy = 4;
		System.out.println(getWidth()+", "+getHeight());
	}

	@Override
	public void act(long now) {
		move(dx, dy);
		
		// 		COLLISIONS
		
		// WORLD
		// bounce off top and bottom
		if(getY() <=0) {
			dy = -dy;
			setY(0);
		} else if(getY()+getHeight() >= getWorld().getHeight()) {
			dy = -dy;
			setY(getWorld().getHeight()-getHeight());
		}
		
		// bounce off left and right
		if(getX() <=0) {
			dx = -dx;
			setX(0);
		}else if(getX()+getWidth() >= getWorld().getWidth()) {
			dx = -dx;
			setX(getWorld().getWidth()-getWidth());
		}
		
		// PADDLE
		Paddle p = (Paddle)getOneIntersectingObject(Paddle.class);
		if(p!=null) {
			
			// bounce off top and bottom
			if(getY() <= p.getY()) {
				dy = -dy;
				setY(p.getY()-getHeight());
			} else if(getY() > p.getY()) {
				dy = -dy;
				setY(p.getY()+p.getHeight());
			}
			
			// bounce off left and right
			if(getX() <= p.getX()) {
				dx = -dx;
				setX(p.getX()-getWidth());
			}else if(getX() >p.getX()) {
				dx = -dx;
				setX(p.getX()+p.getWidth());
			}
			
		}
		
	}

}
