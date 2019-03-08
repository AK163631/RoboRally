package board;

import main.Player;

public abstract class BoardEntity {

	private int x;
	private int y;
	private String finalRepr;
	private String repr;
	private Board board; // internal reference to board

	public BoardEntity(int x, int y, Board board, String repr) {
		this.x = x;
		this.y = y;
		this.board = board;
		this.repr = repr;
		this.finalRepr = repr;
	}

	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setRepr(String repr) {
		this.repr = repr;
	}

	public void RestorRepr() {
		// Restores original representation
		this.repr = this.getFinalRepr();
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	@Override
	public String toString() {
		return this.repr;
	}

	public void act(Player player, BoardEntity prevEntity) {
		// will act on activation
	}

	public void actOnEntry(Player player, BoardEntity prevEntity) {
		// act when robot enters
		// most classes don't need it

	}

	public String getFinalRepr() {
		return finalRepr;
	}

	public Board getBoard() {
		return board;
	}
}
