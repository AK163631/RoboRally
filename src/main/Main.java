package main;

public class Main {
	// TODO fix laser logic to damage first player it encounters
	// TODO tighten access modifiers
	// TODO clean up un-necessary functions
	// TODO clean up unnecessary fields etc
	// TODO leave function optimisation for other group members/ otherwise do my self
	// TODO get team members to complete javadoc using in-line comments as a guide for how functions work
	// TODO change == to .equals where appropriate - Jimmy's task

	public static void main(String[] args) {
		Game g = new Game("brdFile.txt", "prgFile.txt");

		System.out.println(g); // before shot
		while (g.hasNext()) {
			g.step();
			System.out.println(g);
		}

		System.out.println(g); // after shot

	}

}
