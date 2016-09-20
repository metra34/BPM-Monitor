package MainPackage.View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import MainPackage.*;
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
	
	public void createView(){
		//create all swing components
		// setup viewFrame 
		viewPanel = new JPanel(new GridLayout(1,2)); // 1 row, 2 columns
		viewFrame = new JFrame("View");
		viewFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		viewFrame.setSize(new Dimension(100, 80)); //set size using dimension, not rectangle
		
		bpmOutputLabel = new JLabel("offline", SwingConstants.CENTER);
		beatBar = new BeatBar();
		beatBar.setValue(0);
		
		JPanel bpmPanel = new JPanel(new GridLayout(2, 1)); //2 rows, 1 column - upper beatbar - lower bpm display
		bpmPanel.add(beatBar);
		bpmPanel.add(bpmOutputLabel);
		viewPanel.add(bpmPanel);
		
		viewFrame.getContentPane().add(viewPanel, BorderLayout.CENTER);
		//Size the frame
		viewFrame.pack();
		//Show it
		viewFrame.setVisible(true);
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public void createControls(){
		// create all swing components here
		JFrame.setDefaultLookAndFeelDecorated(true);
		controlFrame = new JFrame("Control");
		controlFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		controlFrame.setSize(new Dimension(100, 80));
		
		// panel to hold all controls, 1 row, 2 columns
		controlPanel = new JPanel(new GridLayout(1,2));
		
		menuBar = new JMenuBar();
		menu = new JMenu("DJ Control");
		startMenuItem = new JMenuItem("Start");
		menu.add(startMenuItem);
		
		// create menu item, add it to menu, add listener for user interaction with the menu item, repeat for each menu item
		startMenuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.start();
			}
		});
		
		stopMenuItem = new JMenuItem("Stop");
		menu.add(stopMenuItem);
		stopMenuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				controller.stop();
			}
		});
		
		JMenuItem exit = new JMenuItem ("Quit");
		menu.add(exit);
		exit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		// menu bar holds a collection of menus (1 here) which holds a collection of menu items (3 here)
		menuBar.add(menu);
		controlFrame.setJMenuBar(menuBar);
		
		bpmTextField = new JTextField(2); // 2 represents number of columns in field, can add first arg as initial str text
		bpmLabel = new JLabel("Enter BPM:", SwingConstants.RIGHT);
		// initialize buttons
		setBPMButton = new JButton("Set");
		setBPMButton.setSize(new Dimension(10, 40));
		increaseBPMButton = new JButton(">>");
		decreaseBPMButton = new JButton("<<");
		
		// add listener, delegate action to actionPerformed() of this class
		setBPMButton.addActionListener(this);
		increaseBPMButton.addActionListener(this);
		decreaseBPMButton.addActionListener(this);
		
		//panel to hold the incr and decr buttons, 1 row, 2 columns
		JPanel buttonPanel = new JPanel(new GridLayout(1,2));
		buttonPanel.add(decreaseBPMButton);
		buttonPanel.add(increaseBPMButton);
		
		JPanel enterPanel = new JPanel(new GridLayout(1,2)); // 1 row, 2 columns, left is bpmlabel, right is textfield
		enterPanel.add(bpmLabel);
		enterPanel.add(bpmTextField);
		JPanel insideControlPanel = new JPanel(new GridLayout(3,1)); //3 rows, 1 column - top has enter bpm: - mid has set button - bottom has decr and incr bpm
		insideControlPanel.add(enterPanel);
		insideControlPanel.add(setBPMButton);
		insideControlPanel.add(buttonPanel);
		controlPanel.add(insideControlPanel);
		
		bpmLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		bpmOutputLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		// getrootpane returns you to the top of the heigharchy, getcontentpane gets the contentpane which is (never?) the top
		controlFrame.getRootPane().setDefaultButton(setBPMButton); 
		controlFrame.getContentPane().add(controlPanel, BorderLayout.CENTER);
		
		controlFrame.pack();
		controlFrame.setVisible(true);
	}
	
	public void enableStopMenuItem(){
		stopMenuItem.setEnabled(true);
	}
	
	public void disableStopMenuItem() {
    	stopMenuItem.setEnabled(false);
	}

	public void enableStartMenuItem() {
    	startMenuItem.setEnabled(true);
	}

	public void disableStartMenuItem() {
    	startMenuItem.setEnabled(false);
	}

	@Override
	public void updateBPM() {
		if (model != null){
			int bpm = model.getBPM();
			if (bpm == 0){
				if (bpmOutputLabel != null){
					bpmOutputLabel.setText("offline");
				}
			} else{
				if (bpmOutputLabel != null){
					bpmOutputLabel.setText("Current BPM: "+model.getBPM());
				}
			}
		}
	}

	@Override
	public void updateBeat() {
		if (beatBar != null){
			beatBar.setValue(100);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// check the action event and perform the appropriate action based on button pressed
		if (e.getSource() == setBPMButton){
			int bpm = Integer.parseInt(bpmTextField.getText());
			controller.setBPM(bpm);
		} else if (e.getSource() == increaseBPMButton){
			controller.increaseBPM();
		} else if(e.getSource() == decreaseBPMButton){
			controller.decreaseBPM();
		}
	}
}
