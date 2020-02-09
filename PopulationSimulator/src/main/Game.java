package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import comparator.CustomAnimalComparator;
import entity.Animal;
import entity.Food;
import generator.AnimalGenerator;
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
	private long prevTime;
	public static long dt = -1;
	
	// Simulation
	public static ArrayList<Food> food = new ArrayList<>();
	public static ArrayList<Animal> animals = new ArrayList<>();
	
	private final static Vector[] bounds = {
			new Vector(-500, -500),
			new Vector(500, 500)
	};
	private final static Vector worldDim = new Vector(1000, 1000);
	
	private static FoodGenerator foodGenerator;
	private static AnimalGenerator animalGenerator;

	private static boolean paused;
	
	public Game(String t, int w, int h) {
		title = t;
		width = w;
		height = h;
		
		simSpeed = 1;
	}
	
	@Override
	public void run() {
		init();
		paused = false;
		
		while(running && !paused) {
			update();
			render();
		}
		
		stop();
		
	}
	
	private void init() {
		
		camera = new Camera();
		window = new WindowManager(title, width, height, this);
		
		foodGenerator = new FoodGenerator();
		animalGenerator = new AnimalGenerator();
		
		startNew();
		
	}

	public void pause() {
		foodGenerator.pause();
	}
	
	public void play() {
		foodGenerator.play();
	}
	
	private void startNew() {
		foodGenerator.generateStartingSpawn();
		animalGenerator.generateAnimals(5);
		
		System.out.println(animals);
	}
	
	private void update() {
		
		if(prevTime <= 0) prevTime = System.currentTimeMillis();
		dt = System.currentTimeMillis() - prevTime;
		prevTime = System.currentTimeMillis();
		
		
		foodGenerator.update();
		
		Iterator<Animal> animalIterator = animals.iterator();
		
		// Render all Animals
		while(animalIterator.hasNext()) {
			animalIterator.next().update();
		}
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
		 
		// Render all Food
		for(Food f : food) {
			f.render(g);
		}
		
		Iterator<Animal> animalIterator = animals.iterator();
		// Render all Animals
		while(animalIterator.hasNext()) {
			Animal a = animalIterator.next();
			a.render(g);
			//System.out.println(a);
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
	
	public static float getSimSpeed() {
		return simSpeed;
	}
	
	public Camera getCamera() {
		return camera;
	}
	
	public static Vector[] getBounds() {
		return bounds;
	}
	
	public static Vector getWorldDimentions() {
		return worldDim;
	}
	
	public static Vector getScreenDimentions() {
		return new Vector(width, height);
	}
}
