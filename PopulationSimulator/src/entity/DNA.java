package entity;

import java.awt.Color;
import java.util.HashMap;

public class DNA {
	
	public static final DefaultPreyDNA defaultPrey = new DefaultPreyDNA();
	
	//"species" is the name of the species that this creature is.
	private String species;
	private Color color;
	//The String is for the species that is considered food, 
	//and the Integer is how low the food has to be before being willing to eat it.
	private HashMap<String, Integer> food;
	private float fieldOfViewAngle;
	private int fieldOfViewRadius;
	private int moveSpeed;
	private int radius;
	private float mutationRate;
	private int eatingRate;
	private int fleeRadius;
	private int matingMinimum;
	
	public DNA(String species, Color color, HashMap<String, Integer> food, float fieldOfViewAngle, 
			int fieldOfViewRadius, int moveSpeed, int radius, float mutationRate, int eatingRate, int fleeRadius, 
			int matingMinimum) {
		this.species = species;
		this.color = color;
		this.food = food;
		this.fieldOfViewAngle = fieldOfViewAngle;
		this.fieldOfViewRadius = fieldOfViewRadius;
		this.moveSpeed = moveSpeed;
		this.radius = radius;
		this.mutationRate = mutationRate;
		this.eatingRate = eatingRate;
		this.fleeRadius = fleeRadius;
		this.matingMinimum = matingMinimum;
	}


	public String getSpecies() {
		return species;
	}

	public Color getColor() {
		return color;
	}

	public HashMap<String, Integer> getFood() {
		return food;
	}

	public float getFieldOfViewAngle() {
		return fieldOfViewAngle;
	}

	public int getFieldOfViewRadius() {
		return fieldOfViewRadius;
	}

	public int getMoveSpeed() {
		return moveSpeed;
	}

	public int getRadius() {
		return radius;
	}

	public float getMutationRate() {
		return mutationRate;
	}

	public int getEatingRate() {
		return eatingRate;
	}

	public int getFleeRadius() {
		return fleeRadius;
	}

	public int getMatingMinimum() {
		return matingMinimum;
	}
	
	public static DNA combine(Animal a1, Animal a2) {
		//TODO:  Write this.
		return a1.getDna();
	}

}
