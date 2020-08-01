import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Utils {
	public static String[] inFiles = new String[]{
			"inputs/grid_1.input",
			"inputs/grid_2.input",
			"inputs/grid_3.input",
			"inputs/grid_4.input",
			"inputs/grid_5.input"
	};
	public static String[] outFiles = new String[]{
			"outputs/out_1.txt",
			"outputs/out_2.txt",
			"outputs/out_3.txt",
			"outputs/out_4.txt",
			"outputs/out_5.txt"
	};


	public static void writeFile(String outFile, Battery[] batteries) {
		try {
			FileWriter writer = new FileWriter(outFile);
			for (int i = 0; i < batteries.length; i++) {
				Battery currBattery = batteries[i];
				StringBuilder coordsString = new StringBuilder();

				for (int j = 0; j < currBattery.shapeData[currBattery.rotationId].length; j++) {
					int[] currCell = currBattery.shapeData[currBattery.rotationId][j];
					coordsString.append("|").append(currCell[0]).append(",").append(currCell[1]);
				}

				String shapeString = batteries[i].id + coordsString.toString();
				writer.write(shapeString + "\n");
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Shape[] readShapesFile() {
		try {
			return readShapesFile("inputs/shapes_file.json");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Shape[] readShapesFile(String fileName) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File(fileName)).useDelimiter("\\Z");
		String jsonString = scanner.next();
		scanner.close();
		JSONObject obj = new JSONObject(jsonString);

		JSONArray shapesJSON = obj.getJSONArray("shapes");
		Shape[] shapes = new Shape[shapesJSON.length()];

		for (int s = 0; s < shapesJSON.length(); s++) {
			JSONObject shapeJSON = shapesJSON.getJSONObject(s);

			JSONArray orientationsJSON = shapeJSON.getJSONArray("orientations");

			//Get number of points in the shape
			int shapeMass = orientationsJSON.getJSONObject(0).getJSONArray("cells").length();

			//Make the shapeData array
			int[][][] shapeData = new int[orientationsJSON.length()][shapeMass][2];
			for (int i = 0; i < orientationsJSON.length(); i++) {
				JSONObject orientationJSON = orientationsJSON.getJSONObject(i);
				JSONArray cellsJSON = orientationJSON.getJSONArray("cells");
				for (int j = 0; j < cellsJSON.length(); j++) {
					JSONArray cellJSON = cellsJSON.getJSONArray(j);

					shapeData[i][j][0] = cellJSON.getInt(0);
					shapeData[i][j][1] = cellJSON.getInt(1);
				}
			}

			shapes[s] = new Shape(
					shapeJSON.getInt("shape_id"),
					shapeJSON.getInt("bounding_box"),
					shapeJSON.getInt("capacity"),
					shapeMass,
					shapeData
			);
		}

		return shapes;
	}

	public static ProblemSpecification readInput(String shapesFileName, String fileName) throws FileNotFoundException {
		Shape[] shapes = Utils.readShapesFile(shapesFileName);
		Scanner scanner = new Scanner(new File(fileName)).useDelimiter("\\D"); //any non-digit is a delimiter
		int rows, columns, nShapes, nBlockedCells;
		rows = scanner.nextInt();
		columns = scanner.nextInt();
		nShapes = scanner.nextInt();
		nBlockedCells = scanner.nextInt();

		Shape[] existing_shapes = new Shape[nShapes];
		int[] shapeCounts = new int[nShapes];

		for (int i = 0; i < nShapes; i++) {
			existing_shapes[i] = shapes[scanner.nextInt() - 1];
			shapeCounts[i] = scanner.nextInt();
		}

		int[][] blockedCells = new int[nBlockedCells][2];

		for (int i = 0; i < nBlockedCells; i++) {
			blockedCells[i][0] = scanner.nextInt();
			blockedCells[i][1] = scanner.nextInt();
		}

		scanner.close();

		return new ProblemSpecification(
				rows,
				columns,
				existing_shapes,
				shapeCounts,
				blockedCells
		);
	}

	public static class ProblemSpecification {
		public int rows, columns;

		//Shapes and shapeCounts are parallel arrays (there are shapeCounts[i] instances of shape[i] available)
		public Shape[] shapes;
		public int[] shapeCounts;

		public int[][] blockedCells;

		public ProblemSpecification(int rows, int columns, Shape[] shapes, int[] shapeCounts, int[][] blockedCells) {
			this.rows = rows;
			this.columns = columns;
			this.shapes = shapes;
			this.shapeCounts = shapeCounts;
			this.blockedCells = blockedCells;
		}
	}

	public static int calculateScore(Grid grid) {
		int returnable = 0;
		int filled = 0;
		for (int row = 0; row < grid.grid.length; row++) {
			for (int col = 0; col < grid.grid[row].length; col++) {
				if (col != 0) {
					filled++;
					returnable += 10;
				} else {
					if (!grid.checkAdjacentEmpty(row, col)) {
						returnable -= 4;
					} else {
						// Adjacent whitespaces must be followed
						int adjWhiteSpace = grid.countAdjacentWhitespaces(row, col);
						returnable -= adjWhiteSpace * 2;
					}
				}
			}
		}

		float totalCapacity = 1;
		float scoreModifier = totalCapacity/filled;

		return (int) (returnable*scoreModifier);
	}


	public static void greedy() {

	}
}
