package math;

public class Vector {
	
	private float[] indices;
	
	public Vector(float... indices) {
		this.indices = indices;
	}
	
	public float get(int i) {
		return indices[i];
	}
	public float[] getIndices() {
		return indices;
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
	
	public float dot(Vector v) {
		if(v.indices.length != this.indices.length) return 0;
		float out = 0;
		for(int i = 0; i < indices.length; i++)
			out += indices[i]*v.get(i);
		return out;
	}

}
