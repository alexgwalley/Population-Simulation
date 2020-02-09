package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Iterator;

import javax.swing.event.MouseInputListener;

import entity.Animal;
import math.Vector;

public class Camera implements MouseMotionListener, MouseWheelListener, MouseInputListener{

	
	private static float scrollSpeedDampener = 2;
	
	private Vector pos;
	private Vector prevMousePos;
	private float zoomAmt = 1;
	private float minZoomAmt = 1;
	private float maxZoomAmt = 50;
	//Animal toFollow;
	
	private float moveSpeed = 5;
	
	private boolean mouseDown = false;
	
	private boolean followingAnimal = false;
	private Animal animalToFollow;
	
	public Camera() {
		pos = Game.getScreenDimentions().scale(-1);
	}

	public void update() {
		if(!followingAnimal) return;
		zoomAmt = minZoomAmt;
		pos = animalToFollow.getPos();
		//System.out.println("Moving to animal");
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
			diff = diff.scale(zoomAmt);
			
			// Update position of camera
			pos = pos.sub(diff);
			prevMousePos = null;
			
			//System.out.printf("E: ( %f, %f ) Diff: ( %f, %f ) MousePos: ( %f, %f ) PrevPos: ( %f, %f ) Camera Position ( %f, %f )  \n", 
			//		(float)e.getX(), (float)e.getY(), diff.get(0), diff.get(1), mousePos.get(0), mousePos.get(1), prevMousePos.get(0), prevMousePos.get(1), pos.get(0), pos.get(1));
		}
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		followingAnimal = false;
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

	@Override
	public void mouseClicked(MouseEvent e) {
		
		Vector mousePos = new Vector(e.getX(), e.getY());
		
		Iterator<Animal> animalIterator = Game.animals.iterator();
		
		// Render all Animals
		try {
			while(animalIterator.hasNext()) {
				Animal a = animalIterator.next();
				float diam = a.getDna().getRadius()*2;
				Vector viewPos = toViewPos(a.getPos().sub(new Vector(diam*0.5f, diam*0.5f)));
			
				Vector diff = mousePos.sub(viewPos);
				
				System.out.printf(" ( %f, %f ) dist: %f \n", viewPos.get(0), viewPos.get(1), diff.getMag());
				
				if(diff.getMag() < a.getDna().getRadius()) {
					followAnimal(a);
					System.out.println("Clicked on animal");
					break;
				}
			}
		}catch (Exception ex) {}

		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}
