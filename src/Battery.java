public class Battery extends Shape {
	int rotationId;
	int[] offset = new int[]{0, 0};

	public Battery(int id, int boundingBox, int capacity, int mass, int[][][] shapeData) {
		super(id, boundingBox, capacity, mass, shapeData);
	}

	public Battery(Shape s){
		super (s.id, s.boundingBox, s.capacity, s.mass, s.shapeData);
	}

	public int[][] getCellLocations() {
		int[][] locs = new int[shapeData[rotationId].length][2];

		for (int i = 0; i < shapeData[rotationId].length; i++) {
			locs[i][0] = shapeData[rotationId][i][0] + offset[0];
			locs[i][1] = shapeData[rotationId][i][1] + offset[1];
		}
		return locs;
	}

}
