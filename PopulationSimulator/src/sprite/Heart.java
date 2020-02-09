package sprite;

import java.awt.Graphics;
import java.awt.Image;
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
		this.vel = new Vector(new Random().nextFloat()*0.2f-0.1f, -0.1f);
		Game.sprites.add(this);
	}

	@Override
	public void update() {
		pos = pos.add(vel.scale(Game.dt));
		this.elapsedTime += Game.dt;
		if(elapsedTime >= 1000) {
			Game.sprites.remove(this);
		}
	}

	@Override
	public void render(Graphics g) {
		try {
			Vector viewPos = Game.camera.toViewPos(pos);
			Image img = ImageIO.read(path).getScaledInstance((int) (100/Game.camera.getZoomAmount()), (int) (100/Game.camera.getZoomAmount()), Image.SCALE_DEFAULT);
			g.drawImage(img, (int) viewPos.get(0), (int) viewPos.get(1), null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
