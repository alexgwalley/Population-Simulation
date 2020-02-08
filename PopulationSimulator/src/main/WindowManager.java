package main;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class WindowManager {

	private JFrame frame;
	private Canvas canvas;
	
	private String title;
	
	private int width;
	private int height;
	
	public WindowManager(String t, int w, int h) {
		title = t;
		width = w;
		height = h;
		
		createWindow();
		
	}
	
	private void createWindow() {
		frame = new JFrame(title);
		System.out.println("JFrame Initiated");
		
		frame.setSize(width, height);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		canvas = new Canvas(); // New canvas element for the JFrame, handles drawing
		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setMinimumSize(new Dimension(width, height));
		canvas.setMaximumSize(new Dimension(width, height));
		
		frame.add(canvas);
		
		
		frame.pack(); // Resize to fit all components
	}
	
	public Canvas getCanvas() {
		return canvas;
	}
	
}
