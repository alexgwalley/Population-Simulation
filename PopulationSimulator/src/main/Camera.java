package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import entity.Animal;
import math.Vector;

public class Camera implements MouseMotionListener, MouseWheelListener{

	
	private static float scrollSpeedDampener = 2;
	
	private Vector pos;
	private Vector prevMousePos;
	private float zoomAmt = 1;
	private float minZoomAmt = 1;
	private float maxZoomAmt = 50;
	//Animal toFollow;
	
	private float moveSpeed = 3;
	
	private boolean mouseDown = false;
	
	private boolean followingAnimal = false;
	private Animal animalToFollow;
	
	public Camera() {
		pos = Game.getScreenDimentions().scale(-1);
	}

	public void update() {
		if(!followingAnimal) return;
		pos = animalToFollow.getPos().sub(Game.getScreenDimentions().scale((float) 0.5));
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		followingAnimal = false;
		
		Vector mousePos = new Vector((float)e.getX(), (float)e.getY());
		if(mouseDown == false && e.getButton() == MouseEvent.BUTTON1) {
			mouseDown = true;
			prevMousePos = mousePos;
			return;
		}

		if(prevMousePos == null || prevMousePos.getLength() <= 0) {
			prevMousePos = mousePos;
			return;
		}
		
		if(mouseDown == true) {

			Vector diff = mousePos.sub(prevMousePos);
			diff.scale(moveSpeed);
			//diff = diff.scale(1/zoomAmt);
			
			// Update position of camera
			pos = pos.sub(diff);
			prevMousePos = null;
			
			//System.out.printf("E: ( %f, %f ) Diff: ( %f, %f ) MousePos: ( %f, %f ) PrevPos: ( %f, %f ) Camera Position ( %f, %f )  \n", 
			//		(float)e.getX(), (float)e.getY(), diff.get(0), diff.get(1), mousePos.get(0), mousePos.get(1), prevMousePos.get(0), prevMousePos.get(1), pos.get(0), pos.get(1));
		}
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		zoomAmt += e.getUnitsToScroll() / scrollSpeedDampener;
		if(zoomAmt < minZoomAmt) zoomAmt = minZoomAmt;
		if(zoomAmt > maxZoomAmt) zoomAmt = maxZoomAmt;
	}
	

	@Override
	public void mouseMoved(MouseEvent e) {
		if(mouseDown == true && e.getButton() == MouseEvent.NOBUTTON) mouseDown = false;
	}
	
	public void followAnimal(Animal a) {
		followingAnimal = true;
		animalToFollow = a;
	}
	
	public float getZoomAmount(){
		return zoomAmt;
	}
	
	public Vector getPos() {
		return pos;
	}
	
	public Vector toViewPos(Vector pos) {
		return pos.sub(this.pos).scale(1/zoomAmt).add(Game.getScreenDimentions().scale(1f/2f));
	}
	
	
}
