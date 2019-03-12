package main;

import board.Board;
import board.BoardEntity;
import exceptions.NoMoreInstructionsException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;

public class Game {

	private Board board;
	private ArrayList<Player> players;
	private Player winner;
	private boolean draw = false;
	private int startIndex = 0;
	private int indexOfCurrentPlayer = 0;
	private int iterations = 0;

	public Game(String brdPath, String prgPath) {
		// load board from file
		try {
			this.board = new Board(new ArrayList<>(Files.readAllLines(new File(brdPath).toPath())));
			this.players = this.board.getPlayers();
			this.SetupPlayers(new ArrayList<>(Files.readAllLines(new File(prgPath).toPath())));

		} catch (IOException e) {

			System.out.println("File not found");
			System.exit(-1); // quits game
		}

	}

	public Game(HashMap<String, ArrayList<String>> playersHM, ArrayList<String> board) {
		// TODO this function
		// for GUI support
		// HashMAP -> {"Alice": ("FLFWF","RFWFL")}
	}

	private void SetupPlayers(ArrayList<String> lines) {
		// expects players already be initialised from board

		String[] names = lines.get(1).split(" ");

		// set initial player names
		for (int i = 0; i < names.length; i++) {
			Player p = this.players.get(i);
			p.setName(names[i]); // sets name

			BoardEntity bE = this.board.getEntity(p.getX(), p.getY());
			bE.setRepr(p.getRepr()); // sets initial repr
		}

		// removes unused players
		this.players.removeIf(p -> p.getName() == null);

		// set initial player instructions
		for (int i = 2; i < lines.size(); i++) {
			String[] tokens = lines.get(i).split(" ");
			for (int x = 0; x < tokens.length; x++) {
				this.players.get(x).addInstruction(tokens[x]);
			}
		}

	}

	public Boolean hasNext() {
		// true = game still has steps remaining there is no winner yet or no more
		// instructions
		// false = game has ended and there is a winner and there are still instructions
		// to execute
		return !this.draw;
		// return this.winner == null | !this.draw;
	}


	public void step() {

		if (!this.hasNext()) {
			return;
		}

		// if at end of player list
		if (this.iterations == this.players.size()) {

			// activate player and board actions
			this.activateAllPlayers();

			for (Player p : this.players) {
				if (p.checkWin()) {
					this.winner = p;
					return;
				}
			}

			// rest iterations
			this.iterations = 0;

			// add 1 to start index
			this.startIndex++;

			// set current index to start point 
			this.indexOfCurrentPlayer = this.startIndex;
		}

		Player p = this.players.get(this.indexOfCurrentPlayer % this.players.size());
		try {
			p.step();
		} catch (NoMoreInstructionsException e) {
			this.draw = true;
			return;
		}


		this.indexOfCurrentPlayer++;
		this.iterations++;

		/*
		 *
		 * int blocks = 5; for (int i = 0; i < blocks; i++) { // loops through all
		 * players for (int x = i, orig = i; x < orig + this.players.size(); x++) {
		 * Player p = this.getPlayer(x); System.out.println(p);
		 * System.out.println(this.players.indexOf(p)); } }
		 */
	}

	private void activateAllPlayers() {

		// activates board entities under player
		for (Player p : this.players) {
			p.activateEntity();

		}

		// fires laser
		for (Player p : this.players) {
			p.fireLaser();

		}
	}

	public Player getWinner() {
		return this.winner;
	}

	public ArrayList<ArrayList<BoardEntity>> getBoard() {
		return this.board.getBoard();
	}

	public ArrayList<Player> getPlayers() {
		return this.players;
	}

	@Override
	public String toString() {
		// game state as string
		StringBuilder sb = new StringBuilder();
		for (Player p : this.players) {
			sb.append(p);
			sb.append("\n");
		}
		sb.append(this.board.toString());
		return sb.toString();
	}

}
