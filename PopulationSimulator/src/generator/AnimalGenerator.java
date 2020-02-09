package generator;

import java.awt.Color;
import java.util.HashMap;
import java.util.Random;

import comparator.CustomAnimalComparator;
import entity.Animal;
import entity.DNA;
import entity.Species;
import main.Game;
import math.Vector;
import sprite.Heart;

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
			//pos = pos.sub(Game.getWorldDimentions().scale((float) 0.5));
			
			Color color = new Color((int)random.nextFloat()*255, (int)random.nextFloat()*255, (int)random.nextFloat()*255);
			float foodAmt = 30;
			int matingMin = 80;
			DNA dna = new DNA(Species.getSpecies("basic"), color, food, (float)65, 100, 1, 30, (float)0.5, 10, 80, matingMin);
			Animal a = new Animal(pos, dna, foodAmt);
			
			Game.animals.add(a);
			new Heart(pos);
			new Heart(pos);
			new Heart(pos);
			new Heart(pos);
			new Heart(pos);
			
			Game.animals.sort(new CustomAnimalComparator());
			
			System.out.println(Game.animals);
		}
	}
	
}
