import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Luc {

	public static void main(String[] args) {
		System.out.println("Hello world");


		Utils.ProblemSpecification ps = Utils.readInput(2);
		assert ps != null;
		int sumOfShapes = 0;
		for (int i : ps.shapeCounts) {
			sumOfShapes += i;
		}
		ArrayList<Battery> availableBatteries = new ArrayList<>(sumOfShapes);
		Shape[] shapes = ps.shapes;

		BShape[] bshapes = new BShape[sumOfShapes];
		int counter = 0;
		for (int i = 0; i < shapes.length; i++) {
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
		//ensure single block is last
		if (bshapes[0].id == 24) {
			BShape temp = bshapes[0];
			for (int i = 1; i < bshapes.length - 1; i++) {
				bshapes[i - 1] = bshapes[i];
			}
			bshapes[bshapes.length - 1] = temp;
		}

		int bCnt = 0;
		for (int i = 0; i < bshapes.length; i++) {
//			for (int j = 0; j < ps.shapeCounts[i]; j++) {
				availableBatteries.add(new Battery(bshapes[i]));
				bCnt++;
//			}
		}

		Grid grid = new Grid(ps);
		System.out.println(grid.toCSV());

		ArrayList<Battery> usedBatteries = new ArrayList<>(sumOfShapes);

		for (int i = 0; i < bshapes.length; i++) {
			System.out.println("bshapes[" + i + "].density = id" + bshapes[i].id + " = " + bshapes[i].density);
		}

		Battery[] b = SpringerPaper(availableBatteries, grid, bshapes);
		System.out.println(grid);
		System.out.println(grid.toCSV());
		Utils.writeFile("outputs/output1.txt",b);
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
			//foreach battery available TODO: optimise this to only check battery types/rotations
			for (int i = 0; i < availableBatteries.size(); i++) {
				rc = getBlockPlacement(grid, availableBatteries.get(i));

				if (rc != null) {
					availableBatteries.get(i).offset = rc;
					availableBatteries.get(i).placeInGrid(grid);
					usedBatteries.add(availableBatteries.get(i));
					System.out.println(grid);
					availableBatteries.remove(i);
					break;
				}
			}
		} while (rc != null);


		return usedBatteries.toArray(new Battery[0]);
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



