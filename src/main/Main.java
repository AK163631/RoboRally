package main;


public class Main {

	public static void main(String[] args) {
		Game g = new Game("brdFile.txt", "prgFile.txt");
		
		
		System.out.println(g);
		
		Player p1 = g.getPlayers().get(0);
		Player p2 = g.getPlayers().get(1);
		
		
	}

}
