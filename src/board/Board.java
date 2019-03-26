package board;

import exceptions.InvalidBoardException;
import main.Game;
import main.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class contains a representation of the board and functions to manipulate it
 * <p><strong>
 *     More information to come
 * </strong></p>
 *
 * @author Asad Khan
 */
public class Board {

	/**
	 * Empty nested ArrayList of type BoardEntity, holds all entities on
	 * the board
	 */
	private ArrayList<ArrayList<BoardEntity>> board = new ArrayList<>();

	/**
	 * Empty ArrayList of players found on board
	 * <p>
	 * Note: This is only used to a retrieve player positions from the board
	 * actual players are stores in {@link Game}
	 */
	private ArrayList<Player> players = new ArrayList<>();

	/**
	 * ArrayList of Flags objects on the board
	 */
	private ArrayList<Flag> flags = new ArrayList<>(); // stores list of flags

	/**
	 * Length of board in x direction, technically max index (len - 1)
	 */
	private int xLen;

	/**
	 * Length of  board in Y direction, technically max index (len - 1)
	 */
	private int yLen;

	/**
	 * Constructs and initialises {@code board}, {@code flags} and {@code player} objects from lines
	 *
	 * @param lines A list of strings each on line of the board
	 * @throws InvalidBoardException If board {@code lines} cannot be parsed correctly
	 */
	public Board(ArrayList<String> lines) throws InvalidBoardException {
		// set up board
		this.xLen = lines.get(1).replaceAll(" ", "").length();
		this.yLen = lines.size() - 1;

		// validates board
		// expects xLen to be set
		this.validateBoard(lines.subList(1, lines.size() - 1));

		// build empty board
		for (int y = 0; y < yLen; y++) {
			board.add(new ArrayList<>());
			for (int x = 0; x < xLen; x++) {
				board.get(y).add(null);
			}
		}

		for (int y = 1; y < lines.size(); y++) { // skips first line

			String[] tokens = lines.get(y).replaceAll(" ", "").trim().split("");

			for (int x = 0; x < tokens.length; x++) {

				BoardEntity bE;
				switch (tokens[x]) {
					case "A":
						bE = new NoLocation(x, y - 1, ".");
						this.addEntity(bE); // needed for player
						this.players.add(new Player(x, y - 1, this, "A"));
						continue;
					case "B":
						bE = new NoLocation(x, y - 1, ".");
						this.addEntity(bE);
						this.players.add(new Player(x, y - 1, this, "B"));
						continue;
					case "C":
						bE = new NoLocation(x, y - 1, ".");
						this.addEntity(bE);
						this.players.add(new Player(x, y - 1, this, "C"));
						continue;
					case "D":
						bE = new NoLocation(x, y - 1, ".");
						this.addEntity(bE);
						this.players.add(new Player(x, y - 1, this, "D"));
						continue;
					case "+":
						bE = new Gears(x, y - 1, "+");
						break;
					case "-":
						bE = new Gears(x, y - 1, "-");
						break;
					case "v":
						bE = new Conveyor(x, y - 1, "v", "S", this);
						break;
					case "<":
						bE = new Conveyor(x, y - 1, "<", "W", this);
						break;
					case ">":
						bE = new Conveyor(x, y - 1, ">", "E", this);
						break;
					case "^":
						bE = new Conveyor(x, y - 1, "^", "N", this);
						break;
					case "N":
						bE = new CornerConveyor(x, y - 1, "N");
						break;
					case "E":
						bE = new CornerConveyor(x, y - 1, "E");
						break;
					case "S":
						bE = new CornerConveyor(x, y - 1, "S");
						break;
					case "W":
						bE = new CornerConveyor(x, y - 1, "W");
						break;
					case "n":
						bE = new CornerConveyor(x, y - 1, "n");
						break;
					case "e":
						bE = new CornerConveyor(x, y - 1, "e");
						break;
					case "s":
						bE = new CornerConveyor(x, y - 1, "s");
						break;
					case "w":
						bE = new CornerConveyor(x, y - 1, "w");
						break;
					case "1":
						bE = new Flag(x, y - 1, "1");
						flags.add((Flag) bE);
						break;
					case "2":
						bE = new Flag(x, y - 1, "2");
						flags.add((Flag) bE);
						break;
					case "3":
						bE = new Flag(x, y - 1, "3");
						flags.add((Flag) bE);
						break;
					case "4":
						bE = new Flag(x, y - 1, "4");
						flags.add((Flag) bE);
						break;

					case "x":
						bE = new Pit(x, y - 1, "x");
						break;

					case "[":
						bE = new LaserMarker(x, y - 1, "[");
						break;
					case "]":
						bE = new LaserMarker(x, y - 1, "]");
						break;
					case "(":
						bE = new LaserMarker(x, y - 1, "(");
						break;
					case ")":
						bE = new LaserMarker(x, y - 1, ")");
						break;

					default:
						bE = new NoLocation(x, y - 1, ".");
				}

				this.addEntity(bE);
			}

		}

	}

	/**
	 * Validates the Board ensuring the board x lengths are consistent
	 * ignores y length this will always be one value
	 *
	 * @param lines sets the size of the Board
	 * @throws InvalidBoardException inconsistent x lengths for each line
	 */
	private void validateBoard(List<String> lines) throws InvalidBoardException {
		for (String line : lines) {
			// checks all x lengths are consistent
			if (line.replaceAll(" ", "").length() != this.getXLen()) {
				throw new InvalidBoardException("Board length inconsistent unable to parse");
			}
		}

	}

	/**
	 * Finds first player between {@link LaserMarker} objects both horizontal and vertical
	 * and reduces health by one
	 */
	public void activateLasers() {
		// fires laser in both horizontal and vertical
		for (Player p : Arrays.asList(this.findPlayerInHorizontalLasers(), this.findPlayerInVerticalLasers())) {
			if (p != null) {
				p.decreaseHealth(1); // take one of health
			}
		}
	}

	/**
	 * Attempts to find the first player in the vertical path between {@link LaserMarker}
	 *
	 * @return {@link Player} if player found else {@code null}
	 */
	private Player findPlayerInVerticalLasers() {
		// ( -> ) finds first player in laser path and returns
		// if non found returns null
		boolean pointFound = false;
		int index = 0;
		for (int y = 0; y < this.board.size(); y++) {
			if (pointFound) {
				if (this.getEntity(index, y).getFinalRepr().equals(")")) {
					pointFound = false;
				} else {
					// check player here
					Player p = this.checkPlayerAtLocation(index, y);
					if (p != null) {
						return p;
					}
				}
			} else {
				for (int x = 0; x < this.board.get(y).size(); x++) {
					if (this.getEntity(x, y).getFinalRepr().equals("(")) {
						index = x;
						pointFound = true;
					}
				}
			}
		}
		return null; // no players in laser
	}

	/**
	 * Attempts to find the first player in the Horizontal path between {@link LaserMarker}
	 *
	 * @return {@link Player} if player found else <code>null</code>
	 */
	private Player findPlayerInHorizontalLasers() {
		// [ -> ] finds first player in laser path and returns
		// if non found returns null
		boolean pointFound = false;
		for (int y = 0; y < this.board.size(); y++) {
			for (int x = 0; x < this.board.get(y).size(); x++) {
				BoardEntity bE = this.getEntity(x, y);
				if (!pointFound) {
					if (bE.getFinalRepr().equals("[")) {
						pointFound = true;
					}
				} else {
					if (bE.getFinalRepr().equals("]")) {
						pointFound = false;
					} else {
						// check player here
						Player p = this.checkPlayerAtLocation(x, y);
						if (p != null) {
							return p;
						}
					}
				}
			}
		}
		return null; // no players in laser
	}

	/**
	 * Adds board entity to board, expects x and y of entity to be set
	 * to location it will be placed
	 *
	 * @param bE {@link BoardEntity} to add
	 */
	private void addEntity(BoardEntity bE) {
		this.board.get(bE.getY()).set(bE.getX(), bE);

	}

	/**
	 * Gets {@code BoardEntity} at specific location
	 *
	 * @param x coordinate
	 * @param y coordinate
	 * @return {@code BoardEntity} found
	 */
	public BoardEntity getEntity(int x, int y) {
		return this.board.get(y).get(x);
	}

	/**
	 * Checks and gets player at specified x and y
	 *
	 * @param x coordinate
	 * @param y coordinate
	 * @return {@code Player} if found else {@code null}
	 */
	public Player checkPlayerAtLocation(int x, int y) {
		// if player found at location, return player
		// otherwise return null
		for (Player p : this.players) {
			if (p.getX() == x && p.getY() == y) {
				return p;
			}
		}
		return null;
	}

	/**
	 * Pushes the player in specified direction
	 *
	 * @param player    to push
	 * @param direction to be pushed in
	 */
	private void pushPlayer(Player player, String direction) {
		String originalDirection = player.getDirection(); // resets direction
		player.setDirection(direction);
		player.moveForward(); // in-direct recursive call to placePlayer
		player.setDirection(originalDirection); // resets direction
	}

	/**
	 * Sets the players position on the Board
	 *
	 * <p><strong>
	 * Note: Recursively pushes players in appropriate direction
	 * </strong></p>
	 *
	 * @param x      x coordinate of target location
	 * @param y      y coordinate of target location
	 * @param player player to push
	 * @return {@code BoardEntity} the player is now on top of
	 */
	public BoardEntity placePlayer(int x, int y, Player player) {
		Player p = this.checkPlayerAtLocation(x, y);

		if (p != null) {
			// pushes player back
			this.pushPlayer(p, player.getDirection());
		}

		BoardEntity prevBe = this.getEntity(player.getX(), player.getY());
		prevBe.restoreRepr(); // Restores original representation of previous location

		BoardEntity bE = this.getEntity(x, y);
		bE.setRepr(player.getRepr()); // set new representation

		player.setLocation(x, y); // set new location of player
		player.setPrevEntity(prevBe); // sets previous entity

		bE.actOnEntry(player, prevBe); // call bE action

		return bE; // returns board entity player is on-top of

	}

	/**
	 * @return current {@code board}
	 */
	public ArrayList<ArrayList<BoardEntity>> getBoard() {
		return this.board;

	}

	/**
	 * @return {@code ArrayList} of {@code players}
	 */
	public ArrayList<Player> getPlayers() {
		return this.players;

	}

	/**
	 * @return {@code int} max x index
	 */
	public int getXLen() {
		return xLen;
	}

	/**
	 * @return {@code int} max y index
	 */
	public int getYLen() {
		return yLen;
	}

	/**
	 * @return {@code ArrayList} of {@code Flag} objects on board
	 */
	public ArrayList<Flag> getFlags() {
		return this.flags;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (ArrayList<BoardEntity> al : this.board) {
			sb.append(Arrays.toString(al.toArray())).append("\n");
		}
		return sb.toString();

	}

}
