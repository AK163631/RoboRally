package main;

import exceptions.NoMoreInstructionsException;

public class Main {

	public static void main(String[] args) throws NoMoreInstructionsException {
		Game g = new Game("brdFile.txt", "prgFile.txt");

		System.out.println(g); // before shot

		Player p1 = g.getPlayers().get(0);
		Player p2 = g.getPlayers().get(1);
		Player p3 = g.getPlayers().get(2);
	

		System.out.println(g);

	}

}
