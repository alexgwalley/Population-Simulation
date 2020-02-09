package sprite;

import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import main.Game;
import math.Vector;

public class Heart extends Sprite {
	
	private Vector vel;
	
	public Heart(Vector pos) {
		this.path = new File("res/heart.png");
		this.pos = pos;
		this.vel = new Vector(new Random().nextFloat()*2f-1f, 1);
	}

	@Override
	public void update() {
		pos.add(vel.scale(Game.dt));
		this.elapsedTime += Game.dt;
		if(elapsedTime >= 3000) Game.sprites.remove(this);
	}

	@Override
	public void render(Graphics g) {
		try {
			Vector viewPos = Game.camera.toViewPos(pos);
			g.drawImage(ImageIO.read(path), (int) viewPos.get(0), (int) viewPos.get(1), null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
