package generator;

import java.awt.Color;
import java.util.HashMap;
import java.util.Random;

import entity.Animal;
import entity.DNA;
import main.Game;
import math.Vector;

public class AnimalGenerator {

	Random random;
	
	public void generateAnimals(int num) {
		random = new Random();
		
		HashMap food = new HashMap();
		food.put("food", 100);
		
		for(int i = 0; i < num; i++) { 
			Vector pos = new Vector(random.nextFloat(), random.nextFloat());
			pos = pos.scale(Game.getWorldDimentions());
			pos = pos.sub(Game.getWorldDimentions().scale((float) 0.5));
			
			DNA dna = new DNA("basic", new Color(200, 0, 0), food, (float)65, 10, 1, 5, (float)5, 10, 20, 60);
			Animal a = new Animal("Bob", pos, dna, 50);
			
			Game.animals.add(a);
		}
	}
	
}
