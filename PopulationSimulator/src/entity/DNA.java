package entity;

import java.awt.Color;
import java.util.HashMap;
import java.util.Random;

import generator.PercentGenerator;

public class DNA {
	
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
		Random rand = new Random();
		DNA d1 = a1.getDna();
		DNA d2 = a2.getDna();
		
		float scale = 1;
		
		Species species = d1.getSpecies();
		float weight = PercentGenerator.newPercent();
		float mutationRate = weight*d1.getMutationRate()+(1-weight)*d2.getMutationRate();
		
		weight = PercentGenerator.newPercent();
		float colorRed = (weight*d1.getColor().getRed()+(1-weight)*d2.getColor().getRed());
		if(rand.nextFloat() < 0.05*mutationRate) {
			scale = PercentGenerator.relPercent(mutationRate);
			colorRed += scale;
			colorRed = Math.min(255, colorRed);
			colorRed = Math.max(0, colorRed);
		}
		
		weight = PercentGenerator.newPercent();
		float colorBlue = (weight*d1.getColor().getBlue()+(1-weight)*d2.getColor().getBlue());
		weight = PercentGenerator.newPercent();
		if(rand.nextFloat() < 0.05*mutationRate) {
			scale = PercentGenerator.relPercent(mutationRate);
			colorBlue += scale;
			colorBlue = Math.min(255, colorBlue);
			colorBlue = Math.max(0, colorBlue);
		}
		
		weight = PercentGenerator.newPercent();
		float colorGreen = (weight*d1.getColor().getGreen()+(1-weight)*d2.getColor().getGreen());
		if(rand.nextFloat() < 0.05*mutationRate) {
			scale = PercentGenerator.relPercent(mutationRate);
			colorGreen += scale;
			colorGreen = Math.min(255, colorGreen);
			colorGreen = Math.max(0, colorGreen);
		}
		
		HashMap<String, Integer> food = new HashMap<String,Integer>();
		for(String specie : d1.getFood().keySet()) {
			weight = PercentGenerator.newPercent();
			int value = (int) (weight*d1.getFood().get(specie)+(1-weight)*d2.getFood().get(specie));
			if(rand.nextFloat() < 0.05*mutationRate) {
				scale = PercentGenerator.relPercent(mutationRate);
				value += scale;
				value = Math.max(0, value);
			}
			food.put(specie, value);
		}
		
		weight = PercentGenerator.newPercent();
		float fieldOfViewAngle = (weight*d1.getFieldOfViewAngle()+(1-weight)*d2.getFieldOfViewAngle());
		if(rand.nextFloat() < 0.05*mutationRate) {
			scale = PercentGenerator.relPercent(mutationRate);
			fieldOfViewAngle += scale;
			fieldOfViewAngle = Math.min(360, fieldOfViewAngle);
			fieldOfViewAngle = Math.max(0, fieldOfViewAngle);
		}
		
		
		weight = PercentGenerator.newPercent();
		int fieldOfViewRadius = (int) ((weight*d1.getFieldOfViewRadius()+(1-weight)*d2.getFieldOfViewRadius()));
		if(rand.nextFloat() < 0.05*mutationRate) {
			scale = PercentGenerator.relPercent(mutationRate);
			fieldOfViewRadius += scale;
			fieldOfViewRadius = Math.max(0, fieldOfViewRadius);
		}
		
		

		weight = PercentGenerator.newPercent();
		int moveSpeed = (int) ((weight*d1.getMoveSpeed()+(1-weight)*d2.getMoveSpeed()));
		if(rand.nextFloat() < 0.05*mutationRate) {
			scale = PercentGenerator.relPercent(mutationRate);
			moveSpeed += scale;
			moveSpeed = Math.max(0, moveSpeed);
		}
		
		weight = PercentGenerator.newPercent();
		int radius = (int) ((weight*d1.getRadius()+(1-weight)*d2.getRadius())+scale);
		if(rand.nextFloat() < 0.05*mutationRate) {
			scale = PercentGenerator.relPercent(mutationRate);
			radius += scale;
			radius = Math.max(0, radius);
		}
		
		weight = PercentGenerator.newPercent();
		int eatingRate = (int) ((weight*d1.getEatingRate()+(1-weight)*d2.getEatingRate()));
		if(rand.nextFloat() < 0.05*mutationRate) {
			scale = PercentGenerator.relPercent(mutationRate);
			eatingRate += scale;
			eatingRate = Math.max(0, moveSpeed);
		}
		
		
		weight = PercentGenerator.newPercent();
		int fleeRadius = (int) ((weight*d1.getFleeRadius()+(1-weight)*d2.getFleeRadius()));
		if(rand.nextFloat() < 0.05*mutationRate) {
			scale = PercentGenerator.relPercent(mutationRate);
			fleeRadius += scale;
			fleeRadius = Math.max(0, fleeRadius);
		}

		weight = PercentGenerator.newPercent();
		int matingMinimum = (int) ((weight*d1.getMatingMinimum()+(1-weight)*d2.getMatingMinimum()));
		if(rand.nextFloat() < 0.05*mutationRate) {
			scale = PercentGenerator.relPercent(mutationRate);
			matingMinimum += scale;
			matingMinimum = Math.max(0, matingMinimum);
		}
		
		return new DNA(species, new Color((int)colorRed, (int)colorGreen, (int)colorBlue),food,fieldOfViewAngle,fieldOfViewRadius,moveSpeed,radius,mutationRate,eatingRate,fleeRadius,matingMinimum);
	}

}
