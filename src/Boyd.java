import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

public class Boyd {

	public static void main(String[] args) {
		System.out.println("Hello world");

		for (int i = 3; i < Utils.inFiles.length; i++) {
			System.out.println("Calculating: " + Utils.inFiles[i]);
			try {
				Utils.ProblemSpecification problemSpec = Utils.readInput("inputs/shapes_file.json", Utils.inFiles[i]);

				Battery[] batteries = slider(problemSpec, Utils.outFiles[i]);
				Utils.writeFile(Utils.outFiles[i], batteries);
//				break;

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	public static Battery[] greedy(Utils.ProblemSpecification ps) {

		// Read in PS and convert it to shapes
		Shape[] shapes = ps.shapes;
		int totalShapes = 0;
		for (int i = 0; i < ps.shapeCounts.length; i++) {
			totalShapes += ps.shapeCounts[i];
		}
		BShape[] bshapes = new BShape[totalShapes];
		int counter = 0;
		for (int i = 0; i < ps.shapeCounts.length; i++) {
			for (int j = 0; j < ps.shapeCounts[i]; j++) {
				bshapes[counter] = new BShape(
						shapes[i].id,
						shapes[i].boundingBox,
						shapes[i].capacity,
						shapes[i].mass,
						shapes[i].shapeData
				);
				counter++;
			}
		}
		Arrays.sort(bshapes);

		Grid grid = new Grid(ps);
		ArrayList<Battery> batteries = new ArrayList<>();
		int[] currOffset = new int[]{0, 0};
		int currRotation = 0;

		int maxBoundingBox = 0;
		for (int i = 0; i < totalShapes; i++) {
//			System.out.println("id:" + bshapes[i].id + ", density:" + bshapes[i].density + ", bbox:" + bshapes[i].boundingBox);
			// Check that the current shape can be places at the current offset
			boolean canAdd = true;
			for (int j = 0; j < bshapes[i].shapeData[currRotation].length; j++) {
				int currRow = bshapes[i].shapeData[currRotation][j][0] + currOffset[0];
				int currCol = bshapes[i].shapeData[currRotation][j][1] + currOffset[1];
				if (currRow < 0 || currRow >= ps.rows || currCol < 0 || currCol >= ps.columns || grid.grid[currRow][currCol] == -1) {
					canAdd = false;
					break;
				}
			}
			if (canAdd) {
				// Now actually add the shape in currRotation, currOffset to the grid & battery ArrayList
				for (int j = 0; j < bshapes[i].shapeData[currRotation].length; j++) {
					int currRow = bshapes[i].shapeData[currRotation][j][0] + currOffset[0];
					int currCol = bshapes[i].shapeData[currRotation][j][1] + currOffset[1];
					grid.grid[currRow][currCol] = bshapes[i].id;
				}
				Battery currBattery = new Battery(bshapes[i]);
				currBattery.rotationId = currRotation;
				currBattery.offset[0] = currOffset[0];
				currBattery.offset[1] = currOffset[1];
				batteries.add(currBattery);
//				System.out.println(grid.toCSV());
			}
			// Update the offset such that the next shape will fit

			if (batteries.size() > 0) {
				currOffset[0] += batteries.get(batteries.size() - 1).boundingBox;
				maxBoundingBox = Math.max(maxBoundingBox, batteries.get(batteries.size() - 1).boundingBox);
			}
			if (currOffset[0] >= ps.rows) {
				currOffset[1] += maxBoundingBox;
				maxBoundingBox = 0;
				currOffset[0] = 0;
			}
			if (currOffset[1] >= ps.columns) {
				break;
			}

		}
		return batteries.toArray(new Battery[0]);
	}


	public static Battery[] slider(Utils.ProblemSpecification ps, String filename) {

		// Read in PS and convert it to shapes
		Shape[] shapes = ps.shapes;
		int totalShapes = 0;
		for (int i = 0; i < ps.shapeCounts.length; i++) {
			totalShapes += ps.shapeCounts[i];
//			System.out.println("id:" + ps.shapes[i].id + " count:" + ps.shapeCounts[i]);
		}
//		System.out.println();
		BShape[] bshapes = new BShape[totalShapes];
		int counter = 0;
		for (int i = 0; i < ps.shapeCounts.length; i++) {
			for (int j = 0; j < ps.shapeCounts[i]; j++) {
				bshapes[counter] = new BShape(
						shapes[i].id,
						shapes[i].boundingBox,
						shapes[i].capacity,
						shapes[i].mass,
						shapes[i].shapeData
				);
				counter++;
			}
		}
		Arrays.sort(bshapes);

		Grid grid = new Grid(ps);
		ArrayList<Battery> batteries = new ArrayList<>();
		// for each shape
		int lastId = -1;
		int[] lastOffset = new int[]{0, 0};
		long start = System.currentTimeMillis();
		long t = System.currentTimeMillis();
		long ttotal = 1;
		float ignoreme = 0.2f;
		for (int i = 0; i < totalShapes; i++) {
			boolean placed = false;
			Shape currShape = bshapes[i];
			if (i >= totalShapes*ignoreme) {
				ignoreme += 0.2;
				System.out.println("Checked " + i + "/" + totalShapes + "(" + Math.round(((float) i) / totalShapes * 100) + ")");
				ttotal += System.currentTimeMillis()-t;
				System.out.println("Taken " + ttotal/1000 + "s");
				System.out.println("ETA " + (float) (ttotal) / ((float) (i + 1) / (float) totalShapes));
			}//			System.out.println("id:" + currShape.id);

			if (i % 100 == 0) {
				int shapesComplete = totalShapes - i;
				float millisPerShape = (System.currentTimeMillis() - start) / ((float) i);
				float doneIn = shapesComplete * millisPerShape;
				double doneInMinutes = doneIn / 60.0;
				System.out.println("Done " + i + "/" + totalShapes + " (" + Math.round(((float) i) / totalShapes * 100) + "%) done in: " + doneInMinutes);
			}
			if (i % 10000 == 0) {
				Utils.writeFile(filename, batteries.toArray(new Battery[0]));
			}
//			if (lastId == currShape.id) {
////				System.out.println("Skipping");
//				continue;
//			}
			//for each offset row
			for (int r = lastOffset[0]; r < ps.rows + lastOffset[0]; r++) {
				int rmod = r % ps.rows;
				// for each offset col
				for (int c = lastOffset[1]; c < ps.columns + lastOffset[1]; c++) {
					int cmod = c % ps.columns;
					// for each rotation
					for (int rot = 0; rot < 4; rot++) {
						// check if the current shape can fit
						boolean collision = false;
						for (int cellIndex = 0; cellIndex < currShape.shapeData[rot].length; cellIndex++) {
							int currRow = currShape.shapeData[rot][cellIndex][0] + rmod;
							int currCol = currShape.shapeData[rot][cellIndex][1] + cmod;
							if (currRow < 0 || currRow >= ps.rows || currCol < 0 || currCol >= ps.columns || grid.grid[currRow][currCol] != 0) {
								collision = true;
								break;
							}
						}
						if (!collision) {       // there is no collision and we can place the battery
							for (int cellIndex = 0; cellIndex < currShape.shapeData[rot].length; cellIndex++) {
								int currRow = currShape.shapeData[rot][cellIndex][0] + rmod;
								int currCol = currShape.shapeData[rot][cellIndex][1] + cmod;
								grid.grid[currRow][currCol] = bshapes[i].id;
							}
							Battery currBattery = new Battery(bshapes[i]);
							currBattery.rotationId = rot;
							currBattery.offset[0] = rmod;
							currBattery.offset[1] = cmod;
							batteries.add(currBattery);
							placed = true;
							lastOffset[0] = rmod;
							lastOffset[1] = cmod;
							break;
						}
					}
					if (placed) {
						break;
					}
				}
				if (placed) {
					break;
				}
			}
			if (placed) {
				lastId = -1;
			} else {
				lastId = currShape.id;
			}
		}
		return batteries.toArray(new Battery[0]);
	}


}
