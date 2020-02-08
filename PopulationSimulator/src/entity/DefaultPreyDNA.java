package entity;

import java.awt.Color;
import java.util.HashMap;

public class DefaultPreyDNA extends DNA {

	private HashMap<String, Integer> food = new HashMap<String, Integer>(){
		put("Food", 100);
	};
	
	public DefaultPreyDNA() {
		super("Prey", COLOR.BROWN, food, 2.0f, 50, 5, 30, 0, 10, 30, 70);
	}

}
