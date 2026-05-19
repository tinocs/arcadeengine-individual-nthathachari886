package breakout;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import engine.Actor;
import engine.Sound;
import engine.World;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.Stage;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;


public class BallWorld extends World{
	
	private Score score;
	private Lives lives;
	private BooleanProperty gameOver = new SimpleBooleanProperty(false);
	int level = 1;
	
	Stage stage;
	
	Ball b;
	Paddle p;
	
	double spacing = 4;
	final int ORIGINAL_HEIGHT = 700;
	final int ORIGINAL_WIDTH = 500;
	
	
	Sound gameLost = new Sound("/breakoutresources/game_lost.wav");
	Sound gameWon = new Sound("/breakoutresources/game_won.wav");
	
	String path = getClass().getClassLoader().getResource("breakoutresources/background.png").toString();
	Image img = new Image (path);
	
	
	public BallWorld(Stage s) {
		super();
		stage = s;
		setWidth(ORIGINAL_WIDTH);
		setHeight(ORIGINAL_HEIGHT);
		level = 1;
		//spacing = 10;
	}
	
	public BooleanProperty gameOverProperty() {
	    return gameOver;
	}
	
	@Override
	public void act(long now) {
		if(lives.getValue() ==0) {
			if(!gameOver.getValue()) {
				gameLost.play();
			}
			Ball b = getObjects(Ball.class).get(0);
			b.reset();
			b.setMove(0,  0);
			
			gameOver.set(true);
		}
		if(getObjects(Brick.class).size()==0) {
			//gameOver.set(true);
			
			level++;
			if(level>2) {
				if(!gameOver.getValue()) {
					gameWon.play();
				}
				Ball b = getObjects(Ball.class).get(0);
				b.reset();
				b.setMove(0,  0);
				
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
		score = new Score();
		score.setX(10);
		score.setY(score.getFont().getSize()+10);
		this.getChildren().add(score);
		
		lives = new Lives();
		lives.setX(300);
		lives.setY(lives.getFont().getSize()+10);
		this.getChildren().add(lives);
		
		setLevel(getLevel());
		
		/*
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
		*/
		
		//setLevel(getLevel());
		
	}
	
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int l) {
		
		for(int i=0; i<getObjects(Paddle.class).size(); i++) {
			this.remove(getObjects(Paddle.class).get(0));
		}
		
		for(int i=0; i<getObjects(Ball.class).size(); i++) {
			this.remove(getObjects(Ball.class).get(0));
		}
		
		level = l;
		String filename = "breakout-lvl-"+level+".txt";
		try {
			File f = new File(filename);
			Scanner sc = new Scanner(f);
			
			//double paddlePlacement = sc.nextDouble();
			//System.out.println(paddlePlacement);
			
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
			
			//setImage(img);
			BackgroundPosition initPos = new BackgroundPosition(Side.LEFT, - img.getWidth()/2 + getWidth()/2, false, Side.TOP, 0, false);
			BackgroundImage bg = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, initPos, BackgroundSize.DEFAULT);
			this.setBackground(new Background(bg));
			
			
			p = new Paddle();
			add(p);
			p.setX(getWidth()/2);
			p.setY(getHeight()*6/7);
			
			b = new Ball();
			add(b);
			b.setX(getWidth()/2);
			b.setY(p.getY() - b.getHeight());
			b.reset();
			b.setPaused(true);
			
			
			sc.close();
			
		}catch(IOException e) {
			System.out.println("ERROR: "+e.getMessage());
		}
	}
	
	public void scroll(double dx) {
		// For now, only move the background by the OPPOSITE of dx.
		// For example, if dx was 5 then you would move the background by -5.
		
		double xPos = this.getBackground().getImages().get(0).getPosition().getHorizontalPosition();
		System.out.println(xPos);
		if(xPos-dx <0 && xPos> -img.getWidth()+getWidth()+dx) {
			//setImage(img);
			BackgroundPosition initPos = new BackgroundPosition(Side.LEFT, this.getBackground().getImages().get(0).getPosition().getHorizontalPosition() -dx, false, Side.TOP, 0, false);
			BackgroundImage bg = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, initPos, BackgroundSize.DEFAULT);
			this.setBackground(new Background(bg));
			
			for(int i=0; i<getObjects(Actor.class).size(); i++) {
				Actor a = getObjects(Actor.class).get(i);
				a.setX(a.getX()-dx);
			}
		}
	
		
	}

	
	public Score getScore() {
		return score;
	}
	
	public Lives getLives() {
		return lives;
	}

}
 