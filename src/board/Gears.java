package board;

import main.Player;

public class Gears extends BoardEntity {

	public Gears(int x, int y, String repr) {
		super(x, y, repr);
	}

	@Override
	public void act(Player player, BoardEntity prevEntity) {
		if (this.getFinalRepr().equals("+")) {
			player.turnCW90(1);
		} else {
			player.turnACW90(1);
		}
	}
}
