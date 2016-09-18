package MainPackage;

public interface BeatModelInterface {
	// methods the controller will use to direct model based on user interaction
	void initialize(); // called after BeatModel is initialized
	
	void on(); // turn beat generator on/off
	
	void off();
	
	void setBPM(int bpm); // set the BPM and change beat frequency immediately
	
	int getBPM(); //return current BPM or 0 if off
	// two kinds of observers : BeatObserver - notified on every beat, or notified on every BPM change
	void registerObserver (BeatObserver o);
	
	void removeObserver(BeatObserver o);
	// two kinds of observers : BPMObserver - notified on every BPM change
	void registerObserver (BPMObserver o);
	
	void removeObserver(BPMObserver o);
}
