package main;

import exceptions.InvalidBoardException;
import exceptions.InvalidPlayerConfigurationException;

/** 
 * Class Main which starts the game
 * @author Asad Khan
 *
 */
public class Main {
	// TODO tighten access modifiers - mostly done
	// TODO error handling for invalid prg - mostly done
	// TODO figure out why test files are not acting as expected

	// example usage of game class

	public static void main(String[] args) {
		Game g = null;
		try {

			g = new Game("brdFile.txt", "prgFile.txt");

		} catch (InvalidBoardException | InvalidPlayerConfigurationException e) {

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
