package generator;

import java.util.Random;

import entity.Food;
import main.Game;
import math.Vector;

public class FoodGenerator {
	
	private long lastSpawnedTime;
	private int spawnPeriod = 10000;
	
	private long timeDifference;
	
	Random random;
	
	public FoodGenerator() {
		random = new Random();
	}
	
	public void update() {
		
		if(System.currentTimeMillis() - lastSpawnedTime > spawnPeriod/Game.getSimSpeed()) {
			lastSpawnedTime = System.currentTimeMillis();
			spawn();
		}
		
	}
	
	public void generateStartingSpawn(int num) {
		for(int i = 0; i < num; i++) spawn();
		
	}
	
	// Spawns a new piece of food at a random position in bounds
	private void spawn() {
		Vector pos = new Vector(random.nextFloat(), random.nextFloat());
		pos = pos.scale(Game.getWorldDimentions());
		//pos = pos.sub(Game.getWorldDimentions().scale((float) 0.5));
		
		int index = Game.getChunkIndex(pos);
		//System.out.println("Index: " + index);
		
		Food newFood = new Food(pos, 10);
		Game.foodChunks[index].add(newFood);
	}
	
	public void pause() {
		timeDifference = System.currentTimeMillis() - lastSpawnedTime;
	}
	
	public void play() {
		lastSpawnedTime = System.currentTimeMillis() - timeDifference;
	}

}
