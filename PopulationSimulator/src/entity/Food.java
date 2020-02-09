package entity;

import java.awt.Color;
import java.awt.Graphics;

import main.Game;
import math.*;

public class Food extends Entity {
	private String species;

	public Food(Vector pos) {
		setPos(pos);
		species = "food";
	}
	
	public Food(Vector pos, String spec) {
		setPos(pos);
		species = spec;
	}
	
	public Food(Vector pos, int f) {
		setPos(pos);
		food = f;
		species = "food";
		
	}

	@Override
	public void render(Graphics g) {
		Vector viewPos = Game.camera.toViewPos(getPos());
		
		g.setColor(Color.GREEN);
		g.fillOval((int) viewPos.get(0), (int) viewPos.get(1), (int) (10/Game.camera.getZoomAmount()), (int) (10/Game.camera.getZoomAmount()));
		g.setColor(Color.BLACK);
		g.drawOval((int) viewPos.get(0), (int) viewPos.get(1), (int) (10/Game.camera.getZoomAmount()), (int) (10/Game.camera.getZoomAmount()));
	}

	@Override
	public void update() {}
	
	public String getSpecies() {
		return species;
	}

	public void setFood(int food) {
		this.food = food;
	}

}
