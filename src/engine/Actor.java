package engine;

import javafx.scene.image.ImageView;

public abstract class Actor extends ImageView{

	public Actor() {
		
	}
	
	public void move (double dx, double dy) {
		setX(getX()+dx);
		setY(getY()+dy);
	}
	
	public World getWorld() {
		return (World)getParent();
	}
	
	public double getWidth() {
		return getBoundsInParent().getWidth();
	}
	
	public double getHeight() {
		return getBoundsInParent().getHeight();
	}
	
	public <A extends Actor> java.util.List<A> getIntersectingObjects(java.lang.Class<A> cls) {
		List a = new 
	}
}
