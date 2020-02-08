package entity;

import math.*;

public abstract class Entity {
	
	private Vector pos, vel;
	
	public Vector getPos() {
		return this.pos;
	}
	
	public Vector getVel() {
		return this.vel;
	}
	
	public abstract void render(Graphics g);

}
