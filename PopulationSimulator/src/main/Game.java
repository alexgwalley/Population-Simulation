package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import entity.Food;
import generator.FoodGenerator;
import math.Vector;

public class Game implements Runnable {
	
	// Window and Set-up
	private WindowManager window;

	private Thread thread;
	private boolean running;
	
	private String title;
	private static int width;
	private static int height;
	
	// Rendering
	private BufferStrategy bs;
	private Graphics g;
	
	
	// Updating
	private static float simSpeed;
	public static Camera camera;
	
	// Simulation
	public static ArrayList<Food> food = new ArrayList<>();
	
	private final static Vector[] bounds = {
			new Vector(-500, -500),
			new Vector(500, 500)
	};
	
	private final static int worldWidth = 1000;
	private final static int worldHeight = 1000;
	
	private static FoodGenerator foodGenerator;

	
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
		
		foodGenerator = new FoodGenerator();
		
		startNew();
		
	}
	
	private void startNew() {
		foodGenerator.generateStartingSpawn();
	}
	
	private void update() {
		foodGenerator.update();
	}
	
	private void render() {
		
		bs = window.getCanvas().getBufferStrategy();
		if(bs == null) {
			window.getCanvas().createBufferStrategy(3);
			return;
		}
		
	
		g = bs.getDrawGraphics();
		// Drawing stuff here
		
		// Clear screen
		g.setColor(new Color(255, 255, 255));
		g.fillRect(0, 0, width, height);
		
		// Draw outer bounds
		g.setColor(new Color(0, 0, 0));
		Vector boundsTopLeft = camera.toViewPos(bounds[0]);
		Vector boundsBotRight = camera.toViewPos(bounds[1]);
		int boundsCamWidth = (int)(boundsBotRight.get(0) - boundsTopLeft.get(0));
		int boundsCamHeight = (int)(boundsBotRight.get(1) - boundsTopLeft.get(1));
		
		g.drawRect((int)boundsTopLeft.get(0), (int)boundsTopLeft.get(1), 
				boundsCamWidth, boundsCamHeight);
		 
		Vector size = new Vector(100, 100);
		size = size.scale(1/camera.getZoomAmount());
		

		for(Food f : food) {
			f.render(g);
		}
		
		
		
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
	
	public static Vector[] getBounds() {
		return bounds;
	}
	
	public static int getWorldWidth() {
		return worldWidth;
	}
	
	public static int getWorldHeight() {
		return worldHeight;
	}
	
	public static int getScreenWidth() {
		return width;
	}
	
	public static int getScreenHeight() {
		return height;
	}
}
