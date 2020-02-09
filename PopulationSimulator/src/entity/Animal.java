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
	
	private Vector heading;
	
	public Animal(String name, Vector pos, DNA dna, int food) {
		this.name = name;
		setPos(pos);
		setVel(new Vector(1*dna.getMoveSpeed(),0));
		this.dna = dna;
		this.food = food;
		this.heading = new Vector(1, 0);
	}
	
	public Animal(String name, Vector pos, Vector heading, DNA dna, int food) {
		this.name = name;
		setPos(pos);
		this.heading = heading;
		this.dna = dna;
		this.food = food;
	}

	@Override
	public void render(Graphics g) {
		
		
		// Draw main body color
		float diam = dna.getRadius()*2;
		Vector viewPos = Game.camera.toViewPos(getPos().sub(new Vector(diam*0.5f, diam*0.5f)));
		Vector actPos = Game.camera.toViewPos(getPos().add(heading.scale(20)));
		g.setColor(dna.getColor());
		g.fillOval((int) (viewPos.get(0)), (int) (viewPos.get(1)), (int) (diam/Game.camera.getZoomAmount()), (int) (diam/Game.camera.getZoomAmount()));
		// Draw outline
		g.setColor(Color.BLACK);
		g.drawOval((int) (viewPos.get(0)), (int) (viewPos.get(1)), (int) (diam/Game.camera.getZoomAmount()), (int) (diam/Game.camera.getZoomAmount()));
		// Draw Name
		g.drawString(name, (int) viewPos.get(0), (int) viewPos.get(1) - 20);
		
		// Draw eye
		g.setColor(Color.BLUE);
		g.drawOval((int)(actPos.get(0)-3), (int)(actPos.get(1)-3), (int)(6/Game.camera.getZoomAmount()), (int)(6/Game.camera.getZoomAmount()));
		
		
	}

	@Override
	public void update() {
		setVel(heading.scale(dna.getMoveSpeed()));
		
		if(state != State.EAT) {
			setPos(getPos().add(this.getVel().scale(Game.getSimSpeed()*(0.005f))));
			heading = getVel().normalized();
		}
		
		timeAlive += Game.getSimSpeed();
		
		if(state == State.SEEK_FOOD) {
			seekFood();
		}
	}
	
	private Food getFoodInSight() {
		
		for(Food f : Game.food) {
			//TODO: Check if we can see food, if we found, return that food
			float dist = f.getPos().sub(this.getPos()).getMag();
			if(dist < dna.getRadius()) { // TODO: Check within sight
				System.out.println("Inside");
				return f;
			}
			
		}
		
		return null;
	}
	
	private void seekFood() {
		//TODO:  Write function to have animals to look for food.
		Food f = getFoodInSight();
		if(f != null) {
			System.out.println("Hit food!");
			eatFood(f);
		}
	}
	
	private void revaluateState() {
		if(food > dna.getMatingMinimum()) {
			state = State.SEEK_MATE; 
			return;
		}else {
			state = State.SEEK_FOOD;
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
	
	private void eatFood(Food f) {
		state = State.EAT;
		// Turn towards food
		heading = (f.getPos().sub(this.getPos())).normalized();
		food += f.getFood();
		Game.food.remove(f);
		
		revaluateState();
	}
	
	private void die() {
		//TODO
		Game.animals.remove(this);
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