import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
	private static String[] inFiles = new String[]{
			"inputs/map_1.input",
			"inputs/map_2.input",
			"inputs/map_3.input",
			"inputs/map_4.input",
			"inputs/map_5.input"
	};
	private static String[] outFiles = new String[]{
			"outputs/map_1.txt",
			"outputs/map_2.txt",
			"outputs/map_3.txt",
			"outputs/map_4.txt",
			"outputs/map_5.txt"
	};



	private static void writeFile(String inFile, ArrayList<String> lines) {
		try {
			FileWriter writer = new FileWriter(inFile);
			for (int i = 0; i < lines.size(); i++) {
				writer.write(lines.get(i));
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private static ArrayList<String> readInput(String fileName) {
		ArrayList<String> lines = new ArrayList<>();
		BufferedReader br = null;
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

	public static void main(String[] args) {
		System.out.println("Hello world");

		for (int i = 0; i < inFiles.length; i++) {
			ArrayList<String> lines = readInput(inFiles[i]);
			// Process the file with an algorithm, machine learning, artificial intelligence and Superior Intellect
			writeFile(outFiles[i], lines);
		}
	}
}
