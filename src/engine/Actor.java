package engine;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.ImageView;

public abstract class Actor extends ImageView{

	public Actor() {
		
	}
	
	public abstract void act(long now);
	
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
		List<A> possibleActors = getWorld().getObjects(cls);
		List<A> touchingActors = new ArrayList<A>();
		
		for(A obj : possibleActors) {
			if(obj!=this && obj.getBoundsInParent().intersects(this.getBoundsInParent())) {
				touchingActors.add(obj);
			}
		}
		
		return touchingActors;
	}
	
	public <A extends Actor> A getOneIntersectingObject(java.lang.Class<A> cls) {
		List<A> allActors = getIntersectingObjects(cls);
		if(allActors.size()>0) {
			return allActors.get(0);
		}
		return null;
	}
	
	public void addedToWorld() {
		
	}
}
