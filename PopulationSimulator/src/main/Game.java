package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import chart.DataType;
import chart.SaveLoadChart;
import comparator.CustomAnimalComparator;
import entity.Animal;
import entity.Food;
import entity.Species;
import generator.AnimalGenerator;
import generator.FoodGenerator;
import gui.LeaderboardButton;
import math.Vector;
import sprite.Sprite;


public class Game implements Runnable {
	
	// Window and Set-up
	private WindowManager window;

	private Thread thread;
	static boolean running;
	
	private String title;
	private static int width;
	private static int height;
	
	// Rendering
	private BufferStrategy bs;
	private Graphics g;
	
	
	// Updating
	private static float simSpeed;
	public static Camera camera;
	public static KeyboardManager keyboardManager;
	private long prevTime, prevTime2 = System.currentTimeMillis();
	public static long dt = -1;
	
	public static long gameTime;
	
	// Simulation
	private static long startTime;
	
	public static ArrayList<Food> food = new ArrayList<Food>();
	public static ArrayList<Animal> animals = new ArrayList<Animal>();
	public static ArrayList<Sprite> sprites = new ArrayList<Sprite>();
	
	private static int numChunks = 9;
	public static ArrayList<Food>[] foodChunks = new ArrayList[numChunks];

	public static List<LeaderboardButton> leaderboard = new ArrayList<>();
	
	private final static Vector[] bounds = {
			new Vector(1, 1),
			new Vector(2001, 2001)
	};
	final static Vector worldDim = new Vector(2000, 2000);
	
	private static FoodGenerator foodGenerator;
	private static AnimalGenerator animalGenerator;

	private static boolean paused;
	
	public Game(String t, int w, int h) {
		title = t;
		width = w;
		height = h;
		
		simSpeed = 50;
	}
	
	@Override
	public void run() {
		init();
		paused = false;
		
		while(running && !paused) {
			update();
			render();
			
			gameTime += simSpeed;
		}
		
		stop();
		
	}
	
	private void init() {
		
		for(int i = 0; i < numChunks; i++) {
			foodChunks[i] = new ArrayList<Food>();
		}
		
		startTime = System.currentTimeMillis();
		
		camera = new Camera();
		keyboardManager = new KeyboardManager();
		
		window = new WindowManager(title, width, height, this);
		
		foodGenerator = new FoodGenerator();
		animalGenerator = new AnimalGenerator();
		
		initLeaderboard();
		
		startNew();
		
	}

	public static void pause() {
		foodGenerator.pause();
		paused = true;
	}
	
	public static void play() {
		foodGenerator.play();
		paused = false; 
	}
	
	private void startNew() {
		new Species("basic");
		
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
		
		Iterator<Sprite> spriteIterator = sprites.iterator();
		
		// Render all Sprites
		try {
			while(spriteIterator.hasNext()) {
				spriteIterator.next().update();
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
			
			List<String>[] lines = new List[12];
			
			try {
				SaveLoadChart.saveData();
				DataType[] filters = new DataType[] {DataType.TIME, DataType.DEFAULT, DataType.NUM, DataType.FOOD, 
						DataType.FOVA, DataType.FOVR, DataType.MOVESPEED, 
						DataType.RADIUS, DataType.MUTATIONRATE, DataType.EATINGRATE, 
						DataType.FLEERADIUS, DataType.MATINGMIN};
				
				lines = SaveLoadChart.loadData(Species.getSpecies("basic"), filters);
				
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
			
			prevTime2 = System.currentTimeMillis();
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
		for(int i = 0; i < numChunks; i++) {
			for(Food f : foodChunks[i]) {
				f.render(g);
			}
		}
		
		Iterator<Animal> animalIterator = animals.iterator();
		try {
			while(animalIterator.hasNext()) {
				animalIterator.next().render(g);
			}
		}catch (Exception e) {}
		
		Iterator<Sprite> spriteIterator = sprites.iterator();
		try {
			while(spriteIterator.hasNext()) {
				spriteIterator.next().render(g);
			}
		}catch (Exception e) {}
		
		// Render simSpeed
		g.setColor(new Color(200, 200, 200));
		g.drawString("x"+simSpeed, 10, height - 50);
		
		camera.render(g);
		showLeaderBoard();
		
		// Showing and clean-up
		bs.show();
		g.dispose();
		
	}
	
	private void showLeaderBoard() {
		for(int i = 0; i < 5; i++) {
			leaderboard.get(i).render(g);
		}
	}
	
	private void initLeaderboard() {
		int x = 0;
		int y = 0;
		for(int i = 0; i < 5; i++) {
			leaderboard.add(new LeaderboardButton(null, i+1, x, y, 100, 30));
			y += 30;
		}
	}
	
	private static void updateLeaderboard() {
		for(int i = 0; i < 5; i++) {
			if(animals.get(i) != null) {
				leaderboard.get(i).setAnimal(animals.get(i));
			}else {
				leaderboard.get(i).setAnimal(null);
			}
		}
	}
	
	public static int getChunkIndex(Vector v) {
		v.add(new Vector());
		int a = (int) Math.sqrt(numChunks);
		int idX = (int)(a*v.get(0)/worldDim.get(0));
		int idY = (int)(a*v.get(1)/worldDim.get(1));
//		System.out.println("X: " + v.get(0));
//		System.out.println("Y: " + v.get(1));
//		System.out.println("Ratio: " + a*v.get(0)/worldDim.get(0));
//		System.out.println("Ratio: " + a*v.get(1)/worldDim.get(1));
//		System.out.println("A: " + a);
//		System.out.println("Idx: " + idX);
//		System.out.println("Idy: " + idY);
		int index = idX + idY * a;
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
	
	public static void increaseSimSpeed() {
		simSpeed *= 2;
		simSpeed = Math.min(500, simSpeed);
	}
	
	public static void decreaseSimSpeed() {
		simSpeed /= 2;
		simSpeed = Math.max(1, simSpeed);
	}
	
	public static void sortLeaderboard() {
		animals.sort(new CustomAnimalComparator());
		updateLeaderboard();
	}

}
