package generator;

import java.util.Random;

import entity.Food;
import main.Game;
import math.Vector;

public class FoodGenerator {
	
	private long lastSpawnedTime;
	private int spawnPeriod = 3000;
	
	Random random;
	
	public FoodGenerator() {
		random = new Random();
	}
	
	public void update() {
		
		if(System.currentTimeMillis() - lastSpawnedTime > spawnPeriod) {
			lastSpawnedTime = System.currentTimeMillis();
			spawn();
		}
		
	}
	
	public void generateStartingSpawn() {
		for(int i = 0; i < 30; i++) spawn();
		
	}
	
	// Spawns a new piece of food at a random position in bounds
	private void spawn() {
		Vector pos = new Vector(random.nextFloat()*Game.getWorldWidth() - Game.getWorldWidth()/2, 
				random.nextFloat()*Game.getWorldHeight() - Game.getWorldHeight()/2);
		Food newFood = new Food(pos);
		Game.food.add(newFood);
	}
	

}
