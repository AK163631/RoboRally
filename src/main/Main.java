package main;

import exceptions.InvalidBoardException;

public class Main {
	// TODO tighten access modifiers - mostly done
	// TODO leave function optimisation for other group members/ otherwise do my self
	// TODO get team members to complete javadoc using in-line comments as a guide for how functions work
	// TODO error handling for invalid brd or prg files - inProgress

	// example usage of game class
	public static void main(String[] args) {
		Game g = null;
		try {

			g = new Game("brdFile.txt", "prgFile.txt");

		} catch (InvalidBoardException e) {

			System.out.println(e.toString());
			System.exit(-1);
		}

		System.out.println(g); // before shot

		while (g.hasNext()) {
			g.step();
			System.out.println(g);
		}
		System.out.println(g); //  after shot

	}

}
