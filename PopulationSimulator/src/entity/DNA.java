package entity;

import java.awt.Color;
import java.util.HashMap;

import generator.PercentGenerator;

public class DNA {
	
	public static final DefaultPreyDNA defaultPrey = new DefaultPreyDNA();
	
	//"species" is the name of the species that this creature is.
	private Species species;
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
	
	public DNA(Species species, Color color, HashMap<String, Integer> food, float fieldOfViewAngle, 
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


	public Species getSpecies() {
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
		DNA d1 = a1.getDna();
		DNA d2 = a2.getDna();
		
		Species species = d1.getSpecies();
		float weight = PercentGenerator.newPercent();
		float mutationRate = weight*d1.getMutationRate()+(1-weight)*d2.getMutationRate();
		weight = PercentGenerator.newPercent();
		float scale = PercentGenerator.relPercent(mutationRate);
		float colorRed = (weight*d1.getColor().getRed()+(1-weight)*d2.getColor().getRed())+scale;
		weight = PercentGenerator.newPercent();
		scale = PercentGenerator.relPercent(mutationRate);
		float colorBlue = (weight*d1.getColor().getBlue()+(1-weight)*d2.getColor().getBlue())+scale;
		weight = PercentGenerator.newPercent();
		scale = PercentGenerator.relPercent(mutationRate);
		float colorGreen = (weight*d1.getColor().getGreen()+(1-weight)*d2.getColor().getGreen())+scale;
		HashMap<String, Integer> food = d1.getFood();
		weight = PercentGenerator.newPercent();
		scale = PercentGenerator.relPercent(mutationRate);
		float fieldOfViewAngle = (weight*d1.getFieldOfViewAngle()+(1-weight)*d2.getFieldOfViewAngle())+scale;
		weight = PercentGenerator.newPercent();
		scale = PercentGenerator.relPercent(mutationRate);
		int fieldOfViewRadius = (int) ((weight*d1.getFieldOfViewRadius()+(1-weight)*d2.getFieldOfViewRadius())+scale);
		weight = PercentGenerator.newPercent();
		scale = PercentGenerator.relPercent(mutationRate);
		int moveSpeed = (int) ((weight*d1.getMoveSpeed()+(1-weight)*d2.getMoveSpeed())+scale);
		weight = PercentGenerator.newPercent();
		int radius = (int) ((weight*d1.getRadius()+(1-weight)*d2.getRadius())+scale);
		weight = PercentGenerator.newPercent();
		scale = PercentGenerator.relPercent(mutationRate);
		int eatingRate = (int) ((weight*d1.getEatingRate()+(1-weight)*d2.getEatingRate()+scale));
		weight = PercentGenerator.newPercent();
		scale = PercentGenerator.relPercent(mutationRate);
		int fleeRadius = (int) ((weight*d1.getFleeRadius()+(1-weight)*d2.getFleeRadius())+scale);
		weight = PercentGenerator.newPercent();
		scale = PercentGenerator.relPercent(mutationRate);
		int matingMinimum = (int) ((weight*d1.getMatingMinimum()+(1-weight)*d2.getMatingMinimum())+scale);
		return new DNA(species, new Color((int)colorRed, (int)colorGreen, (int)colorBlue),food,fieldOfViewAngle,fieldOfViewRadius,moveSpeed,radius,mutationRate,eatingRate,fleeRadius,matingMinimum);
	}

}
