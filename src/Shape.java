public class Shape {

	public int id, boundingBox, capacity, mass;
	public int[][][] shapeData;

	public Shape(int id, int boundingBox, int capacity, int mass, int[][][] shapeData) {
		this.id = id;
		this.boundingBox = boundingBox;
		this.capacity = capacity;
		this.mass = mass;
		this.shapeData = shapeData;
	}
}
