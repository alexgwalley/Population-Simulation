package main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;

import chart.DataType;

public class WindowManager {

	private JFrame frame;
	private Canvas canvas;
	private JMenuBar menubar;
	private JMenuItem save;
	private JMenuItem load;
	
	private JFrame chartFrame;
	private JMenuBar chMenubar;
	private JMenu dataTypes;
	private JMenuItem num, food, fova, fovr, movespeed, radius, mutrate, eatrate, fleerad, matemin;
	private JTextArea chartdata;
	private DataType currentDisplay = DataType.NUM;
	
	private String title;
	
	private int width;
	private int height;
	
	private Game gameRef;
	public WindowManager(String t, int w, int h, Game game) {
		title = t;
		width = w;
		height = h;
		
		createWindow();
		
		gameRef = game;
		
	}
	
	private void createWindow() {
		frame = new JFrame(title);
		System.out.println("JFrame Initiated");
		
		frame.setSize(width, height);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		canvas = new Canvas(); // New canvas element for the JFrame, handles drawing
		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setMinimumSize(new Dimension(width, height));
		canvas.setMaximumSize(new Dimension(width, height));
		
		canvas.addMouseMotionListener((MouseMotionListener) Game.camera);
		canvas.addMouseWheelListener((MouseWheelListener) Game.camera);
		
		frame.add(canvas);
		
		menubar = new JMenuBar();
		
		save = new JMenuItem("Save");
		menubar.add(save);
		
		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					SaveLoadGame.saveGame();
				}catch(Exception ex) {
					ex.printStackTrace();
				}
			}
			
		});
		
		load = new JMenuItem("Load");
		menubar.add(load);
		
		load.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					SaveLoadGame.loadGame();
				}catch(Exception ex) {
					ex.printStackTrace();
				}
			}
			
		});
		
		frame.setJMenuBar(menubar);
		
		frame.pack();  //Resize to fit all components
		
		chartFrame = new JFrame("Chart");
		chartFrame.setVisible(true);
		chartFrame.setSize(200, height);
		chartFrame.setLocationRelativeTo(frame);
		chartFrame.setResizable(false);
		
		chMenubar = new JMenuBar();
		chartFrame.setJMenuBar(chMenubar);
		
		dataTypes = new JMenu("Show Data");
		chMenubar.add(dataTypes);
		
		num = new JMenuItem("Pop. Size");
		food = new JMenuItem("Hunger");
		fova = new JMenuItem("FOV Angle");
		fovr = new JMenuItem("FOV Radius");
		movespeed = new JMenuItem("Move Speed");
		radius = new JMenuItem("Size");
		mutrate = new JMenuItem("Mutation Rate");
		eatrate = new JMenuItem("Eating Speed");
		fleerad = new JMenuItem("Flee Radius");
		matemin = new JMenuItem("Mate Minimum");
		dataTypes.add(num);
		dataTypes.add(food);
		dataTypes.add(fova);
		dataTypes.add(fovr);
		dataTypes.add(movespeed);
		dataTypes.add(radius);
		dataTypes.add(mutrate);
		dataTypes.add(eatrate);
		dataTypes.add(fleerad);
		dataTypes.add(matemin);
		
		chartdata = new JTextArea();
		chartFrame.add(chartdata);
		
		chartFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public Canvas getCanvas() {
		return canvas;
	}
	
	public DataType getCurrentDisplay() {
		return this.currentDisplay;
	}
	
	private class ChangeData implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == num) {currentDisplay = DataType.NUM;}
			if(e.getSource() == food) {currentDisplay = DataType.FOOD;}
			if(e.getSource() == fova) {currentDisplay = DataType.FOVA;}
			if(e.getSource() == fovr) {currentDisplay = DataType.FOVR;}
			if(e.getSource() == movespeed) {currentDisplay = DataType.MOVESPEED;}
			if(e.getSource() == radius) {currentDisplay = DataType.RADIUS;}
			if(e.getSource() == mutrate) {currentDisplay = DataType.MUTATIONRATE;}
			if(e.getSource() == eatrate) {currentDisplay = DataType.EATINGRATE;}
			if(e.getSource() == fleerad) {currentDisplay = DataType.FLEERADIUS;}
			if(e.getSource() == matemin) {currentDisplay = DataType.MATINGMIN;}
		}
		
	}
	
}
