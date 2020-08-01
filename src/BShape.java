public class BShape extends Shape implements Comparable {
	public float density;

	public BShape(int id, int boundingBox, int capacity, int mass, int[][][] shapeData) {
		super(id, boundingBox, capacity, mass, shapeData);
		this.density = ((float) capacity) / ((float) mass);
	}

	@Override
	public int compareTo(Object other) {
		// Sort from highest density to lowest
		return -Float.compare(this.density, ((BShape) other).density);
	}
}
