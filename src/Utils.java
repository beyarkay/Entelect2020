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


	public static ArrayList<String> readInput(String fileName) {
		ArrayList<String> lines = new ArrayList<>();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(fileName));

//		String[] firstLineParts = br.readLine().split(",");

			String line;
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
			br.close();
			return lines;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
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
