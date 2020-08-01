import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Luc {

	public static void main(String[] args) {
		System.out.println("Hello world");


		Utils.ProblemSpecification ps = Utils.readInput(1);
		assert ps != null;
		int sumOfShapes = 0;
		for (int i : ps.shapeCounts) {
			sumOfShapes += 1;
		}
		Battery[] batteries = new Battery[sumOfShapes];
		Shape[] shapes = ps.shapes;
		int bCnt = 0;
		for (int i = 0; i < ps.shapeCounts.length; i++) {
			for(int j =0; j < ps.shapeCounts[i]; j++){
				batteries[bCnt] = new Battery(shapes[i]);
				bCnt++;
			}
		}

		Grid grid = new Grid(ps);
		System.out.println(grid.toCSV());

		System.out.println("bye");

//		for (int i = 0; i < Utils.inFiles.length; i++) {
//			ArrayList<String> lines = Utils.readInput(Utils.inFiles[i]);
//			// Process the file with an algorithm, machine learning, artificial intelligence and Superior Intellect
//			Utils.writeFile(Utils.outFiles[i], lines);
//		}
	}
}

