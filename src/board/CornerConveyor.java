package board;

import java.util.Arrays;

import main.Player;

public class CornerConveyor extends BoardEntity {

	public CornerConveyor(int x, int y, String repr) {
		super(x, y, repr);
	}

	@Override
	public void act(Player player, BoardEntity prevEntity) {
		if (!Arrays.asList("^", "v", ">", "<").contains(prevEntity.getFinalRepr())) {
			return;
		}
		player.setDirection(this.getFinalRepr().toUpperCase()); // sets direction of player
	}
	
}
