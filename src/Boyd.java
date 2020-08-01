import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

public class Boyd {

	public static void main(String[] args) {
		System.out.println("Hello world");

		for (int i = 0; i < Utils.inFiles.length; i++) {
			System.out.println("Calculating: " + Utils.inFiles[i]);
			try {
				Utils.ProblemSpecification problemSpec = Utils.readInput("inputs/shapes_file.json", Utils.inFiles[i]);

				Battery[] batteries = greedy(problemSpec);
				Utils.writeFile(Utils.outFiles[i], batteries);
//				break;

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	public static Battery[] greedy(Utils.ProblemSpecification ps) {

		// Read in PS and
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


}
