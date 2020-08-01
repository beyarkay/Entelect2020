import java.util.Arrays;

public class Grid {
	// top left Rows-Columns
	public int[][] grid;

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
}
