package board;

import exceptions.InvalidBoardException;
import main.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {

	private ArrayList<ArrayList<BoardEntity>> board = new ArrayList<>();
	private ArrayList<Player> players = new ArrayList<>();
	private ArrayList<Flag> flags = new ArrayList<>(); // stores list of flags
	private int xLen;
	private int yLen;

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
						bE = new Conveyor(x, y - 1, "S", "v", this);
						break;
					case "<":
						bE = new Conveyor(x, y - 1, "W", "<", this);
						break;
					case ">":
						bE = new Conveyor(x, y - 1, "E", ">", this);
						break;
					case "^":
						bE = new Conveyor(x, y - 1, "N", "^", this);
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

	private void validateBoard(List<String> lines) throws InvalidBoardException {
		for (String line : lines) {
			// checks all x lengths are consistent
			if (line.replaceAll(" ", "").length() != this.getXLen()) {
				throw new InvalidBoardException();
			}
		}

	}

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

	private void addEntity(BoardEntity bE) {
		this.board.get(bE.getY()).set(bE.getX(), bE);

	}

	public BoardEntity getEntity(int x, int y) {
		return this.board.get(y).get(x);
	}

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

	private void pushPlayer(Player player, String direction) {
		String originalDirection = player.getDirection(); // resets direction
		player.setDirection(direction);
		player.moveForward(); // in-direct recursive call to placePlayer
		player.setDirection(originalDirection); // resets direction
	}

	public BoardEntity placePlayer(int x, int y, Player player) {
		Player p = this.checkPlayerAtLocation(x, y);

		if (p != null) {
			// pushes player back
			this.pushPlayer(p, player.getDirection());
		}

		BoardEntity prevBe = this.getEntity(player.getX(), player.getY());
		prevBe.RestorRepr(); // Restores original representation of previous location

		BoardEntity bE = this.getEntity(x, y);
		bE.setRepr(player.getRepr()); // set new representation

		player.setLocation(x, y); // set new location of player
		player.setPrevEntity(prevBe); // sets previous entity

		bE.actOnEntry(player, prevBe); // call bE action

		return bE; // returns board entity player is on-top of

	}

	public ArrayList<ArrayList<BoardEntity>> getBoard() {
		return this.board;

	}

	public ArrayList<Player> getPlayers() {
		return this.players;

	}

	public void activateLasers() {
		// fires laser in both horizontal and vertical
		for (Player p : Arrays.asList(this.findPlayerInHorizontalLasers(), this.findPlayerInVerticalLasers())) {
			if (p != null) {
				p.decreaseHealth(1); // take one of health
			}
		}
	}

	public int getXLen() {
		return xLen;
	}

	public int getYLen() {
		return yLen;
	}

	public ArrayList<Flag> getFlags() {
		return this.flags;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (ArrayList<BoardEntity> al : this.board) {
			sb.append(Arrays.toString(al.toArray())).append("\n");
		}
		return sb.toString();

	}

}
