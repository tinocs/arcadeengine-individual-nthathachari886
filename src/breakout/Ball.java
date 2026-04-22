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
		
		dx = 2;
		dy = 2;
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
			if(getX()>p.getX() && getX()<p.getX()+p.getWidth()) {
				if(getY() <= p.getY()) {
					dy = -dy;
					setY(p.getY()-getHeight());
				} else if(getY() > p.getY()) {
					dy = -dy;
					setY(p.getY()+p.getHeight());
				}
			}else if(getY()>p.getY() && getY()<p.getY()+p.getHeight()) {
			
				// bounce off left and right
				if(getX() <= p.getX()) {
					dx = -dx;
					setX(p.getX()-getWidth());
				}else if(getX() >p.getX()) {
					dx = -dx;
					setX(p.getX()+p.getWidth());
				}
			}else {
				dx = -dx;
				dy = -dy;
			}
			
		}
		
		// BRICK
		Brick c = (Brick)getOneIntersectingObject(Brick.class);
		if(c!=null) {
			
			// bounce off top and bottom
			if(getX()>c.getX() && getX()<c.getX()+c.getWidth()) {
				if(getY() <= c.getY()) {
					dy = -dy;
					setY(c.getY()-getHeight());
				} else if(getY() > c.getY()) {
					dy = -dy;
					setY(c.getY()+c.getHeight());
				}
			}else if(getY()>c.getY() && getY()<c.getY()+c.getHeight()) {
			
				// bounce off left and right
				if(getX() <= c.getX()) {
					dx = -dx;
					setX(c.getX()-getWidth());
				}else if(getX() >c.getX()) {
					dx = -dx;
					setX(c.getX()+c.getWidth());
				}
			}else {
				dx = -dx;
				dy = -dy;
			}
			
		}
		
	}

}
