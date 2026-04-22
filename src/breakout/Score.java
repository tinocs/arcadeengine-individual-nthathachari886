package breakout;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Score extends Text{
	
	private int value;
	
	public Score() {
		super();
		value = 0;
		setFont(new Font(20));
		updateDisplay();
	}
	
	public void updateDisplay() {
		setText("Score: "+value);
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int v) {
		value = v;
		updateDisplay();
	}

}
