package entity;

import java.awt.Color;
import java.awt.Graphics;
import math.*;

public class Animal extends Entity{
	
	private DNA dna;
	private int food;
	private State state = State.SEEK_MATE;
	private String name = "";
	private int timeAlive = 0;
	
	public Animal(Vector pos, DNA dna, int food) {
		setPos(pos);
		this.dna = dna;
		this.food = food;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillOval((int) getPos(0), (int) getPos(1), 30, 30);
		g.setColor(Color.BLACK);
		g.drawOval((int) getPos(0), (int) getPos(1), 30, 30);
		g.drawString(name, (int) getPos(0), (int) getPos(1) - 20);
	}

	@Override
	public void update() {
		setPos(getPos().add(this.getVel().scale(1f/60f)));
	}
	
	private void seekFood() {
		//TODO:  Write function to have animals to look for food.
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
	
}