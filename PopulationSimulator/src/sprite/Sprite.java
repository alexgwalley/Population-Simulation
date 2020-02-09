package sprite;

import java.awt.Graphics;
import java.io.File;

import math.Vector;

public abstract class Sprite {
	
	protected File path;
	protected float elapsedTime = 0;
	protected Vector pos;
	
	public abstract void update();
	public abstract void render(Graphics g);

}
