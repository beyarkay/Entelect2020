import java.io.FileNotFoundException;
import java.util.Arrays;

public class Boyd {

	public static void main(String[] args) {
		System.out.println("Hello world");

		for (int i = 0; i < Utils.inFiles.length; i++) {
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
		Battery[] batteries = new Battery[totalShapes];

		int[] currOffset = new int[]{0, 0};

		int maxBoundingBox = 0;
		for (int i = 0; i < totalShapes; i++) {
			System.out.println("id:" + bshapes[i].id + ", density:" + bshapes[i].density);
			batteries[i] = new Battery(bshapes[i]);
			batteries[i].rotationId = 0;
			batteries[i].offset = currOffset;
			currOffset[0] += batteries[i].boundingBox;
			maxBoundingBox = Math.max(maxBoundingBox, batteries[i].boundingBox);
			if (currOffset[0] > ps.columns) {
				currOffset[1] += maxBoundingBox;
				maxBoundingBox = 0;
				currOffset[0] = 0;
			}
		}
		return batteries;
	}


}
