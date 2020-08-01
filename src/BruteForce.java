import java.io.FileNotFoundException;
import java.util.*;

public class BruteForce {

	static Utils.ProblemSpecification problemSpec;
	static boolean[][] occupied;

	public static void main(String[] args) throws FileNotFoundException {
		if (args.length < 3) {
			System.out.println("please supply input_file, output_file and desired max_seconds_since_last_improvement");
			System.exit(-1);
		}

		long timeCutoffMillis = (long) Integer.parseInt(args[2]) * 1000;

		System.out.println("reading input");
		problemSpec = Utils.readInput("inputs/shapes_file.json", args[0]);

		System.out.println("finished reading input");

		occupied = new boolean[problemSpec.rows][problemSpec.columns];
		for (int r = 0; r < problemSpec.rows; r++)
			for (int c = 0; c < problemSpec.columns; c++)
				occupied[r][c] = false;

		System.out.println();

		//Make array of ShapeCounts
		ShapeWithCount[] shapeCounts = new ShapeWithCount[problemSpec.shapes.length];
		for (int i = 0; i < shapeCounts.length; i++)
			shapeCounts[i] = new ShapeWithCount(problemSpec.shapes[i], problemSpec.shapeCounts[i]);

		//Sort by energy density
//		Arrays.sort(shapeCounts, (o1, o2) -> Float.compare(-(float)o1.capacity/o1.mass, -(float)o2.capacity/o2.mass));
		Arrays.sort(shapeCounts, (o1, o2) -> Float.compare(-o1.mass, -o2.mass));
		for (int i = 0; i < shapeCounts.length; i++)
			shapeCounts[i].index = i;

		Stack<NiceBattery> stack = new Stack<>();

		int startShape = 0;
		int startRotation = 0;
		int startRow = 0;
		int startCol = 0;

		//scoring heuristics
		int totalMass = 0;
		int totalCapacity = 0;

		float bestScore = 0.0f;
		NiceBattery[] bestSolution = new NiceBattery[]{};

		long lastImprovement = System.currentTimeMillis();

		loop0:
		while (true) {
			boolean placed = false;

			loop1:
			for (int i = startShape; i < shapeCounts.length; i++) {

				if (System.currentTimeMillis() - lastImprovement > timeCutoffMillis) {
					System.out.println("time limit exceeded");
					break loop0;
				}

				ShapeWithCount shape = shapeCounts[i];

				if (shape.count == 0)
					continue;

				for (int rotation = startRotation; rotation < 4; rotation++) {
					for (int r = startRow; r < problemSpec.rows - shape.boundingBox + 1; r++) {
						for (int c = startCol; c < problemSpec.columns - shape.boundingBox + 1; c++) {
							//check if shape can fit here
							boolean canFit = true;
							for (int p = 0; p < shape.mass; p++) {
								int rTranslated = shape.shapeData[rotation][p][0] + r;
								int cTranslated = shape.shapeData[rotation][p][1] + c;
								if (occupied[rTranslated][cTranslated]) {
									canFit = false;
									break;
								}
							}

							if (canFit) {
								//Place block here
								//Mark grid cells as occupied
								for (int p = 0; p < shape.mass; p++) {
									int rTranslated = shape.shapeData[rotation][p][0] + r;
									int cTranslated = shape.shapeData[rotation][p][1] + c;
									occupied[rTranslated][cTranslated] = true;
								}
								shape.count--;
								stack.push(new NiceBattery(shape, rotation, r, c));
								placed = true;

								totalMass += shape.mass;
								totalCapacity += shape.capacity;

								float score = (10 * totalMass) * ((float) totalCapacity / totalMass);

//								for (int j = 0; j < stack.size(); j++) {
//									System.out.print(stack.get(j).shape.id + "(" + stack.get(j).row + "," + stack.get(j).col + "," + stack.get(j).rotation + ") ");
//								}
//								System.out.println(score);

								if (score > bestScore) {
									bestScore = score;
									bestSolution = stack.toArray(new NiceBattery[stack.size()]);
//									System.out.println("new high score: " + bestScore);
//									lastImprovement = System.currentTimeMillis();
								}

								break loop1;
							}
						}
					}
				}
			}

			if (placed) {
				startCol = 0;
				startRow = 0;
				startRotation = 0;
				startShape = 0;
			} else {
				if (stack.isEmpty()) {
					System.out.println("brute force complete");
					break;
				}

				//Remove last placed block
				NiceBattery battery = stack.pop();
				ShapeWithCount shape = (ShapeWithCount) battery.shape;
				shape.count++;
				//remove from grid
				for (int p = 0; p < shape.mass; p++) {
					int rTranslated = shape.shapeData[battery.rotation][p][0] + battery.row;
					int cTranslated = shape.shapeData[battery.rotation][p][1] + battery.col;
					occupied[rTranslated][cTranslated] = false;
				}

				//start from one after where we left off
				startCol = battery.col + 1;
				startRow = battery.row;
				startRotation = battery.rotation;
				startShape = shape.index;

				totalMass -= shape.mass;
				totalCapacity -= shape.capacity;
			}
		}

		//Convert to ShitBatteries
		Battery[] shitBatteries = new Battery[bestSolution.length];
		for (int i = 0; i < bestSolution.length; i++) {
			shitBatteries[i] = bestSolution[i].toShitBattery();
		}

		Utils.writeFile(args[1], shitBatteries);
	}

	private static class ShapeWithCount extends Shape {
		public int count;
		public int index;

		public ShapeWithCount(Shape shape, int count) {
			super(shape.id, shape.boundingBox, shape.capacity, shape.mass, shape.shapeData);
			this.count = count;
		}
	}
}
