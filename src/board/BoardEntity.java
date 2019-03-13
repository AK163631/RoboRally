package board;

import main.Player;

public abstract class BoardEntity {

	private int x;
	private int y;
	private String finalRepr;
	private String repr;

	public BoardEntity(int x, int y, String repr) {
		this.x = x;
		this.y = y;
		this.repr = repr;
		this.finalRepr = repr;
	}

	public void act(Player player, BoardEntity prevEntity) {
		// will act on activation
	}

	public void actOnEntry(Player player, BoardEntity prevEntity) {
		// act when robot enters
		// most classes don't need it
	}

	public void RestorRepr() {
		// Restores original representation
		this.repr = this.getFinalRepr();
	}

	public void setRepr(String repr) {
		this.repr = repr;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public String getFinalRepr() {
		return finalRepr;
	}

	@Override
	public String toString() {
		return this.repr;
	}
}
