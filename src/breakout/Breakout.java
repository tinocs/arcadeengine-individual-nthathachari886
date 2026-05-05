package breakout;

import java.io.File;
import java.util.Scanner;

import engine.World;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Breakout extends Application{
	
	static Stage s;
	Scene h;
	BallWorld world;
	Button play;
	
	//int level = 0;	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		// STAGE SETUP
		s = stage;
		s.setTitle("Breakout");
		
		// LEVEL
		world = new BallWorld(s);
		BorderPane root = new BorderPane();
		root.setCenter(world);
				
		h = goToHomeScreen(world);
		s.setScene(h);
		
		s.show();
	}
	
	
	public Scene goToHomeScreen(BallWorld world) {
		// SETUP
		s.setHeight(world.getOriginalHeight());
		s.setWidth(world.getOriginalWidth());
		
		// TITLE
		VBox screen = new VBox();
		
		ImageView t = new ImageView();
		String path = getClass().getClassLoader().getResource("breakoutresources/title.png").toString();
		Image img = new Image (path);
		t.setImage(img);
		
		// PLAY BUTTON
		MyButtonHandler buttonHandler = new MyButtonHandler();
		play = new Button("PLAY");
		play.setOnAction(buttonHandler);
		
		// ADDING
		screen.getChildren().addAll(t, play);
		
		Scene home = new Scene(screen, world.getWidth(), world.getHeight());
		screen.setAlignment(Pos.TOP_CENTER);
		
		return home;
	}
	
	/*
	public void loadLevel() {
		world.setLevel(level);
	}
	*/

	
	private class MyButtonHandler implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent e) {
			if(e.getSource() == play) {
				BorderPane root = new BorderPane();
				world = new BallWorld(s);
				world.gameOverProperty().addListener((obs, oldVal, newVal) -> {
				    if (newVal) {
				        s.setScene(goToHomeScreen(world));
				    	//world.setLevel(world.getLevel()+1);
				    }
				});
				
				root.setCenter(world);
				
				Scene scene = new Scene(root, world.getWidth(), world.getHeight());
				
				s.setScene(scene);
				world.start();
			}
			
		}
		
	}
	
	private class MyPlayListener implements ChangeListener<BooleanProperty>{


		@Override
		public void changed(ObservableValue<? extends BooleanProperty> observable, BooleanProperty oldValue,
				BooleanProperty newValue) {
			if(newValue.getValue()) {				
				s.setScene(h);
				world.start();
			}
			
		}

	}

}
