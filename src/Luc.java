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
			sumOfShapes += i;
		}
		ArrayList<Battery> availableBatteries = new ArrayList<>(sumOfShapes);
		Shape[] shapes = ps.shapes;

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
		//ensure single block is last
		if (bshapes[0].id == 24) {
			BShape temp = bshapes[0];
			for (int i = 1; i < bshapes.length - 1; i++) {
				bshapes[i - 1] = bshapes[i];
			}
			bshapes[shapes.length - 1] = temp;
		}

		int bCnt = 0;
		for (int i = 0; i < ps.shapeCounts.length; i++) {
			for (int j = 0; j < ps.shapeCounts[i]; j++) {
				availableBatteries.set(0,new Battery(bshapes[i]));
				bCnt++;
			}
		}

		Grid grid = new Grid(ps);
		System.out.println(grid.toCSV());

		ArrayList<Battery> usedBatteries = new ArrayList<>(sumOfShapes);

		for (int i = 0; i < bshapes.length; i++) {
			System.out.println("bshapes[" + i + "].density = id" + bshapes[i].id + " = " + bshapes[i].density);
		}

		SpringerPaper(availableBatteries, grid, bshapes);


		System.out.println("bye");

//		for (int i = 0; i < Utils.inFiles.length; i++) {
//			ArrayList<String> lines = Utils.readInput(Utils.inFiles[i]);
//			// Process the file with an algorithm, machine learning, artificial intelligence and Superior Intellect
//			Utils.writeFile(Utils.outFiles[i], lines);
//		}
	}

	private static Battery[] SpringerPaper(ArrayList<Battery> availableBatteries, Grid grid, BShape[] bshapes) {
		List<Battery> usedBatteries = new ArrayList<>();
		int[] rc = {0, 0};
		do {
			//foreach battery available TODO: optimise this to only check battery types
			for (int i = 0; i < availableBatteries.size(); i++) {
				rc = getBlockPlacement(grid, availableBatteries.get(i));

				if (rc != null) {
					availableBatteries.get(i).offset = rc;
				}
			}
		} while (rc != null);


		return (Battery[]) usedBatteries.toArray();
	}

	private static int[] getBlockPlacement(Grid grid, Battery battery) {
		for (int row = 0; row < grid.rows; row++) {
			for (int column = 0; column < grid.columns; column++) {
				if (battery.canPlace(grid, row, column)) {
					return new int[]{row, column};
				}
			}
		}
		System.out.println("No valid block placements found");
		return null;
	}
}



