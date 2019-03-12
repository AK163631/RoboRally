package main;

import board.Board;
import board.BoardEntity;
import board.Flag;
import board.NoLocation;
import exceptions.NoMoreInstructionsException;

import java.util.ArrayList;
import java.util.Arrays;

public class Player {

	private ArrayList<ArrayList<String>> instructions = new ArrayList<>();
	private final ArrayList<String> directions = new ArrayList<>(
			Arrays.asList("N", "E", "S", "W"));
	private ArrayList<Flag> flags = new ArrayList<>();
	private BoardEntity prevEntity;
	private BoardEntity onTopOf;
	private int instructionIndex = 0;
	private int instructionBlockIndex = 0;
	private int directionIndex = 0;
	private int health = 5; // not used yet
	private int initialX;
	private int initialY;
	private int x;
	private int y;
	private Board board;
	private String name;
	private String repr;

	public Player(int x, int y, Board board, String repr) {
		this.x = x;
		this.y = y;
		this.initialX = x;
		this.initialY = y;
		this.repr = repr;
		this.board = board;
		this.onTopOf = this.board.getEntity(x, y);
	}

	public void decreaseHealth(int factor) {
		this.health -= factor;

		if (this.health <= 0) {
			// player must have died
			this.restPlayer();
		}
	}

	public boolean checkWin() {

		// checks is play flag list is equal to list of all flags on the board
		return this.flags.equals(this.board.getFlags());
	}

	public void addFlag(Flag flag) {
		// adds flag to flags if in correct order
		// otherwise ignores

		// is not flags collected
		if (this.flags.size() == 0) {

			// if flag is 1 i.e. the first flag
			if (flag.getValue() == 1) {

				// adds first flag
				this.flags.add(flag);
			}

		}

		// check is flag is one greater then the last flag added
		else if (flag.getValue() == this.flags.get(this.flags.size() - 1).getValue() + 1) {
			this.flags.add(flag);
		}

	}

	public void fireLaser() {
		// function needs optimisation

		// resolves first player in front of player
		// decreases its health by one

		switch (this.getDirection()) {
			case "N":
				for (int i = 1; i >= this.y - i; i++) {
					Player p = this.board.checkPlayerAtLocation(this.x, this.y - i);
					if (p != null) {
						p.decreaseHealth(1);
						break;
					}
				}
				break;
			case "E":
				for (int i = 1; i + this.x < this.board.getXLen(); i++) {
					Player p = this.board.checkPlayerAtLocation(this.x + i, this.y);
					if (p != null) {
						p.decreaseHealth(1);
						break;
					}
				}
				break;
			case "S":
				for (int i = 1; this.y + i < this.board.getYLen(); i++) {
					Player p = this.board.checkPlayerAtLocation(this.x, this.y + i);
					if (p != null) {
						p.decreaseHealth(1);
						break;
					}
				}
				break;
			case "W":
				for (int i = 1; this.x - i >= 0; i++) {
					Player p = this.board.checkPlayerAtLocation(this.x - i, this.y);
					if (p != null) {
						p.decreaseHealth(1);
						break;
					}
				}
				break;


		}

	}

	public void activateEntity() {
		this.onTopOf.act(this, this.prevEntity);
		// activates board entity it is on-top of
	}

	public void restPlayer() {
		// if initial location occupied will check N,E,S,W in order, for available space
		this.health = 5;
		this.setDirection("N");

		if (this.x == this.initialX && this.y == this.initialY) {
			// if already in starting position return
			return;
		}

		int[][] locs = new int[][]{{this.initialX, this.initialY}, // initial
				{this.initialX, this.initialY - 1}, // north
				{this.initialX + 1, this.initialY}, // east
				{this.initialX, this.initialY + 1}, // south
				{this.initialX - 1, this.initialY}}; // west

		for (int[] loc : locs) {
			Player p;
			BoardEntity bE;
			try {
				p = this.board.checkPlayerAtLocation(loc[0], loc[1]);
				bE = this.board.getBoard().get(loc[1]).get(loc[0]);
			} catch (IndexOutOfBoundsException e) {
				// in-case location is out of bounds i.e south on default board
				// skips location
				continue;
			}
			// checks if player not found and object at location is NoLocation then accepts
			// location
			if (p == null && bE instanceof NoLocation) {
				this.board.placePlayer(loc[0], loc[1], this);
				break; // no need to check other locations
			}
		}
	}

	public void turnACW90(int multiplier) {
		this.directionIndex -= multiplier;
	}

	public void turnCW90(int multiplier) {
		this.directionIndex += multiplier;
	}

	public void step() throws NoMoreInstructionsException {

		// executes current instruction
		this.executeInstruction(this.instructions.get(this.instructionBlockIndex).get(this.instructionIndex));

		// works independent of block size and with in-consistent block sizes
		if (this.instructionIndex >= this.instructions.get(this.instructionBlockIndex).size() - 1) {
			if (this.instructionBlockIndex >= this.instructions.size() - 1) {
				// throws error when no more instructions found
				throw new NoMoreInstructionsException();
			}

			this.instructionBlockIndex++; // move to next block
			this.instructionIndex = 0; // move to first instruction on next block

		} else {

			this.instructionIndex++; // move to next instruction
		}
	}

	private void executeInstruction(String instruction) {
		switch (instruction) {
			case "F":
				this.moveForward();
				break;
			case "B":
				this.moveBackward();
				break;
			case "L":
				this.turnACW90(1);
				break;
			case "R":
				this.turnCW90(1);
				break;
			case "U":
				this.turnCW90(2);
				break;
		}
	}

	public void moveForward() {
		try {
			switch (this.getDirection()) {
				case "N":
					this.onTopOf = this.board.placePlayer(this.x, this.y - 1, this);
					break;
				case "E":
					this.onTopOf = this.board.placePlayer(this.x + 1, this.y, this);
					break;
				case "S":
					this.onTopOf = this.board.placePlayer(this.x, this.y + 1, this);
					break;
				case "W":
					this.onTopOf = this.board.placePlayer(this.x - 1, this.y, this);
					break;
			}
		} catch (IndexOutOfBoundsException e) { // needs changing to IndexOutOfBoundsException
			// fallen of the board
			System.err.println("Fallen: " + this.toString());
			this.restPlayer();
		}
	}

	private void moveBackward() {
		this.turnCW90(2); // flip
		this.moveForward();
		this.turnCW90(2); // return to original direction
	}

	public void addInstruction(String instruction) {
		// add instruction block to instructions
		this.instructions.add(new ArrayList<String>(Arrays.asList(instruction.split(""))));
	}


	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public void setName(String name) {
		this.name = name;

	}

	public String getName() {
		return this.name;
	}

	public void setPrevEntity(BoardEntity bE) {
		this.prevEntity = bE;
	}

	public String getRepr() {
		return this.repr;
	}

	public void setDirection(String direction) {
		this.directionIndex = this.directions.indexOf(direction);
	}

	public String getDirection() {
		// behaves cyclically
		if (this.directionIndex < 0)
			return this.directions.get(this.directionIndex + this.directions.size());
		else {
			return this.directions.get(this.directionIndex % this.directions.size());
		}
	}

	public BoardEntity getOnTopOf() {
		return onTopOf;
	}

	@Override
	public String toString() {
		return this.getName() + " " + this.getRepr() + " " + this.getDirection() + " "
				+ Arrays.toString(this.instructions.toArray()) + " " + Arrays.toString(this.flags.toArray()) + " "
				+ " (" + this.getX() + "," + this.getY() + ") " + this.health + " "
				+ this.instructions.get(this.instructionBlockIndex).get(this.instructionIndex);
	}
}
