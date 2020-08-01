import java.io.FileNotFoundException;
import java.util.Arrays;

public class Boyd {

	public static void main(String[] args) {
		System.out.println("Hello world");

		for (int i = 0; i < Utils.inFiles.length; i++) {
			try {

				Utils.ProblemSpecification problemSpec = Utils.readInput("inputs/shapes_file.json", Utils.inFiles[i]);
				Shape[] shapes = problemSpec.shapes;

//				strat1(shapes);
				Battery[] batteries = new Battery[shapes.length];
				for (int j = 0; j < shapes.length; j++) {
					batteries[j] = new Battery(
							shapes[j].id,
							shapes[j].boundingBox,
							shapes[j].capacity,
							shapes[j].mass,
							shapes[j].shapeData
					);
					batteries[j].rotationId = 0;
				}
				Utils.writeFile(Utils.outFiles[i], batteries);


			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	public static void strat1(Shape[] shapes) {
		BShape[] bshapes = new BShape[shapes.length];
		for (int i = 0; i < shapes.length; i++) {
			bshapes[i] = new BShape(
					shapes[i].id,
					shapes[i].boundingBox,
					shapes[i].capacity,
					shapes[i].mass,
					shapes[i].shapeData
			);
		}
		Arrays.sort(bshapes);
		for (int i = 0; i < bshapes.length; i++) {
			System.out.println("bshapes[i].density = " + bshapes[i].density);
		}

	}


}
