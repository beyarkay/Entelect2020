import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Utils {
	public static String[] inFiles = new String[]{
			"inputs/grid_1.input",
			"inputs/grid_2.input",
			"inputs/grid_3.input"
	};
	public static String[] outFiles = new String[]{
			"outputs/map_1.txt",
			"outputs/map_2.txt",
			"outputs/map_3.txt"
	};


	public static void writeFile(String inFile, ArrayList<String> lines) {
		try {
			FileWriter writer = new FileWriter(inFile);
			for (int i = 0; i < lines.size(); i++) {
				writer.write(lines.get(i) + "\n");
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

		for (int row = 0; row < grid.grid.length; row++) {
			for (int col = 0; col < grid.grid[row].length; col++) {
				if (col != 0) {
					returnable += 10;
				} else {
					if (!grid.checkAdjacentEmpty(row, col)) {
						returnable -= 4;
					} else {
						// Adjacent whitespaces must be followed
						int adjWhiteSpace = grid.countAdjacentWhitespaces(row, col);
					}
				}
			}
		}

		return returnable;
	}


	public static void greedy() {

	}
}
