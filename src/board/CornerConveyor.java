package board;

import java.util.Arrays;

import main.Player;

public class CornerConveyor extends BoardEntity {

	public CornerConveyor(int x, int y, Board board, String repr) {
		super(x, y, board, repr);
	}

	@Override
	public void act(Player player, BoardEntity prevEntity) {
		if (!Arrays.asList(new String[] { "^", "v", ">", "<" }).contains(prevEntity.getFinalRepr())) {
			return;
		}
		player.setDirection(this.getFinalRepr().toUpperCase()); // sets direction of player

	}

}
