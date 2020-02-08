package entity;

import java.awt.Graphics;

import math.*;

public abstract class Entity {
	
	private Vector pos;
	private Vector vel;
	
	public Vector getPos() {
		return this.pos;
	}
	
	public float getPos(int i) {
		return this.pos.get(i);
	}
	
	public Vector getVel() {
		return this.vel;
	}
	
	public float getVel(int i) {
		return this.vel.get(i);
	}
	
	public void setPos(Vector pos) {
		this.pos = pos;
	}
	
	public void setPos(int i, float pos) {
		this.pos.set(i, pos);
	}
	
	public void setVel(Vector v) {
		this.vel = v;
	}

	public void setVel(int i, float vel) {
		this.vel.set(i, vel);
	}

	public abstract void render(Graphics g);
	public abstract void update();

}
