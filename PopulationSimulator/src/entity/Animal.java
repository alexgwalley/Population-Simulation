package entity;

import java.awt.Color;
import java.awt.Graphics;

import main.Game;
import math.*;

public class Animal extends Entity{
	
	private DNA dna;
	private int food;
	private State state = State.SEEK_FOOD;
	private String name = "";
	private int timeAlive = 0;
	
	public Animal(String name, Vector pos, DNA dna, int food) {
		this.name = name;
		setPos(pos);
		setVel(new Vector(1*dna.getMoveSpeed(),0));
		this.dna = dna;
		this.food = food;
	}
	
	public Animal(Vector pos, Vector vel, DNA dna, int food) {
		setPos(pos);
		setVel(vel);
		this.dna = dna;
		this.food = food;
	}

	@Override
	public void render(Graphics g) {
		Vector viewPos = Game.camera.toViewPos(getPos());
		
		g.setColor(dna.getColor());
		g.fillOval((int) viewPos.get(0), (int) viewPos.get(1), (int) (30/Game.camera.getZoomAmount()), (int) (30/Game.camera.getZoomAmount()));
		g.setColor(Color.BLACK);
		g.drawOval((int) viewPos.get(0), (int) viewPos.get(1), (int) (30/Game.camera.getZoomAmount()), (int) (30/Game.camera.getZoomAmount()));
		g.drawString(name, (int) viewPos.get(0), (int) viewPos.get(1) - 20);
	}

	@Override
	public void update() {
		if(state != State.EAT)
			setPos(getPos().add(this.getVel().scale(Game.getSimSpeed()*(0.005f))));
		
		timeAlive += Game.getSimSpeed();
		
		if(state == State.SEEK_FOOD) {
			seekFood();
		}
	}
	
	private void checkEatFood() {
		
	}
	
	private Food getFoodInSight() {
		boolean foundFood = false;
		
		for(Food f : Game.food) {
			//TODO: Check if we can see food, if we found, return that food
		}
		
		return null;
	}
	
	private void seekFood() {
		//TODO:  Write function to have animals to look for food.
		if(getFoodInSight() != null) {
			//TODO: Turn towards the food
		}
		
	}
	
	private void seekMate() {
		//TODO:  Write function to have animals to look for a mate;
	}
	
	private void mate(Animal partner) {
		//TODO:  Write function for when animals mate.
	}
	
	private void flee(Animal predator) {
		//TODO:  Write function to flee from a predator in a certain radius.
	}
	
	private void die() {
		//TODO
	}

	public DNA getDna() {
		return dna;
	}

	public void setDna(DNA dna) {
		this.dna = dna;
	}

	public int getFood() {
		return food;
	}

	public void setFood(int food) {
		this.food = food;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTimeAlive() {
		return timeAlive;
	}

	public void setTimeAlive(int timeAlive) {
		this.timeAlive = timeAlive;
	}
	
}