package MainPackage.Model;

import MainPackage.Controller.BeatController;
import MainPackage.Controller.ControllerInterface;

public class DJTestDrive {
	public static void main (String[] args){
		// model created first, passed as an arguement to controller and initialized within controller - view is created and intialized within controller
		BeatModelInterface model = new BeatModel();
		ControllerInterface controller = new BeatController(model);
	}
}
