package main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;

import javax.swing.JFrame;

public class WindowManager {

	private JFrame frame;
	private Canvas canvas;
	
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
		
		frame.pack(); // Resize to fit all components
	}
	
	public Canvas getCanvas() {
		return canvas;
	}
	
}
