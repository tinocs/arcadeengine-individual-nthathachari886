package breakout;


import engine.Actor;
import engine.Sound;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;


public class Ball extends Actor{
	
	private double dx;
	private double dy;
	
	private final double DX = 7;
	private final double DY = -7;
	
	private boolean isPaused;
	
	Sound ballBounce = new Sound("/breakoutresources/ball_bounce.wav");
	Sound brickHit = new Sound("/breakoutresources/brick_hit.wav");
	Sound loseLife = new Sound("/breakoutresources/lose_life.wav");
	
	
	public Ball() {
		String path = getClass().getClassLoader().getResource("breakoutresources/ball.png").toString();
		Image img = new Image (path);
		setImage(img);
		
		dx = 0;
		dy = 0;
		
		isPaused = true;
	}

	@Override
	public void act(long now) {
		if(isPaused) {
			reset();
			if(getWorld().isKeyPressed(KeyCode.SPACE)) {
				isPaused = false;
				if(isPaused) {
					dx = 0;
					dy = 0;
				}else {
					dx = DX;
					dy = DY;
				}
			}
		}
		
		move(dx, dy);
		
		// 		COLLISIONS
		
		// WORLD
		// bounce off top and bottom
		if(getY() <=0) {
			dy = -dy;
			setY(0);
			ballBounce.play();
		} else if(getY()+getHeight() >= getWorld().getHeight()) {
			dy = -dy;
			setY(getWorld().getHeight()-getHeight());
			BallWorld bw = (BallWorld)getWorld();
			bw.getLives().increment();
			loseLife.play();
		}
		
		// bounce off left and right
		if(getX() <=0) {
			dx = -dx;
			setX(0);
			ballBounce.play();
		}else if(getX()+getWidth() >= getWorld().getWidth()) {
			dx = -dx;
			setX(getWorld().getWidth()-getWidth());
			ballBounce.play();
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
			BallWorld bw = (BallWorld)getWorld();
			bw.getScore().setValue(bw.getScore().getValue()+100);
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
			brickHit.play();
			FadeTransition fd = new FadeTransition(Duration.millis(300));
			fd.setNode(c);
			fd.setFromValue(1.0);
		    fd.setToValue(0);
		    //fd.setCycleCount();
			fd.play();
			fd.setOnFinished(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					getWorld().remove(c);
				}
			});
			
		}
		
	}
	
	public void setPaused(boolean x) {
		isPaused = x;
	}
	
	public boolean getPaused() {
		return isPaused;
	}
	
	public void reset() {
		BallWorld bw = (BallWorld)getWorld();
		Paddle p = bw.getObjects(Paddle.class).get(0);
		setX( p.getX() + p.getWidth()/2 - getWidth()/2);
		setY(p.getY()-getHeight());
		dx = 0;
		dy = 0;
	}
	
	public void setMove(double x, double y) {
		dx = x;
		dy = y;
	}
	
	public double getDx() {
		return dx;
	}
	
	public double getDy() {
		return dy;
	}


}
