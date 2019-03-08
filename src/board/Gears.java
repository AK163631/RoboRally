package board;

import main.Player;

public class Gears extends BoardEntity {

	public Gears(int x, int y, Board board, String repr) {
		super(x, y, board, repr);
	}

	@Override
	public void act(Player player, BoardEntity prevEntity) {
		if (this.getFinalRepr() == "+") {
			player.turnCW90(1);
		} else {
			player.turnACW90(1);
		}
	}
}
