package board;

import java.util.ArrayList;
import java.util.Arrays;
import main.Player;

public class Board {

	private ArrayList<ArrayList<BoardEntity>> board = new ArrayList<ArrayList<BoardEntity>>();
	private ArrayList<Player> players = new ArrayList<>();
	private int xLen;
	private int yLen;

	public Board(ArrayList<String> lines) {

		this.xLen = lines.get(1).replaceAll(" ", "").length();
		this.yLen = lines.size() - 1;

		// build empty board
		for (int y = 0; y < yLen; y++) {
			board.add(new ArrayList<BoardEntity>());
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
					bE = new NoLocation(x, y - 1, this, ".");
					this.players.add(new Player(x, y - 1, this, "A"));
					break;
				case "B":
					bE = new NoLocation(x, y - 1, this, ".");
					this.players.add(new Player(x, y - 1, this, "B"));
					break;
				case "C":
					bE = new NoLocation(x, y - 1, this, ".");
					this.players.add(new Player(x, y - 1, this, "C"));
					break;
				case "D":
					bE = new NoLocation(x, y - 1, this, ".");
					this.players.add(new Player(x, y - 1, this, "D"));
					break;
				case "+":
					bE = new Gears(x, y - 1, this, "+");
					break;
				case "-":
					bE = new Gears(x, y - 1, this, "-");
					break;
				case "v":
					bE = new Conveyor(x, y - 1, this, "v", "S");
					break;
				case "<":
					bE = new Conveyor(x, y - 1, this, "<", "W");
					break;
				case ">":
					bE = new Conveyor(x, y - 1, this, ">", "E");
					break;
				case "^":
					bE = new Conveyor(x, y - 1, this, "^", "N");
					break;
				case "N":
					bE = new CornerConveyor(x, y - 1, this, "N");
					break;
				case "E":
					bE = new CornerConveyor(x, y - 1, this, "E");
					break;
				case "S":
					bE = new CornerConveyor(x, y - 1, this, "S");
					break;
				case "W":
					bE = new CornerConveyor(x, y - 1, this, "W");
					break;
				case "n":
					bE = new CornerConveyor(x, y - 1, this, "n");
					break;
				case "e":
					bE = new CornerConveyor(x, y - 1, this, "e");
					break;
				case "s":
					bE = new CornerConveyor(x, y - 1, this, "s");
					break;
				case "w":
					bE = new CornerConveyor(x, y - 1, this, "w");
					break;
				case "1":
					bE = new Flag(x, y - 1, this, "1");
					break;
				case "2":
					bE = new Flag(x, y - 1, this, "2");
					break;
				case "3":
					bE = new Flag(x, y - 1, this, "3");
					break;
				case "4":
					bE = new Flag(x, y - 1, this, "4");
					break;

				case "x":
					bE = new Pit(x, y - 1, this, "x");
					break;
				/*
				 * 
				 * case "[": bE = new LaserMarker(x, y - 1, this, "["); break; case "]": bE =
				 * new LaserMarker(x, y - 1, this, "]"); break; case "(": bE = new Laser(x, y -
				 * 1, this, "("); break; case ")": bE = new Laser(x, y - 1, this, ")"); break;
				 */

				default:
					bE = new NoLocation(x, y - 1, this, ".");
				}

				this.addEntity(bE);
			}

		}

	}

	private void addEntity(BoardEntity bE) {
		this.board.get(bE.getY()).set(bE.getX(), bE);
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

	public void pushPlayer(Player player, String direction) {
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

		BoardEntity prevBe = this.board.get(player.getY()).get(player.getX());
		prevBe.RestorRepr(); // Restores original representation of previous location

		BoardEntity bE = this.board.get(y).get(x);
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (ArrayList<BoardEntity> al : this.board) {
			sb.append(Arrays.toString(al.toArray()) + "\n");
		}
		return sb.toString();

	}

}
