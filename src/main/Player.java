package main;

import java.util.ArrayList;
import java.util.Arrays;

import board.Board;
import board.BoardEntity;
import board.Flag;
import board.NoLocation;

public class Player {

	private ArrayList<ArrayList<String>> instructions = new ArrayList<ArrayList<String>>();
	private final ArrayList<String> directions = new ArrayList<String>(
			Arrays.asList(new String[] { "N", "E", "S", "W" }));
	private ArrayList<Flag> flags = new ArrayList<Flag>();
	private BoardEntity prevEntity;
	public BoardEntity onTopOf;

	private int directionIndex = 0;
	private int health = 5; // not used yet
	private int initalX;
	private int initalY;
	private int x;
	private int y;
	private Board board;
	private String name;
	private String repr;

	public Player(int x, int y, Board board, String repr) {
		this.x = x;
		this.y = y;
		this.initalX = x;
		this.initalY = y;
		this.repr = repr;
		this.board = board;
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

		String dir = this.getDirection();

		if (dir == "N") {
			for (int i = 1; i >= this.y - i; i++) {
				Player p = this.board.checkPlayerAtLocation(this.x, this.y - i);
				if (p != null) {
					p.decreaseHealth(1);
					break;
				}
			}

		} else if (dir == "E") {
			for (int i = 1; i + this.x < this.board.getxLen(); i++) {
				Player p = this.board.checkPlayerAtLocation(this.x + i, this.y);
				if (p != null) {
					p.decreaseHealth(1);
					break;
				}
			}

		} else if (dir == "S") {
			for (int i = 1; this.y + i < this.board.getyLen(); i++) {
				Player p = this.board.checkPlayerAtLocation(this.x, this.y + i);
				if (p != null) {
					p.decreaseHealth(1);
					break;
				}
			}

		} else if (dir == "W") {
			for (int i = 1; this.x - i >= 0; i++) {
				Player p = this.board.checkPlayerAtLocation(this.x - i, this.y);
				if (p != null) {
					p.decreaseHealth(1);
					break;
				}
			}

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

		if (this.x == this.initalX && this.y == this.initalY) {
			// if already in starting position return
			return;
		}

		int[][] locs = new int[][] { { this.initalX, this.initalY }, // initial
				{ this.initalX, this.initalY - 1 }, // north
				{ this.initalX + 1, this.initalY }, // east
				{ this.initalX, this.initalY + 1 }, // south
				{ this.initalX - 1, this.initalY } }; // west

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
			// checks if player not found and object at location is Nolocation then accepts
			// location
			if (p == null && bE instanceof NoLocation) {
				this.board.placePlayer(loc[0], loc[1], this);
				break; // no need to check other locations
			}
		}
	}

	public void turnACW90(int multiplyer) {
		this.directionIndex -= multiplyer;
	}

	public void turnCW90(int multiplyer) {
		this.directionIndex += multiplyer;
	}

	public void executeInstruction(String instruction) {
		if (instruction == "F") {
			this.moveForward();
		} else if (instruction == "B") {
			this.moveBackward();

		} else if (instruction == "L") {
			this.turnACW90(1);

		} else if (instruction == "R") {
			this.turnCW90(1);

		} else if (instruction == "U") {
			this.turnCW90(2);
		}
	}

	public void moveForward() {
		try {
			String dir = this.getDirection();
			if (dir == "N") {
				this.onTopOf = this.board.placePlayer(this.x, this.y - 1, this);
			} else if (dir == "E") {
				this.onTopOf = this.board.placePlayer(this.x + 1, this.y, this);
			} else if (dir == "S") {
				this.onTopOf = this.board.placePlayer(this.x, this.y + 1, this);
			} else if (dir == "W") {
				this.onTopOf = this.board.placePlayer(this.x - 1, this.y, this);
			}
		} catch (IndexOutOfBoundsException e) { // needs changing to IndexOutOfBoundsException
			// fallen of the board
			System.err.println("Fallen: " + this.toString());
			this.restPlayer();
		}
	}

	public void moveBackward() {
		this.turnCW90(2); // flip
		this.moveForward();
		this.turnCW90(2); // return to original direction
	}

	public void addInstruction(String instruction) {
		// add instruction block to instructions
		this.instructions.add(new ArrayList<String>(Arrays.asList(instruction.split(""))));
	}

	public void addInstructions(ArrayList<String> instructions) {
		// add list of instructions to instructions
		for (String st : instructions) {
			this.addInstruction(st);
		}
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
				+ " (" + this.getX() + "," + this.getY() + ") " + this.health;
	}
}
