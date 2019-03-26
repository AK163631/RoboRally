package game;

import board.InvalidBoardException;

import java.io.IOException;

/**
 * Class Main which starts the game
 *
 * @author Asad Khan
 */
public class Main {
	// TODO JavaDocs
	// TODO figure out why test files are not acting as expected


	// example usage of game class
	public static void main(String[] args) {
		Game g = null;
		try {

			g = new Game("Docs\\brdFile.txt", "Docs\\prgFile.txt");

		} catch (InvalidBoardException | InvalidPlayerConfigurationException | IOException e) {

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
