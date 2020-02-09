package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Iterator;

import javax.swing.event.MouseInputListener;

import entity.Animal;
import entity.State;
import gui.LeaderboardButton;
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
		pos = Game.getWorldDimentions().sub(Game.getScreenDimentions().scale(2));
	}

	public void update() {
		if(!followingAnimal) return;
		zoomAmt = minZoomAmt;
		pos = animalToFollow.getPos();
		//System.out.println("Moving to animal");
	}
	
	public void render(Graphics g) {
		if(!followingAnimal) return;
		int x = (int)Game.getScreenDimentions().get(0)-250;
		int y = (int)Game.getScreenDimentions().get(1)-300;
		int padding = 20;
		g.setColor(new Color(55, 55, 55, 175));
		g.fillRect(x, y, 250, 300);
		/*private Species species;
		private Color color;
		//The String is for the species that is considered food, 
		//and the Integer is how low the food has to be before being willing to eat it.
		private HashMap<String, Integer> food;
		private float fieldOfViewAngle;
		private int fieldOfViewRadius;
		private int moveSpeed;
		private int radius;
		private float mutationRate;
		private int eatingRate;
		private int fleeRadius;
		private int matingMinimum;*/
		g.setColor(Color.WHITE);
		y+=padding*1.25;
		x+=padding;
		g.drawString("Species: " + animalToFollow.getDna().getSpecies().toString(), x, y);
		y += padding;
		g.drawString("Time Alive: " + (int)animalToFollow.getTimeAlive(), x, y);
		y += padding;
		g.drawString("Food: " + animalToFollow.getFood(), x, y);
		y += padding;
		g.drawString("State: " + animalToFollow.getState(), x, y);
		y += padding;
		g.drawString("FOVA: " + animalToFollow.getDna().getFieldOfViewAngle(), x, y);
		y += padding;
		g.drawString("FOVR: " + animalToFollow.getDna().getFieldOfViewRadius(), x, y);
		y += padding;
		g.drawString("Move Speed: " + animalToFollow.getDna().getMoveSpeed(), x, y);
		y += padding;
		g.drawString("Eating Rate: " + animalToFollow.getDna().getEatingRate(), x, y);
		y += padding;
		g.drawString("Flee Radius: " + animalToFollow.getDna().getFleeRadius(), x, y);
		y += padding;
		g.drawString("Mating Minimum: " + animalToFollow.getDna().getMatingMinimum(), x, y);
		
		
		
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
		for(LeaderboardButton b : Game.leaderboard)
			b.handleHovered(new Vector(e.getX(), e.getY()));
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

		
		Iterator<LeaderboardButton> lbIter = Game.leaderboard.iterator();
		
		while(lbIter.hasNext()) {
			lbIter.next().handleMouseClick();
		}
		
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
