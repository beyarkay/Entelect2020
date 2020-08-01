import java.util.Arrays;

public class Shape {

	public int id, boundingBox, capacity, mass;
	public int[][][] shapeData;

	public Shape(int id, int boundingBox, int capacity, int mass, int[][][] shapeData) {
		this.id = id;
		this.boundingBox = boundingBox;
		this.capacity = capacity;
		this.mass = mass;
		this.shapeData = shapeData; //[rotations][cells][2]
	}

	@Override
	public String toString() {
		return "Shape{" +
				"id=" + id +
				", boundingBox=" + boundingBox +
				", capacity=" + capacity +
				", mass=" + mass +
				", shapeData=" + Arrays.toString(shapeData) +
				'}';
	}
}
