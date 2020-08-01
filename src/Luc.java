import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Luc {

	public static void main(String[] args) {
		System.out.println("Hello world");

		Shape[] shapes = Utils.readShapesFile();
		assert shapes != null;

		System.out.println("bye");

//		for (int i = 0; i < Utils.inFiles.length; i++) {
//			ArrayList<String> lines = Utils.readInput(Utils.inFiles[i]);
//			// Process the file with an algorithm, machine learning, artificial intelligence and Superior Intellect
//			Utils.writeFile(Utils.outFiles[i], lines);
//		}
	}
}

