package generator;

import java.util.Random;

public class PercentGenerator {
	
	private static final Random r = new Random();
	
	public static final float newPercent() {
		float x = r.nextFloat();
		return (float) Math.cos(Math.PI*x);
	}
	
	public static final float relPercent(float stdv) {
		return (float) (r.nextGaussian())*stdv+1f;
	}

}
