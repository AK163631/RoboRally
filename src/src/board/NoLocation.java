package board;

import main.Player;

public class NoLocation extends BoardEntity {

	public NoLocation(int x, int y, Board board, String repr) {
		super(x, y, board, repr);
	}

	@Override
	public void act(Player player, BoardEntity prevEntity) {
		// does nothing
	}

}
