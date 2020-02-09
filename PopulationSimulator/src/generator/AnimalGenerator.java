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
	
	public void generateBasics(int num) {
		random = new Random();
		
		HashMap food = new HashMap();
		food.put("food", 100);
		//food.put("basic", 30);
		for(int i = 0; i < num; i++) { 
			Vector pos = new Vector(random.nextFloat(), random.nextFloat());
			pos = pos.scale(Game.getWorldDimentions());
			//pos = pos.sub(Game.getWorldDimentions().scale((float) 0.5));
			int r = 0;//(int)random.nextFloat()*255;
			int g = 0;//(int)random.nextFloat()*255;
			int b = 255;//(int)random.nextFloat()*255;
			System.out.printf(" (%d, %d, %d) ", r, g, b);
			Color color = new Color(r, g, b);
			
			float mutationRate = 0.5f;
			
			int matingMin = (int) (80 + PercentGenerator.relPercent(mutationRate));
			
			float fieldOfViewAngle = 65 + PercentGenerator.relPercent(mutationRate);
			int fieldOfViewRadius = (int) (100 + PercentGenerator.relPercent(mutationRate));
			float moveSpeed = (float) (1 + PercentGenerator.relPercent(mutationRate)*0.15);
			int radius = (int) (30 + PercentGenerator.relPercent(mutationRate));
			int eatingRate = (int) (10 + PercentGenerator.relPercent(mutationRate));
			int fleeRadius = (int) (radius + 15 + PercentGenerator.relPercent(mutationRate));
			int matingMinimum = 80;
			
			DNA dna = new DNA(Species.getSpecies("basic"), color, food, fieldOfViewAngle, fieldOfViewRadius, moveSpeed, radius, mutationRate, eatingRate, fleeRadius, matingMin);
			
			float foodAmt = 30;
			Animal a = new Animal(pos, dna, foodAmt);
			
			Game.animals.add(a);
			new Heart(pos);
//			new Heart(pos);
//			new Heart(pos);
//			new Heart(pos);
//			new Heart(pos);
			
			Game.animals.sort(new CustomAnimalComparator());
			
			System.out.println(Game.animals);
		}
	}
	
	public void generatePredators(int num) {
		random = new Random();
		
		HashMap food = new HashMap();
		food.put("food", 30);
		food.put("basic", 100);
		//food.put("basic", 30);
		for(int i = 0; i < num; i++) { 
			Vector pos = new Vector(random.nextFloat(), random.nextFloat());
			pos = pos.scale(Game.getWorldDimentions());
			//pos = pos.sub(Game.getWorldDimentions().scale((float) 0.5));
			int r = 255;//(int)random.nextFloat()*255;
			int g = 0;//(int)random.nextFloat()*255;
			int b = 0;//(int)random.nextFloat()*255;
			System.out.printf(" (%d, %d, %d) ", r, g, b);
			Color color = new Color(r, g, b);
			
			float mutationRate = 0.5f;
			
			int matingMin = (int) (80 + PercentGenerator.relPercent(mutationRate));
			
			float fieldOfViewAngle = 65 + PercentGenerator.relPercent(mutationRate);
			int fieldOfViewRadius = (int) (100 + PercentGenerator.relPercent(mutationRate));
			float moveSpeed = (float) (1 + PercentGenerator.relPercent(mutationRate)*0.15);
			int radius = (int) (30 + PercentGenerator.relPercent(mutationRate));
			int eatingRate = (int) (10 + PercentGenerator.relPercent(mutationRate));
			int fleeRadius = (int) (radius + 30 + PercentGenerator.relPercent(mutationRate));
			int matingMinimum = 80;
			
			DNA dna = new DNA(Species.getSpecies("predator"), color, food, fieldOfViewAngle, fieldOfViewRadius, moveSpeed, radius, mutationRate, eatingRate, fleeRadius, matingMin);
			
			float foodAmt = 30;
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
