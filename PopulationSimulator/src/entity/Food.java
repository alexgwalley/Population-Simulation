package entity;

import java.awt.Color;
import java.awt.Graphics;

public class Food extends Entity {
	
	public Food() {
		
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillOval((int) getPos().get(0), (int) getPos().get(1), 10, 10);
		g.setColor(Color.BLACK);
		g.drawOval((int) getPos().get(0), (int) getPos().get(1), 10, 10);
	}

}
