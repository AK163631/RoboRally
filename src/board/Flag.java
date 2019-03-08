package board;

import main.Player;

public class Flag extends BoardEntity {

	public Flag(int x, int y, Board board, String repr) {
		super(x, y, board, repr);
	}

	@Override
	public void act(Player player, BoardEntity prevEntity) {
	}

}
