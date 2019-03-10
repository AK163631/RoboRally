package main;

public class Main {

	public static void main(String[] args) {
		Game g = new Game("brdFile.txt", "prgFile.txt");

		System.out.println(g); // before shot

		Player p1 = g.getPlayers().get(0);
		Player p2 = g.getPlayers().get(1);
		Player p3 = g.getPlayers().get(2);
		p1.moveForward();
		p1.moveForward();
		p1.moveForward();
		p1.moveForward();
		p1.moveForward();
		p1.executeInstruction("L");
		p1.moveForward();
		p1.moveForward();
		p1.moveForward();
		p1.moveForward();
		p1.executeInstruction("U");
		p1.moveForward();
		p1.moveForward();
		p1.moveForward();
		p1.moveForward();
		p1.executeInstruction("R");
		p1.moveForward();
		p1.moveForward();

		System.out.println(g);

	}

}
