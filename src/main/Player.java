package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import board.Board;
import board.BoardEntity;

public class Player {

	private ArrayList<ArrayList<String>> instructions = new ArrayList<ArrayList<String>>();
	private final ArrayList<String> directions = new ArrayList<String>(
			Arrays.asList(new String[] { "N", "E", "S", "W" }));
	private BoardEntity prevEntity;
	private BoardEntity onTopOf;
	private int directionIndex = 0;
	private int health = 5;
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
	
	public void activateEntity() {
		this.onTopOf.act(this, this.prevEntity);
		// activates board entity it is on-top of
	}

	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void restPlayer() {
		// will push robot out of its starting position if taken
		this.health = 5;
		this.setDirection("N");
		this.board.placePlayer(this.initalX, this.initalY, this); // returns to original location
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
			this.turnCW90(1);

		} else if (instruction == "R") {
			this.turnACW90(1);

		} else if (instruction == "U") {
			this.turnCW90(2);
		}

	}

	public void setDirection(String direction) {
		this.directionIndex = this.directions.indexOf(direction);
	}

	public String getDirection() {
		// behaves cyclically
		return this.directions.get(this.directionIndex % this.directions.size());

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
		} catch (ArrayIndexOutOfBoundsException e) { // needs changing to IndexOutOfBoundsException
			// fallen of the board
			this.restPlayer();
		}

	}

	public void moveBackward() {
		try {
			String dir = this.getDirection();
			if (dir == "N") {
				System.out.println(this.x + "," + (this.y + 1));
				this.onTopOf = this.board.placePlayer(this.x, this.y + 1, this);
			} else if (dir == "E") {
				this.onTopOf = this.board.placePlayer(this.x - 1, this.y, this);
			} else if (dir == "S") {
				this.onTopOf = this.board.placePlayer(this.x, this.y - 1, this);
			} else if (dir == "W") {
				this.onTopOf = this.board.placePlayer(this.x + 1, this.y, this);
			}
			
		} catch (ArrayIndexOutOfBoundsException e) { // needs changing to IndexOutOfBoundsException
			// fallen of the board
			this.restPlayer();
		}

	}

	public String getRepr() {
		return this.repr;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
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

	public void setName(String name) {
		this.name = name;

	}

	public String getName() {
		return this.name;
	}
	
	public void setPrevEntity(BoardEntity bE) {
		this.prevEntity = bE;
	}

	@Override
	public String toString() {
		return this.getName() + " " + this.getRepr() + " " + this.getDirection() + " "
				+ Arrays.toString(this.instructions.toArray()) + " (" + this.getX() + "," + this.getY() + ") ";

	}

}
