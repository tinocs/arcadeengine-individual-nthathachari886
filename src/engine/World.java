package engine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public abstract class World extends javafx.scene.layout.Pane{
	
	private AnimationTimer timer;
	private boolean timerIsRunning;
	private Set<KeyCode> keysCurrentlyPressed;
	private boolean widthHasBeenSet;
	private boolean heightHasBeenSet;
	
	public World() {
		widthHasBeenSet = false;
		heightHasBeenSet = false;
		keysCurrentlyPressed = new HashSet<KeyCode>();
		timerIsRunning = false;
		
		widthProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if(newValue.intValue() > 0) {
					widthHasBeenSet = true;
				}
				if(widthHasBeenSet && heightHasBeenSet) {
					onDimensionsInitialized();
				}
			}
			
		});
		
		heightProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if(newValue.intValue() > 0) {
					heightHasBeenSet = true;
				}
				if(widthHasBeenSet && heightHasBeenSet) {
					onDimensionsInitialized();
				}
			}
			
		});
		
		sceneProperty().addListener(new ChangeListener<Scene>() {

			@Override
			public void changed(ObservableValue<? extends Scene> observable, Scene oldValue, Scene newValue) {
				requestFocus();
			}
			
		});
		
		setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent e) {
				keysCurrentlyPressed.add(e.getCode());
				
			}
			
		});
		
		setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent e) {
				keysCurrentlyPressed.remove(e.getCode());
				
			}
			
		});
		
		timer = new AnimationTimer() {

			@Override
			public void handle(long now) {
				act(now);
				
				for(Actor n : getObjects(Actor.class)) {
					if(n.getWorld() != null) {
						act(now);
					}
				}
				
			}
			
		};
	}
	
	public abstract void act(long now);
	
	public void start() {
		timer.start();
		timerIsRunning = true;
	}
	
	public void stop() {
		timer.stop();
		timerIsRunning = false;
	}
	
	public boolean isStopped() {
		return timerIsRunning;
	}
	
	public void add(Actor actor) {
		getChildren().add(actor);
	}
	
	public void remove(Actor actor) {
		getChildren().remove(actor);
	}
	
	public <A extends Actor> java.util.List<A> getObjects(java.lang.Class<A> cls){
		
		List<A> list = new ArrayList<A>();
		
		for(Node n : getChildren()) {
			if(cls.isInstance(n)) {
				list.add(cls.cast(n));
			}
		}
		
		return list;
	}
	
	public <A extends Actor> java.util.List<A> getObjectsAt(double x, double y, java.lang.Class<A> cls){
		List<A> allActors = getObjects(cls);
		List<A> actors = new ArrayList<A>();
		
		for(A a : allActors) {
			if(a.getBoundsInParent().contains(x, y)) {
				actors.add(a);
			}
		}
		
		return actors;
	}
	
	public boolean isKeyPressed(javafx.scene.input.KeyCode code) {
		// TO_DO
		return false;
	}
	
	public abstract void onDimensionsInitialized();

	
}



















