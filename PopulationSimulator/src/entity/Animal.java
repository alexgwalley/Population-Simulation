package entity;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import main.Game;
import math.Vector;

public class Animal extends Entity{
	
	private DNA dna;
	private State state = State.SEEK_FOOD;
	private String name = "";
	private int timeAlive = 0;
	
	private Vector heading;
	
	private Animal chasedBy;
	
	public Animal(String name, Vector pos, DNA dna, int food) {
		this.name = name;
		setPos(pos);
		this.dna = dna;
		this.food = food;
		Random rand = new Random();
		this.heading = new Vector(rand.nextFloat(), rand.nextFloat()).normalized();
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
		Vector eyePos = Game.camera.toViewPos(getPos().add(heading.scale(20)));
		Vector actPos = Game.camera.toViewPos(getPos());
		int alpha = Math.min(255, (int)(255*this.food/dna.getMatingMinimum()));
		g.setColor( new Color(dna.getColor().getRed(), dna.getColor().getGreen(), dna.getColor().getBlue(), alpha) );
		g.fillOval((int) (viewPos.get(0)), (int) (viewPos.get(1)), (int) (diam/Game.camera.getZoomAmount()), (int) (diam/Game.camera.getZoomAmount()));
		// Draw outline
		g.setColor(Color.BLACK);
		g.drawOval((int) (viewPos.get(0)), (int) (viewPos.get(1)), (int) (diam/Game.camera.getZoomAmount()), (int) (diam/Game.camera.getZoomAmount()));
		// Draw Name
		if(state == State.HUNTING)
			g.drawString(name + "(Hunting)", (int) viewPos.get(0), (int) viewPos.get(1) - 20);
		else if(state == State.FLEE)
			g.drawString(name + "(Fleeing)", (int) viewPos.get(0), (int) viewPos.get(1) - 20);
		else
			g.drawString(name, (int) viewPos.get(0), (int) viewPos.get(1) - 20);
		
		// Draw Arc
		g.setColor(Color.GRAY);
		Vector leftEnd = heading.rotateDegrees((float) (dna.getFieldOfViewAngle()*0.5)).normalized().scale(dna.getFieldOfViewRadius()/Game.camera.getZoomAmount()).add(actPos);
		Vector rightEnd = heading.rotateDegrees((float) (-dna.getFieldOfViewAngle()*0.5)).normalized().scale(dna.getFieldOfViewRadius()/Game.camera.getZoomAmount()).add(actPos);
		g.drawLine((int)actPos.get(0), (int)actPos.get(1), (int)leftEnd.get(0), (int)leftEnd.get(1));
		g.drawLine((int)actPos.get(0), (int)actPos.get(1), (int)rightEnd.get(0), (int)rightEnd.get(1));
		
		// Draw mating circle
		if(state == State.SEEK_MATE || state == State.GOING_TO_MATE) {
			g.setColor(Color.PINK);
			float matingCircleDiam = dna.getMatingMinimum()*2;
			Vector matingCirclePos = Game.camera.toViewPos(getPos().sub(new Vector(matingCircleDiam*0.5f, matingCircleDiam*0.5f)));
			g.drawOval((int)matingCirclePos.get(0), (int)matingCirclePos.get(1), (int) (matingCircleDiam/Game.camera.getZoomAmount()), (int) (matingCircleDiam/Game.camera.getZoomAmount()));
		}
	}

	@Override
	public void update() {
		checkAndRecalcHeadingEdges();
		setVel(heading.scale(dna.getMoveSpeed()));
		
		if(state != State.EAT && state != State.MATING) {
			setPos(getPos().add(this.getVel().scale(Game.getSimSpeed()*(0.005f))));
			heading = getVel().normalized();
			
			// Lose food from moving
			this.food -= (dna.getMoveSpeed()*Game.getSimSpeed()*(0.005f))/175f;
			
			if(this.food <= 0) this.die();
			
		}
		
		this.revaluateState();
		
		timeAlive += Game.getSimSpeed();
		
		if(state == State.SEEK_FOOD) {
			seekFood();
		}
		if(state == State.SEEK_MATE || state == State.GOING_TO_MATE)
			seekMate();
		if(state == State.FLEE)
			flee(chasedBy);

	}
	
	private void checkAndRecalcHeadingEdges() {
		if(getPos().get(0) > Game.getBounds()[1].get(0) || getPos().get(0) < Game.getBounds()[0].get(0)) { // Off right or left hand side
			heading.set(0, -heading.get(0));
		}
		if(getPos().get(1) > Game.getBounds()[1].get(1) || getPos().get(1) < Game.getBounds()[0].get(1)) { // Off top or bottom
			heading.set(1, -heading.get(1));
		}
		
	}
	
	private Entity getFoodInSight() {
		
		for(Animal a : Game.animals) {
			if(!dna.getFood().containsKey(a.dna.getSpecies())) continue;
			if(food > dna.getFood().get(a.dna.getSpecies())) continue; // If not desperate enough
			
			Vector to = a.getPos().sub(this.getPos());
			float dist = to.getMag();
			if(dist < dna.getFieldOfViewAngle() &&  to.angleBetweenDegrees(this.heading) < dna.getFieldOfViewAngle()*0.5) { // TODO: Check within sight
				return a;
			}
			
		}
		
		for(Food f : Game.food) {
			if(!dna.getFood().containsKey(f.getSpecies())) continue;
			if(food > dna.getFood().get(f.getSpecies())) continue; // If not desperate enough
			//TODO: Check if we can see food, if we found, return that food
			Vector to = f.getPos().sub(this.getPos());
			float dist = to.getMag();
			if(dist < dna.getFieldOfViewAngle() &&  to.angleBetweenDegrees(this.heading) < dna.getFieldOfViewAngle()*0.5) { // TODO: Check within sight
				return f;
			}
			
		}
		
		return null;
	}
	
	private Animal beingChased() {
		for(Animal a : Game.animals) {
			if(a == this) continue;
			if(a.state != State.HUNTING) continue;
			
			Vector to = a.getPos().sub(this.getPos());
			float dist = to.getMag();
			if(dist < dna.getFleeRadius()-a.getDna().getRadius()) {
				return a;
			}
		}
		
		return null;
	}
	
	
	private void seekFood() {
		//TODO:  Write function to have animals to look for food.
		var f = getFoodInSight();
		if(f != null) {
			Vector to = f.getPos().sub(this.getPos());
			heading = to.normalized();
			
			if(f instanceof Animal) {
				state = State.HUNTING;
				if(to.getMag() < dna.getRadius()+((Animal) f).getDna().getRadius()) {
					eatFood(f);
					System.out.println("Nom");
				}
			}else {
				if(to.getMag() < dna.getRadius()) {
					eatFood(f);
				}
			}
			
		}
	}
	
	private void revaluateState() {
		chasedBy = beingChased();
		if(chasedBy != null) {
			state = State.FLEE;
		}else if(food > dna.getMatingMinimum()) {
			state = State.SEEK_MATE; 
			return;
		}else {
			state = State.SEEK_FOOD;
		}
	}
	
	private void seekMate() {
		//TODO:  Write function to have animals to look for a mate;
		Animal a = checkMateInRange();
		if(a != null) {
			Vector to = a.getPos().sub(this.getPos());
			heading = to.normalized();
			state = State.GOING_TO_MATE;
			if(to.getMag() < dna.getRadius() + a.getDna().getRadius()) {
				mate(a);
			}
		}else {
			revaluateState();
		}
	}
	
	private Animal checkMateInRange() {
		for(Animal a : Game.animals) {
			if(a == this) continue;
			if(a.state != State.SEEK_MATE && a.state != State.GOING_TO_MATE) continue;
			
			Vector to = a.getPos().sub(this.getPos());
			if(to.getMag() < dna.getMatingMinimum()+dna.getRadius()+a.getDna().getRadius() &&
					this.state !=  State.MATING &&
					a.state != State.MATING) {
				return a;
			}
		}
		return null;
	}
	
	private void mate(Animal partner) {
		//TODO:  Write function for when animals mate.
		state = State.MATING;
		// Combine DNA
		DNA childDNA = this.dna.combine(this, partner);
		
		// Create new animal with dna
		Animal a = new Animal("Child", this.getPos(), childDNA, 50); // Food should be the sum of the dna.matingLoss
		Game.animals.add(a);
		
		// this.food -= dna.matingLoss
		// partner.food -= partner.dna.matingLoss
		this.food -= 50;
		partner.food -= 50;
		this.revaluateState();
		partner.revaluateState();
	}
	
	private void flee(Animal predator) {
		//TODO:  Write function to flee from a predator in a certain radius.
		Vector to = predator.getPos().sub(this.getPos());
		heading = to.rotateDegrees(180).normalized();
	}
	
	private void eatFood(Entity f) {
		state = State.EAT;
		// Turn towards food
		heading = (f.getPos().sub(this.getPos())).normalized();
		
		
		if(f instanceof Food) {
			food += f.getFood();
			Game.food.remove(f);
		}else {
			f.subFood(dna.getEatingRate());
			food += Math.min(dna.getEatingRate(), f.getFood());
		}
		
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

	public float getFood() {
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