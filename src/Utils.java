import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Utils {
	public static String[] inFiles = new String[]{
			"inputs/grid_1.input",
			"inputs/grid_2.input",
			"inputs/grid_3.input"
	};
	public static String[] outFiles = new String[]{
			"outputs/map_1.txt",
			"outputs/map_2.txt",
			"outputs/map_3.txt"
	};



	public static void writeFile(String inFile, ArrayList<String> lines) {
		try {
			FileWriter writer = new FileWriter(inFile);
			for (int i = 0; i < lines.size(); i++) {
				writer.write(lines.get(i) + "\n");
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static ArrayList<String> readInput(String fileName) {
		ArrayList<String> lines = new ArrayList<>();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(fileName));

//		String[] firstLineParts = br.readLine().split(",");

			String line;
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
			br.close();
			return lines;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}



	public static void greedy(){

	}
}
