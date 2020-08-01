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

	
	public void checkCanPlaceAndPlace(Grid grid){
		if (canPlace(grid)) {
			for (int j = 0; j < this.shapeData[this.rotationId].length; j++) {
				int currRow = this.shapeData[this.rotationId][j][0] + this.offset[0];
				int currCol = this.shapeData[this.rotationId][j][1] + this.offset[1];
				grid.grid[currRow][currCol] = this.id;

			}
		}
	}
	public boolean canPlace(Grid grid){
		// For each cell, first check that you can add it to the grid
		boolean canAdd = true;
		for (int j = 0; j < this.shapeData[this.rotationId].length; j++) {
			int currRow = this.shapeData[this.rotationId][j][0] + this.offset[0];
			int currCol = this.shapeData[this.rotationId][j][1] + this.offset[1];
			if (currRow < 0 || currRow >= grid.rows || currCol < 0 || currCol >= grid.columns || grid.grid[currRow][currCol] == -1) {
				canAdd = false;
				break;
			}
		}
		return canAdd;
	}
	
}
