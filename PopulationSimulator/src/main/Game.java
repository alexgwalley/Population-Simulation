package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.locationtech.jts.index.quadtree.Quadtree;

import chart.DataType;
import chart.SaveLoadChart;
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
	private long prevTime, prevTime2 = System.currentTimeMillis();
	public static long dt = -1;
	
	// Simulation
	private static long startTime;
	
	public static ArrayList<Food> food = new ArrayList<>();
	public static ArrayList<Animal> animals = new ArrayList<>();
	
	private static int numChunks = 9;
	public static ArrayList<Food>[] foodChunks = new ArrayList[numChunks];
	
	private final static Vector[] bounds = {
			new Vector(-1000, -1000),
			new Vector(1000, 1000)
	};
	private final static Vector worldDim = new Vector(2000, 2000);
	
	private static FoodGenerator foodGenerator;
	private static AnimalGenerator animalGenerator;

	private static boolean paused;
	
	public Game(String t, int w, int h) {
		title = t;
		width = w;
		height = h;
		
		simSpeed = 25;
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
		
		startTime = System.currentTimeMillis();
		
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
		
		SaveLoadChart.wipeData();
		
		System.out.println(animals);
	}
	
	private void update() {
		
		if(prevTime <= 0) prevTime = System.currentTimeMillis();
		dt = System.currentTimeMillis() - prevTime;
		prevTime = System.currentTimeMillis();
		
		
		foodGenerator.update();
		camera.update();
		
		Iterator<Animal> animalIterator = animals.iterator();
		
		// Render all Animals
		try {
			while(animalIterator.hasNext()) {
				animalIterator.next().update();
			}
		}catch (Exception e) {
			
		}
		
		//Check if the system needs to be saved or loaded.
		//This is done so that saving and loading doesn't run into concurrent use errors.
	
//		try {
//			if(SaveLoadGame.setToSave) SaveLoadGame.saveGame();
//			if(SaveLoadGame.setToLoad) SaveLoadGame.loadGame();
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}

		if(System.currentTimeMillis()-prevTime2 >= 1000) {
			
			List<String>[] lines = new List[3];
			
			try {
				SaveLoadChart.saveData();
				DataType[] filters = new DataType[] {DataType.TIME, DataType.DEFAULT, DataType.NUM, DataType.FOOD, 
						DataType.FOVA, DataType.FOVR, DataType.MOVESPEED, 
						DataType.RADIUS, DataType.MUTATIONRATE, DataType.EATINGRATE, 
						DataType.FLEERADIUS, DataType.MATINGMIN};
				
				lines = SaveLoadChart.loadData("basic", filters);
				
				float[][] allValues = new float[filters.length][lines[0].size()];
				float[] times;
				
				for(int i = 0; i < filters.length; i++) {
					for(int j = 0; j < lines[0].size(); j++) {
						if(i == 1) continue;
						try {
							allValues[i][j] = Float.parseFloat(lines[i].get(j));
						}catch (Exception e){
							System.out.println("Error Parsing: " + lines[i].get(j) + " i: " + i);
						}
					}
				}
				
				window.updateChart(allValues[0], allValues, filters);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			prevTime2+=1000;
		}
	}
	
	private void render() {
		
		bs = window.getCanvas().getBufferStrategy();
		if(bs == null) {
			window.getCanvas().createBufferStrategy(3);
			return;
		}
		
		//Adjusts screen whenever resized.
		if(window.getFrame().getWidth() != width || window.getFrame().getHeight() != height) {
			width = window.getFrame().getWidth();
			height = window.getFrame().getHeight();
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
		try {
			while(animalIterator.hasNext()) {
				animalIterator.next().render(g);
			}
		}catch (Exception e) {}
		
		
		// Showing and clean-up
		bs.show();
		g.dispose();
		
	}
	
	public static int getChunkIndex(Vector v) {
		int idX = (int)(worldDim.get(0)/v.get(0))*numChunks;
		int idY = (int)(worldDim.get(1)/v.get(1))*numChunks;
		
		int index = idX + idY * numChunks;
		return index;
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
	
	public static long getStartTime() {
		return startTime;
	}
}
