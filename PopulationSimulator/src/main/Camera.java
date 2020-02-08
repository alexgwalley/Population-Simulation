package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import math.Vector;

public class Camera implements MouseMotionListener{

	private Vector pos;
	private Vector prevMousePos;
	float zoomAmt;
	//Animal toFollow;
	
	public Camera() {
		pos = new Vector(0, 0);
	}
	
	public void update() {
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Vector mousePos = new Vector((float)e.getX(), (float)e.getY());
		if(prevMousePos == null || prevMousePos.getLength() <= 0) {
			prevMousePos = mousePos;
			return;
		}
		

		Vector diff = mousePos.sub(prevMousePos);
		
		// Update position of camera
		pos = pos.sub(diff);
		prevMousePos = mousePos;
		
		System.out.printf("E: ( %f, %f ) Diff: ( %f, %f ) MousePos: ( %f, %f ) PrevPos: ( %f, %f ) Camera Position ( %f, %f )  \n", 
				(float)e.getX(), (float)e.getY(), diff.get(0), diff.get(1), mousePos.get(0), mousePos.get(1), prevMousePos.get(0), prevMousePos.get(1), pos.get(0), pos.get(1));
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}
	
	public Vector getPos() {
		return pos;
	}
	
	
}
