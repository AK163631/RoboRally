package game;

import board.Board;
import board.BoardEntity;
import board.Flag;
import board.NoLocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

/**
 * Manages player state and associated player manipulation functions
 *
 * @author Asad Khan
 */
public class Player {

	private ArrayList<ArrayList<String>> instructions = new ArrayList<>();
	private final ArrayList<String> directions = new ArrayList<>(Arrays.asList("N", "E", "S", "W"));
	private ArrayList<Flag> flags = new ArrayList<>();
	private BoardEntity prevEntity;
	private BoardEntity onTopOf;
	private int instructionIndex = 0;
	private int instructionBlockIndex = 0;
	private int directionIndex = 0;
	private int health = 5;
	private int initialX;
	private int initialY;
	private int x;
	private int y;
	private Board board;
	private String name;
	private String repr;

	/**
	 * Constructs player object
	 *
	 * @param x     initial x coordinate
	 * @param y     initial y coordinate
	 * @param board reference to the game board
	 * @param repr  player representation
	 */
	public Player(int x, int y, Board board, String repr) {
		this.x = x;
		this.y = y;
		this.initialX = x;
		this.initialY = y;
		this.repr = repr;
		this.board = board;
		this.onTopOf = this.board.getEntity(x, y);
	}


	public void validatePlayer() throws InvalidPlayerConfigurationException {
		int initialBlockSize = this.instructions.get(0).size();
		for (ArrayList<String> block : this.instructions) {

			// checks if block sizes are inconsistent
			if (block.size() != initialBlockSize) {
				throw new InvalidPlayerConfigurationException("Instruction blocks inconsistent expected size of "
						+ initialBlockSize + " got " + block.size() + " for player " + this.name);
			}

			// checks if instructions are all the same
			// loops through in block in sets of 2
			for (int start = 0, end = 2; end <= block.size(); start++, end++) {

				if (Set.copyOf(block.subList(start, end)).size() == 1) {
					// sets can only have one of each item
					// size == 1 if all items in a list are duplicate
					throw new InvalidPlayerConfigurationException("Repetition of " + block.get(start) + " in block "
							+ String.join("", block) + " for player " + this.name);
				}

			}


			ArrayList<String> al = new ArrayList<>(Arrays.asList("L", "R", "U", "F", "B", "W"));
			for (String i : block) {
				// check if instruction in not in accepted instructions
				if (!al.contains(i)) {
					throw new InvalidPlayerConfigurationException("Invalid instruction " + i + " for player " + this.name);
				}
			}
		}

	}

	/**
	 * Decreases player health by value specified
	 * in factor
	 *
	 * @param factor factor to decrease health by
	 */
	public void decreaseHealth(int factor) {
		this.health -= factor;

		if (this.health <= 0) {
			// player must have died
			this.restPlayer();
		}
	}

	/**
	 * Checks if the player has won
	 *
	 * @return a {@code Boolean} which specifies true if the player has won,
	 * and false otherwise
	 */
	public boolean checkWin() {
		// checks is play flag list is equal to list of all flags on the board
		return this.flags.size() == this.board.getFlags().size();
	}

	/**
	 * Adds flag to player. Checks if flag is in the right order before adding
	 *
	 * @param flag flag to be added
	 */
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

	/**
	 * Decreases health of first player found directly
	 * in front of player
	 */
	public void fireLaser() {
		// function may need optimisation

		// resolves first player in front of player
		// decreases its health by one


		switch (this.getDirection()) {
			// fix this
			case "N":
				for (int y = this.y - 1; y >= 0; y--) {
					Player p = this.board.checkPlayerAtLocation(this.x, y);
					if (p != null) {
						p.decreaseHealth(1);
						break;
					}
				}
				break;
			case "E":
				for (int x = this.x + 1; x < this.board.getXLen(); x++) {
					Player p = this.board.checkPlayerAtLocation(x, this.y);
					if (p != null) {
						p.decreaseHealth(1);
						break;
					}
				}
				break;
			case "S":
				for (int y = this.y + 1; y < this.board.getYLen(); y++) {
					Player p = this.board.checkPlayerAtLocation(this.x, y);
					if (p != null) {
						p.decreaseHealth(1);
						break;
					}
				}
				break;
			case "W":
				for (int x = this.x - 1; x >= 0; x--) {
					Player p = this.board.checkPlayerAtLocation(x, this.y);
					if (p != null) {
						p.decreaseHealth(1);
						break;
					}
				}
				break;
		}

	}

	/**
	 * calls {@link board.BoardEntity#act(Player, BoardEntity)} of {@code BoardEntity} player
	 * is on top of
	 */
	public void activateEntity() {
		this.onTopOf.act(this, this.prevEntity);
		// activates board entity it is on-top of
	}

	/**
	 * Loops though initial position(ip), North of ip, East of ip, South of ip and West of ip
	 * in order looks for first available location and places player at that location
	 * Sets {@code direction} to North
	 * Sets {@code health} to 5
	 */
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

	/**
	 * Turns the player anti clockwise by 90 degrees
	 *
	 * @param multiplier how many times the player will turn
	 */
	public void turnACW90(int multiplier) {
		this.directionIndex -= multiplier;
	}

	/**
	 * Turns the player clockwise by 90 degrees
	 *
	 * @param multiplier how many times the player will turn
	 */
	public void turnCW90(int multiplier) {
		this.directionIndex += multiplier;
	}

	/**
	 * Executes next instruction from instruction list
	 *
	 * @throws NoMoreInstructionsException when no more instructions found
	 * @see #executeInstruction(String)
	 */
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

	/**
	 * Executes the instruction specified
	 *
	 * @param instruction instruction character to execute
	 */
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

	/**
	 * Moves the Player forward in current direction by one place
	 * Rests player if fallen of board
	 *
	 * @see Player#restPlayer()
	 */
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

	/**
	 * Moves the Player backwards by inverting the direction by 180 degrees
	 * then moving it forward then inverting its direction again by 180 degrees
	 *
	 * @see Player#turnCW90(int)
	 * @see Player#moveForward()
	 */
	private void moveBackward() {
		this.turnCW90(2); // flip
		this.moveForward();
		this.turnCW90(2); // return to original direction
	}

	/**
	 * Add an instruction to the instruction block
	 *
	 * @param instruction block of instructions to add
	 */
	public void addInstruction(String instruction) {
		// add instruction block to instructions
		this.instructions.add(new ArrayList<>(Arrays.asList(instruction.split(""))));
	}

	/**
	 * Sets the location of the Player
	 *
	 * @param x x coordinate
	 * @param y y coordinate
	 */
	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * @return x coordinate of current player location
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * @return y coordinate of current player location
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * Sets the player name
	 *
	 * @param name player name
	 */
	public void setName(String name) {

		this.name = name;
	}

	/**
	 * @return gets player Name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the previous boardEntity
	 *
	 * @param bE previous board entity player was on top of {@link BoardEntity}
	 */
	public void setPrevEntity(BoardEntity bE) {
		this.prevEntity = bE;
	}

	/**
	 * @return gets player representation
	 */
	public String getRepr() {
		return this.repr;
	}

	/**
	 * Sets the direction of the Player
	 *
	 * @param direction direction
	 */
	public void setDirection(String direction) {
		this.directionIndex = this.directions.indexOf(direction);
	}

	/**
	 * @return gets current direction of player
	 */
	public String getDirection() {
		// behaves cyclically
		if (this.directions.isEmpty()) {
			throw new IllegalStateException("this.directions is empty, may cause infinite loop");
		}

		while (directionIndex < 0) { // might loop for a while if number very large
			// will never exit if list is empty
			this.directionIndex = this.directions.size() + directionIndex;
		}

		return this.directions.get(this.directionIndex % this.directions.size());
	}


	/**
	 * @return {@code BoardEntity} player is on top of
	 */
	public BoardEntity getOnTopOf() {
		return onTopOf;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.getName() + " " + this.getRepr() + " " + this.getDirection() + " "
				+ Arrays.toString(this.instructions.toArray()) + " " + Arrays.toString(this.flags.toArray()) + " "
				+ " (" + this.getX() + "," + this.getY() + ") " + this.health + " "
				+ this.instructions.get(this.instructionBlockIndex).get(this.instructionIndex);
	}
}
