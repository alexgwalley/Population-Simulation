package math;

public class Vector {
	
	private float[] indices;
	
	public Vector(float... indices) {
		this.indices = indices;
	}
	
	public float get(int i) {
		return indices[i];
	}
	
	public float set(int i, float value) {
		return indices[i] = value;
	}
	
	public float[] getIndices() {
		return indices;
	}
	
	public int getLength() {
		return indices.length;
	}
	
	public Vector add(Vector addend) {
		if(addend.indices.length != this.indices.length) return null;
		float[] out = new float[indices.length];
		for(int i = 0; i < indices.length; i++)
			out[i] = indices[i]+addend.get(i);
		return new Vector(out);
	}
	
	public Vector sub(Vector addend) {
		if(addend.indices.length != this.indices.length) return null;
		float[] out = new float[indices.length];
		for(int i = 0; i < indices.length; i++)
			out[i] = indices[i]-addend.get(i);
		return new Vector(out);
	}
	
	public Vector scale(float scalar) {
		float[] out = new float[indices.length];
		for(int i = 0; i < indices.length; i++)
			out[i] = scalar*indices[i];
		return new Vector(out);
	}
	
	public Vector scale(Vector addend) {
		if(addend.indices.length != this.indices.length) return null;
		float[] out = new float[indices.length];
		for(int i = 0; i < indices.length; i++)
			out[i] = addend.get(i)*indices[i];
		return new Vector(out);
	}
	
	public float dot(Vector v) {
		if(v.indices.length != this.indices.length) return 0;
		float out = 0;
		for(int i = 0; i < indices.length; i++)
			out += indices[i]*v.get(i);
		return out;
	}
	
	public float getMag() {
		float mag = 0;
		for(float i : indices) {
			mag += i*i;
		}
		return (float) Math.sqrt(mag);
	}
	
	public Vector normalized() {
		float mag = this.getMag();
		float[] out = new float[indices.length];
		for(int i = 0; i < this.indices.length; i++) {
			out[i] = indices[i]/mag;
		}
		return new Vector(out);
	}
	
	public float getAngleDegrees() {
		if(this.indices.length != 2) return -1000;
		return (float)Math.toDegrees(Math.atan2(indices[1], indices[0]));
	}
	
	public float getAngleRadians() {
		if(this.indices.length != 2) return -1000;
		return (float)(Math.atan2(indices[1], indices[0]));
	}
	
	public Vector rotateDegrees(float angle) {
		angle = (float)Math.toRadians(angle);
		float[] out = new float[indices.length];
		out[0] = (float) (Math.cos(angle) * this.get(0) + Math.sin(angle) * this.get(1));
		out[1] = (float) (Math.cos(angle) * this.get(1) - Math.sin(angle) * this.get(0));
		return new Vector(out);
	}

	public float angleBetweenDegrees(Vector b) {
		float n = this.dot(b);
		n /= (this.getMag() * b.getMag());
		n = (float) Math.acos(n);
		return (float) Math.toDegrees(n);
	}
}
