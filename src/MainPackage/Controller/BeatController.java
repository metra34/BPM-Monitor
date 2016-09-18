package MainPackage.Controller;

import MainPackage.Model.BeatModelInterface;
import MainPackage.View.DJView;

public class BeatController implements ControllerInterface {
	// no event listeners in controller, all are in view and controller is called by view
	// controller creates and holds on to the view
	BeatModelInterface model;
	DJView view;
	
	public BeatController(BeatModelInterface model){
		this.model = model;
		// initialize view
		view = new DJView(this, model);
		view.createView();
		view.createControls();
		view.disableStopMenuItem();
		view.enableStartMenuItem();
		// initialize model
		model.initialize();
	}

	@Override
	public void start() {
		model.on();
		view.disableStartMenuItem();
		view.enableStopMenuItem();
	}

	@Override
	public void stop() {
		model.off();
		view.disableStopMenuItem();
		view.enableStartMenuItem();
	}

	@Override
	public void setBPM(int bpm) {
		model.setBPM(bpm);
	}

	@Override
	public void increaseBPM() {
		int bpm = model.getBPM();
		model.setBPM(bpm + 1);
	}

	@Override
	public void decreaseBPM() {
		int bpm = model.getBPM();
		model.setBPM(bpm - 1);
	}

}
