package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import math.Vector;

public class Camera implements MouseMotionListener{

	Vector pos;
	Vector prevPos;
	float zoomAmt;
	//Animal toFollow;
	
	public Camera() {

	}
	
	public void update() {
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Vector mousePos = new Vector((float)e.getX(), (float)e.getY());
		if(prevPos == null || prevPos.getLength() <= 0) {
			prevPos = mousePos;
			return;
		}
		
		if(pos == null || pos.getLength() <= 0) {
			pos = prevPos;
		}
		
		Vector diff = mousePos.sub(prevPos);
		
		// Update position of camera
		prevPos = pos;
		pos = pos.add(diff);
		
		System.out.printf("E: ( %f, %f ) Diff: ( %f, %f ) MousePos: ( %f, %f ) PrevPos: ( %f, %f ) Camera Position ( %f, %f )  \n", 
				(float)e.getX(), (float)e.getY(), diff.get(0), diff.get(1), mousePos.get(0), mousePos.get(1), prevPos.get(0), prevPos.get(1), pos.get(0), pos.get(1));
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}
	
	
	
}
