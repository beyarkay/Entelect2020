import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Grid {
	// top left Rows-Columns
	public int[][] grid;
	public int[][] grid_visited;

	int rows, columns;


	public Grid(Utils.ProblemSpecification ps) {
		this.grid = new int[ps.rows][ps.columns];
		for (int i = 0; i < ps.blockedCells.length; i++) {
			grid[ps.blockedCells[i][0]][ps.blockedCells[i][1]] = -1;
		}
		rows = ps.rows;
		columns = ps.columns;
	}

	public boolean checkAdjacentEmpty(int row, int col) {
		boolean up, right, down, left;
		up = grid[row - 1][col] == 0;
		right = grid[row][col + 1] == 0;
		down = grid[row - 1][col] == 0;
		left = grid[row][col - 1] == 0;
		return up | right | down | left;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int[] row : grid) {
			sb.append(Arrays.toString(row));
			sb.append("\n");
		}
		return sb.toString();
	}

	public String toCSV() {
		StringBuilder sb = new StringBuilder();
		for (int row = 0; row < grid.length; row++) {
			for (int col = 0; col < grid[row].length; col++) {
				sb.append(grid[row][col]);
				sb.append(',');
			}
			sb.append("\n");

		}
		return sb.toString();
	}



	public int countAdjacentWhitespaces(int startRow, int startCol) {
		int returnable = 0;
		// reset grid visited
		for (int[] r : grid_visited) {
			for (int c : r) {
				c = 0;
			}
		}

		int h = grid.length;
		if (h == 0)
			return 0;
		int l = grid[0].length;

		boolean[][] visited = new boolean[h][l];

		Queue<String> queue = new LinkedList<>();

		queue.add(startRow + "," + startCol);

		System.out.println("Breadth-First Traversal: ");
		while (!queue.isEmpty()) {

			String x = queue.remove();
			int row = Integer.parseInt(x.split(",")[0]);
			int col = Integer.parseInt(x.split(",")[1]);

			if (row < 0 || col < 0 || row >= h || col >= l || visited[row][col])
				continue;

			visited[row][col] = true;
			System.out.println(x);
			returnable++;
			if (col > 0 && grid[row][col - 1] == 0)
				queue.add(row + "," + (col - 1)); //go left
			if (col < l - 1 && grid[row][col + 1] == 0)
				queue.add(row + "," + (col + 1)); //go right
			if (row > 0 && grid[row - 1][col] == 0)
				queue.add((row - 1) + "," + col); //go up
			if (row < h - 1 && grid[row + 1][col] == 0)
				queue.add((row + 1) + "," + col); //go down
		}

		return returnable;
	}
}
