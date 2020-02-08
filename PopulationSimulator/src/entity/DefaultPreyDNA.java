package entity;

import java.awt.Color;
import java.util.HashMap;

public class DefaultPreyDNA extends DNA {

	private static HashMap<String, Integer> f = new HashMap<String, Integer>(){
		{put("Food", 100);}
	};
	
	public DefaultPreyDNA() {
		super("Prey", Color.WHITE, f, 2.0f, 50, 5, 30, 0.0f, 10, 30, 70);
	}

}
