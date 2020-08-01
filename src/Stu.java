import java.io.FileNotFoundException;

import java.util.Arrays;

public class Stu {

	public static void main(String[] args) throws FileNotFoundException {
		System.out.println("Hello world");

		Shape[] shapes = Utils.readShapesFile("inputs/shapes_file.json");
		Utils.ProblemSpecification problemSpec = Utils.readInput("inputs/shapes_file.json", "inputs/grid_1.input");

		System.out.println(Arrays.toString(shapes));

//		for (int i = 0; i < Utils.inFiles.length; i++) {
//			ArrayList<String> lines = Utils.readInput(Utils.inFiles[i]);
//			// Process the file with an algorithm, machine learning, artificial intelligence and Superior Intellect
//			Utils.writeFile(Utils.outFiles[i], lines);
//		}
	}
}
