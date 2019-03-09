package board;

import main.Player;

public class Flag extends BoardEntity {
	private int number;

	public Flag(int x, int y, Board board, String repr) {
		super(x, y, board, repr);
		this.number = Integer.parseInt(repr);
	}
	
	

	@Override
	public void actOnEntry(Player player, BoardEntity prevEntity) {
		player.addFlag(this);
	}
}
