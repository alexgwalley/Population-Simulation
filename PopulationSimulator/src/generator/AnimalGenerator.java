package generator;

import java.awt.Color;
import java.util.HashMap;
import java.util.Random;

import comparator.CustomAnimalComparator;
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
		//food.put("basic", 30);
		for(int i = 0; i < num; i++) { 
			Vector pos = new Vector(random.nextFloat(), random.nextFloat());
			pos = pos.scale(Game.getWorldDimentions());
			pos = pos.sub(Game.getWorldDimentions().scale((float) 0.5));
			
			DNA dna = new DNA("basic", new Color(200, 0, 0), food, (float)65, 100, 1, 30, (float)5, 10, 80, 60);
			Animal a = new Animal(pos, dna, 50);
			
			Game.animals.add(a);
			
			Game.animals.sort(new CustomAnimalComparator());
			
			System.out.println(Game.animals);
		}
	}
	
}
