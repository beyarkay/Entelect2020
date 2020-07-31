import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		System.out.println("Hello world");
	}

	String[] inFiles = new String[]{
			"inputs/map_1.input",
			"inputs/map_2.input",
			"inputs/map_3.input",
			"inputs/map_4.input",
			"inputs/map_5.input"
	};
	String[] outFiles = new String[]{
			"outputs/map_1.txt",
			"outputs/map_2.txt",
			"outputs/map_3.txt",
			"outputs/map_4.txt",
			"outputs/map_5.txt"
	};

	public static ArrayList<String> readInput(String fileName) throws IOException {
		ArrayList<String> lines = new ArrayList<>();
		BufferedReader br = new BufferedReader(new FileReader(fileName));
//		String[] firstLineParts = br.readLine().split(",");

		String line;
		while ((line = br.readLine()) != null) {
			lines.add(line);
		}
		br.close();
		return lines;
	}
}
