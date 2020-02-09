package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardManager implements KeyListener{

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Key Pressed");
		System.out.println(e.getKeyCode());
		// TODO Auto-generated method stub
		if(e.getKeyCode() == 39) { // Right Arrow
			Game.increaseSimSpeed();
		}
		if(e.getKeyCode() == 37) { // Left Arrow
			Game.decreaseSimSpeed();
		}
//		if(e.getKeyCode() == 32) { // Spacebar
//			if(Game.running)
//				Game.pause();
//			else
//				Game.play();
//		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
