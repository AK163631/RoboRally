package board;

import main.Player;

public class Flag extends BoardEntity {
	private int number;

	public Flag(int x, int y, Board board, String repr) {
		super(x, y, board, repr);
		this.number = Integer.parseInt(repr);
	}

	public int getValue() {
		return this.number;
	}
	

	@Override
	public void act(Player player, BoardEntity prevEntity) {
		player.addFlag(this);
	}

}
