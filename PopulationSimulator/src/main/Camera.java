package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import math.Vector;

public class Camera implements MouseMotionListener, MouseWheelListener{

	
	private static float scrollSpeedDampener = 2;
	
	private Vector pos;
	private Vector prevMousePos;
	private float zoomAmt = 1;
	private float minZoomAmt = 1;
	private float maxZoomAmt = 50;
	//Animal toFollow;
	
	private boolean mouseDown = false;
	
	public Camera() {
		pos = new Vector(0, 0);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
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
			diff = diff.scale(1/zoomAmt);
			
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
	
	public float getZoomAmount(){
		return zoomAmt;
	}
	
	public Vector getPos() {
		return pos;
	}
	
	public Vector toViewPos(Vector pos) {
		return pos.sub(this.pos).scale(1/zoomAmt);
	}
	
	
}
