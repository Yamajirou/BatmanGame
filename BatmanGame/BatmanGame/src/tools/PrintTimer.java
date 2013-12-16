package tools;

import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class PrintTimer {
	int seg;
	boolean okToPrint = true;
	Timer t;
	String string;
	
	public PrintTimer(){
		seg = -1;
		t = new Timer();
		string = "";
	}
	
	public void print(String oq, int tempo){
		if(okToPrint){
			okToPrint = false;
			string = oq;
			t.scheduleTask(new Task() {
				@Override
				public void run() {
					System.out.println(string);
					okToPrint = true;
				}
			}, tempo);
		}
	}
	
}
