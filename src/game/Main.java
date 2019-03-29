package game;

import board.InvalidBoardException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Entry
 *
 * @author Asad Khan
 */
public class Main {
	// TODO JavaDocs
	// TODO figure out why test files are not acting as expected

	private static Game fromFile() throws InvalidBoardException, InvalidPlayerConfigurationException, IOException {
		return new Game("Docs\\brdFile.txt", "Docs\\prgFile.txt");
	}

	private static Game fromCustom() throws InvalidBoardException, InvalidPlayerConfigurationException {
		HashMap<String, ArrayList<String>> hm = new HashMap<>();
		hm.put("Alice", new ArrayList<>(Arrays.asList("FWFLF", "FWLRL")));
		hm.put("Bob", new ArrayList<>(Arrays.asList("FWFLB", "FLFLF")));
		ArrayList<String> al = new ArrayList<>(Arrays.asList(
				"format 1", // format string required otherwise offset incorrect
				".....+......",
				".1.s<<w.E>>S",
				"...v.2^.^..v",
				"...e>>n.N<<W",
				"...-..[..].(",
				".x ....x.....",
				".....CABD..)"
		));
		return new Game(hm, al);
	}

	// example usage of game class
	public static void main(String[] args) {
		Game g = null;
		try {

			g = fromCustom();
			System.exit(0);

		} catch (Exception e) {

			System.out.println(e.toString());
			System.exit(-1);
		}

		System.out.println(g); // before shot

		while (g.hasNext()) {
			g.step();
			System.out.println(g);
		}
		System.out.println(g); //  after shot

		if (g.getWinner() != null) {
			System.out.println("Winner: " + g.getWinner().getName());
		} else {
			System.out.println("Draw");
		}

	}


}
