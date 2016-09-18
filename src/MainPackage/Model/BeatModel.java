package MainPackage.Model;

import java.util.ArrayList;

import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public class BeatModel implements BeatModelInterface, MetaEventListener {
	//Sequencer object generates real beats
	private Sequencer sequencer;
	private ArrayList<BeatObserver> beatObservers = new ArrayList<BeatObserver>();
	private ArrayList<BPMObserver> bpmObservers = new ArrayList<BPMObserver>();
	private int bpm = 90;
	private Sequence sequence;
	private Track track;
	
	@Override
	public void meta(MetaMessage arg0) {
		// Start the sequence again and send notification to beat observers
		if (arg0.getType() == 47){
			beatEvent();
			this.sequencer.start();
			setBPM(getBPM());
		}
	}

	@Override
	public void initialize() {
		//setup sequencer and the beat tracks
		setUpMidi();
		buildTrackAndStart();
	}
	
	public void setUpMidi() {
		try{
			sequencer = MidiSystem.getSequencer();
			sequencer.open();
			sequencer.addMetaEventListener(this);
			sequence = new Sequence(Sequence.PPQ, 4);
			track = sequence.createTrack();
			sequencer.setTempoInBPM(getBPM());
			// added setSequence to fix IllegalStateException
			sequencer.setSequence(sequence);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void buildTrackAndStart() {
		// TODO Auto-generated method stub
		int[] trackList = {35, 0, 46, 0};
		
		sequence.deleteTrack(null);
		track = sequence.createTrack();
		
		makeTracks(trackList);
		track.add(makeEvent(192,9,1,0,4));
	}

	@Override
	public void on() {
		// start the sequencer and set the bpm, default: 90
		sequencer.start();
		setBPM(90);
	}

	@Override
	public void off() {
		// stop the sequence and set bpm to 0
		this.sequencer.stop();
		setBPM(0);
	}

	@Override
	public void setBPM(int bpm) {
		// change bpm and set sequencer to new bpm, notify observers
		this.bpm = bpm;
		this.sequencer.setTempoInBPM(getBPM());
		notifyBPMObservers();
	}

	@Override
	public int getBPM() {
		return this.bpm;
	}

	@Override
	public void registerObserver(BeatObserver o) {
		beatObservers.add(o);

	}

	@Override
	public void removeObserver(BeatObserver o) {
		beatObservers.remove(o);
	}

	@Override
	public void registerObserver(BPMObserver o) {
		bpmObservers.add(o);
	}

	@Override
	public void removeObserver(BPMObserver o) {
		bpmObservers.remove(o);
	}
	
	void beatEvent(){
		notifyBeatObservers();
	}
	
	public void notifyBeatObservers(){
		//loop through BeatObservers and call updateBeat()
		for (int i=0; i<beatObservers.size(); i++){
			BeatObserver obs = (BeatObserver) beatObservers.get(i);
			obs.updateBeat();
		}
	}
	
	public void notifyBPMObservers() {
		// loop through BPM observers and call updateBPM()
		for (int i=0; i<bpmObservers.size(); i++){
			BPMObserver obs = (BPMObserver) bpmObservers.get(i);
			obs.updateBPM();
		}
		
	}

	public void makeTracks(int[] trackList) {
		// TODO Auto-generated method stub
		for (int i=0; i<trackList.length; i++){
			int key = trackList[i];
			
			if (key != 0){
				track.add(makeEvent(144, 9, key, 100, i));
				track.add(makeEvent(128, 9, key, 100, i+1));
			}
		}
	}
	
	public MidiEvent makeEvent(int i, int j, int k, int l, int m) {
		// TODO Auto-generated method stub
		MidiEvent event = null;
		try{
			ShortMessage a = new ShortMessage();
			a.setMessage(i, j, k, l);
			event = new MidiEvent(a, m);
		}catch(Exception e){
			e.printStackTrace();
		}
		return event;
	}


}
