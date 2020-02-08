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

public class WindowManager {

	private JFrame frame;
	private Canvas canvas;
	private JMenuBar menubar;
	private JMenuItem save;
	private JMenuItem load;
	
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
					Game.saveGame();
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
					Game.loadGame();
				}catch(Exception ex) {
					ex.printStackTrace();
				}
			}
			
		});
		
		frame.setJMenuBar(menubar);
		
		frame.pack();  //Resize to fit all components
	}
	
	public Canvas getCanvas() {
		return canvas;
	}
	
}
