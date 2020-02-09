package gui;

import java.awt.Color;
import java.awt.Graphics;

import entity.Animal;
import main.Game;
import math.Vector;

public class LeaderboardButton {
	
	private Animal animal;
	private int x;
	private int y;
	private int width;
	private int height;
	private int number;
	
	private boolean hoveredOver;
	
	public LeaderboardButton(Animal a, int number, int x, int y, int width, int height) {
		animal = a;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.number = number;
	}
	
	public void render(Graphics g) {
		if(animal == null) return;
		if(!hoveredOver)
			g.setColor(new Color(55, 55, 55, 175));
		else
			g.setColor(new Color(111, 111, 111, 175));
		g.fillRect(x, (int) (y), width, height);
		g.setColor(new Color(255, 255, 255));
		g.drawString(number + ". " + animal.getName(), x+20, y+height/2);
	}
	
	public void setAnimal(Animal a) {
		animal = a;
	}
	
	public void handleHovered(Vector mousePos) {
		if(mousePos.get(0) < x + width && mousePos.get(0) > x
				&& mousePos.get(1) < y + height && mousePos.get(1) > y) {
			hoveredOver = true;
		}else
			hoveredOver = false;	
	}
	
	public void handleMouseClick() {
		if(hoveredOver) {
			Game.camera.followAnimal(animal);
		}
	}

	

}
