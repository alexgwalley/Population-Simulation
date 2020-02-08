package entity;

import java.awt.Color;
import java.awt.Graphics;
import math.*;

public class Food extends Entity {
	
	public Food(Vector pos) {
		setPos(pos);
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillOval((int) getPos().get(0), (int) getPos().get(1), 10, 10);
		g.setColor(Color.BLACK);
		g.drawOval((int) getPos().get(0), (int) getPos().get(1), 10, 10);
	}

	@Override
	public void update() {}

}
