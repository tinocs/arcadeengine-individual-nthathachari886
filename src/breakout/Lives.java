package breakout;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Lives extends Text{
	
	private int value;
	
	public Lives() {
		super();
		value = 100;
		setFont(new Font(20));
		updateDisplay();
	}
	
	public void setValue(int v) {
		value = v;
		updateDisplay();
	}
	
	public int getValue() {
		return value;
	}
	
	public void increment() {
		this.value--;
		updateDisplay();
	}
	
	public void updateDisplay() {
		setText("Lives: "+value);
	}
	
	public String toString() {
		return ""+value;
	}
	
}
