package main;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Game implements Runnable {
	
	// Window and Set-up
	private WindowManager window;

	private Thread thread;
	private boolean running;
	
	private String title;
	private int width;
	private int height;
	
	// Rendering
	
	private BufferStrategy bs;
	private Graphics g;
	
	
	// Updating
	private static float simSpeed;
	public static Camera camera;
	
	// Simulation

	
	public Game(String t, int w, int h) {
		title = t;
		width = w;
		height = h;
	}
	
	public static void main(String[] args) {
		
	}

	@Override
	public void run() {
		init();
		
		while(running) {
			update();
			render();
		}
		
		stop();
		
	}
	
	private void init() {
		
		camera = new Camera();
		window = new WindowManager(title, width, height, this);
		
	}
	
	private void update() {
		
	}
	
	private void render() {
		bs = window.getCanvas().getBufferStrategy();
		if(bs == null) {
			window.getCanvas().createBufferStrategy(3);
			return;
		}
		
	
		g = bs.getDrawGraphics();
		// Drawing stuff here
		
		
		// Showing and clean-up
		bs.show();
		g.dispose();
		
	}

	
	public synchronized void start() {
		if(running) return;
		
		running = true;
		thread = new Thread(this);
		System.out.println("Game thread created");
		
		thread.start();
		
	}
	
	public synchronized void stop() {
		if(!running) return;
		
		try {
			thread.join();
		}catch (InterruptedException e){
			e.printStackTrace();
		}
	}
	
	public float getSimSpeed() {
		return simSpeed;
	}
	
	public Camera getCamera() {
		return camera;
	}
}
