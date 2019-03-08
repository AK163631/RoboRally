package board;

import main.Player;

public class Pit extends BoardEntity{

	public Pit(int x, int y, Board board, String repr) {
		super(x, y, board, repr);
	}

	@Override
	public void actOnEntry(Player player, BoardEntity prevEntity) {
		player.restPlayer();
	}
}
