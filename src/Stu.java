import java.util.ArrayList;

public class Stu {

	public static void main(String[] args) {
		System.out.println("Hello world");

		for (int i = 0; i < Utils.inFiles.length; i++) {
			ArrayList<String> lines = Utils.readInput(Utils.inFiles[i]);
			// Process the file with an algorithm, machine learning, artificial intelligence and Superior Intellect
			Utils.writeFile(Utils.outFiles[i], lines);
		}
	}
}
