package MainPackage.View;

import java.awt.*;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

import MainPackage.Controller.ControllerInterface;
import MainPackage.Model.BPMObserver;
import MainPackage.Model.BeatModelInterface;
import MainPackage.Model.BeatObserver;

@SuppressWarnings("unused")
public class DJView implements ActionListener, BeatObserver, BPMObserver {
	// view holds a reference to the model and controller
	BeatModelInterface model;
	ControllerInterface controller;
	//display components
	JFrame viewFrame;
	JPanel viewPanel;
	BeatBar beatBar;
	JLabel bpmOutputLabel;
	JFrame controlFrame;
	JPanel controlPanel;
	JLabel bpmLabel;
	JTextField bpmTextField;
	JButton setBPMButton;
	JButton increaseBPMButton;
	JButton decreaseBPMButton;
	JMenuBar menuBar;
	JMenu menu;
	JMenuItem startMenuItem;
	JMenuItem stopMenuItem;
	
	public DJView(ControllerInterface controller, BeatModelInterface model){
		this.controller = controller;
		this.model = model;
		// register the model as a BeatObserver and BPMObserver
		model.registerObserver((BeatObserver) this);
		model.registerObserver((BPMObserver) this);
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public void createView(){
		//create all swing components
		// setup viewFrame 
		beatBar = new BeatBar();
		viewFrame = new JFrame("View");
		viewFrame.setBounds(new Rectangle(100, 500, 1500, 800));
		viewFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		viewPanel = new JPanel();
		viewPanel.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		viewPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		viewPanel.setLayout(new GridBagLayout());
		
		bpmOutputLabel = new JLabel("Current BPM: ");
		
		viewFrame.add(viewPanel);
		viewFrame.add(beatBar);
		viewFrame.add(bpmOutputLabel);
		
		//Size the frame
		viewFrame.pack();
		//Show it
		viewFrame.setVisible(true);
	}

	@Override
	public void updateBPM() {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBeat() {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

}
