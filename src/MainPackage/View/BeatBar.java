package MainPackage.View;

import javax.swing.JProgressBar;

public class BeatBar extends JProgressBar implements Runnable {
	// used during deserialization to verify that the sender and receiver of a serialized object have loaded classes for that object 
	// that are compatible with respect to serialization
	// If the receiver has loaded a class for the object that has a different serialVersionUID than that of the corresponding sender's class, 
	// then deserialization will result in an InvalidClassException. A serializable class can declare its own serialVersionUID explicitly 
	// by declaring a field named "serialVersionUID" 
	private static final long serialVersionUID = 2L;
	
	JProgressBar progressBar;
	Thread thread;
	
	public BeatBar(){
		// create a new thread that will handle updating the progress bar
		// set the maximum for progress bar
		
		thread = new Thread(this);
		setMaximum(100);
		thread.start();
	}

	// Implemented from Runnable, run is called in a separate thread and called in that separately executing thread
	// set to run every beat to update bar
	@Override
	public void run() {
		// TODO Auto-generated method stub
		for (;;){
			// decrement the value bar by .25 every 50 ms and repaint to display changes in model
			int value = getValue();
			value = (int)(value * .75);
			setValue(value);
			try{
				Thread.sleep(50);
			}catch(Exception e){
				e.printStackTrace();
			}
		}

	}

}
