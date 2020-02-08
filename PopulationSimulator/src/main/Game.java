package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

import comparator.CustomAnimalComparator;
import entity.Animal;
import entity.DNA;
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
	public static SortedSet<Animal> animals = new TreeSet<>(new CustomAnimalComparator());
	
	private final static Vector[] bounds = {
			new Vector(-500, -500),
			new Vector(500, 500)
	};
	private final static Vector worldDim = new Vector(1000, 1000);
	
	private static FoodGenerator foodGenerator;

	private static boolean paused;
	
	public Game(String t, int w, int h) {
		title = t;
		width = w;
		height = h;
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
		 
		// Render all Food
		for(Food f : food) {
			f.render(g);
		}
		
		// Render all Animals
		for(Animal a : animals) {
			a.update();
			a.render(g);
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
	
	public static void saveGame() throws IOException {
		saveAnimalData();
		saveFoodData();
	}
	
	private static void saveAnimalData() throws IOException{
		//TODO: Add in text for food data.
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File("res\\game1.sim"), true));
		for(Animal a : animals) {
			DNA d = a.getDna();
			String line = "a," + a.getName() + "," + a.getPos(0) + "," + a.getPos(1) + "," + a.getFood() + "," + d.getSpecies() + "," 
					+ d.getColor() + "," + d.getFieldOfViewAngle() + "," + d.getFieldOfViewRadius() + "," 
					+ d.getMoveSpeed() + "," + d.getRadius() + "," + d.getMutationRate() + "," + d.getEatingRate() + "," 
					+ d.getFleeRadius() + "," + d.getMatingMinimum();
			bw.write(line);
			bw.newLine();
		}
		bw.flush();
		bw.close();
	}
	
	private static void saveFoodData() throws IOException{
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File("res\\game1.sim"), true));
		bw.newLine();
		for(Food f : food) {
			String line = "f," + f.getPos(0) + "," + f.getPos(1) + "," + f.getFood();
			bw.write(line);
			bw.newLine();
		}
		bw.flush();
		bw.close();
	}
	
	public static void loadGame() throws IOException{
		loadAnimals();
		loadFood();
	}
	
	private static void loadAnimals() throws IOException{
		animals = new TreeSet<>(new CustomAnimalComparator());
		BufferedReader br = new BufferedReader(new FileReader("res\\game1.sim"));
		String line = "";
		while((line = br.readLine()) != null) {
			String[] data = line.split(",");
			if(!data[0].equals("a")) continue;
			DNA d = new DNA(data[5],Color.WHITE,DNA.defaultPrey.getFood(),Float.parseFloat(data[7]),Integer.parseInt(data[8]),Integer.parseInt(data[9]),Integer.parseInt(data[10]),Float.parseFloat(data[11]),Integer.parseInt(data[12]),Integer.parseInt(data[13]),Integer.parseInt(data[14]));
			animals.add(new Animal(data[1], new Vector(Float.parseFloat(data[2]),Float.parseFloat(data[3])), d, Integer.parseInt(data[4])));
		}
		br.close();
	}
	
	private static void loadFood() throws IOException{
		food = new ArrayList<Food>();
		BufferedReader br = new BufferedReader(new FileReader("res\\game1.sim"));
		String line = "";
		while((line = br.readLine()) != null) {
			String data[] = line.split(",");
			if(!data[0].equals("f")) continue;
			food.add(new Food(new Vector(Float.parseFloat(data[1]), Float.parseFloat(data[2])), Integer.parseInt(data[3])));
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
	
	public static Vector getWorldDimentions() {
		return worldDim;
	}
	
	public static Vector getScreenDimentions() {
		return new Vector(width, height);
	}
}
