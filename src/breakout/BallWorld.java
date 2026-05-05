package breakout;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import engine.World;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;


public class BallWorld extends World{
	
	private Score score;
	private BooleanProperty gameOver = new SimpleBooleanProperty(false);
	int level = 1;
	
	Stage stage;
	
	Ball b;
	Paddle p;
	
	double spacing = 4;
	final int ORIGINAL_HEIGHT = 700;
	final int ORIGINAL_WIDTH = 500;

	
	public BallWorld(Stage s) {
		super();
		setWidth(500);
		setHeight(700);
		level = 1;
		stage = s;
		//spacing = 10;
	}
	
	public BooleanProperty gameOverProperty() {
	    return gameOver;
	}
	
	@Override
	public void act(long now) {
		if(getObjects(Brick.class).size()==0) {
			//gameOver.set(true);
			
			level++;
			if(level>2) {
				gameOver.set(true);
			}else {
				//System.out.println("OVER");
				setLevel(level);
			}
			
		}
	}
	
	public int getOriginalHeight() {
		return ORIGINAL_HEIGHT;
	}
	
	public int getOriginalWidth() {
		return ORIGINAL_WIDTH;
	}

	@Override
	public void onDimensionsInitialized() {
		b = new Ball();
		add(b);
		b.setX(getWidth()/2);
		b.setY(getHeight()/2);
		
		p = new Paddle();
		add(p);
		p.setX(getWidth()/2);
		p.setY(getHeight()*3/5);

		
		score = new Score();
		score.setX(10);
		score.setY(score.getFont().getSize()+10);
		this.getChildren().add(score);
		
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
		
		setLevel(getLevel());
	}
	
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int l) {
		level = l;
		String filename = "breakout-lvl-"+level+".txt";
		try {
			File f = new File(filename);
			Scanner sc = new Scanner(f);
			
			int rows = sc.nextInt();
			int cols = sc.nextInt();
			
			Brick t = new Brick();
			
			double initX = getWidth()/2 - cols*t.getWidth()/2 - ((cols-1)*spacing)/2;
			double initY = 50;
			
			for(int r=0; r<rows; r++) {
				for(int c=0; c<cols; c++) {
					int i = sc.nextInt();
					if(i==0) {
						continue;
					}else{
						Brick x = new Brick(i);
						add(x);
						x.setX(initX + c*x.getWidth() + c*spacing);
						x.setY(initY + r*x.getHeight() + r*spacing);
						
					}
				}
			}
			
			setHeight(ORIGINAL_HEIGHT);
			setWidth(ORIGINAL_WIDTH);
			
			stage.setHeight(ORIGINAL_HEIGHT);
			stage.setWidth(ORIGINAL_WIDTH);
			
			b.setX(getWidth()/2);
			b.setY(getHeight()/2);
			
			p.setX(getWidth()/2);
			p.setY(getHeight()*3/5);
			
			sc.close();
			
		}catch(IOException e) {
			System.out.println("ERROR: "+e.getMessage());
		}
	}
	
	public Score getScore() {
		return score;
	}

}
 