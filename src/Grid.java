import java.util.Arrays;

public class Grid {
	// top left Rows-Columns
	public int[][] grid;
	public int[][] grid_visited;

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

	public int countAdjacentWhitespaces(int row, int col) {
		int returnable = 0;

		for (int[] r : grid_visited) {
			for (int c : r) {
				c = 0;
			}
		}


		return returnable;
	}
}
