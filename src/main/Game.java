package main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import java.util.HashMap;

import board.Board;
import board.BoardEntity;

public class Game {

	public Board board;
	private ArrayList<Player> players;

	public Game(String brdPath, String prgPath) {
		// load board from file
		try {
			this.board = new Board(new ArrayList<String>(Files.readAllLines(new File(brdPath).toPath())));
			this.players = this.board.getPlayers();
			this.SetupPlayers(new ArrayList<String>(Files.readAllLines(new File(prgPath).toPath())));
		
		} catch (IOException e) {
			
			System.out.println("File not found");
			System.exit(-1); // quits game
		}

	}

	public Game(HashMap<String, ArrayList<String>> playersHM, ArrayList<String> board) {
		// for GUI support
		// HashMAP -> {"Alice": ("FLFWF","RFWFL")}
	}

	public void SetupPlayers(ArrayList<String> lines) {
		// expects players already be initialised from board

		String[] names = lines.get(1).split(" ");

		// set initial player names
		for (int i = 0; i < names.length; i++) {
			Player p = this.players.get(i);
			p.setName(names[i]); // sets name

			BoardEntity bE = this.board.getBoard().get(p.getY()).get(p.getX());
			bE.setRepr(p.getRepr()); // sets initial repr
		}
		for (Player p : (ArrayList<Player>) this.players.clone()) { // should not change over iterating array
			// removes unused players
			if (p.getName() == null) {
				this.players.remove(p);
			}
		}
		// set initial player instructions
		for (int i = 2; i < lines.size(); i++) {
			String[] tokens = lines.get(i).split(" ");
			for (int x = 0; x < tokens.length; x++) {
				this.players.get(x).addInstruction(tokens[x]);
			}
		}

	}

	public Board step() {
		// executes one instruction for each robot

		// need to decide weather robot controls current instruction
		// or game engine
		return null;
	}

	public ArrayList<Player> getPlayers() {
		return this.players;
	}

	@Override
	public String toString() {
		// game state as string
		StringBuilder sb = new StringBuilder();
		for (Player p : this.players) {
			sb.append(p.toString() + "\n");
		}
		sb.append(this.board.toString());
		return sb.toString();
	}

}
