package board;

import java.util.Arrays;

import main.Player;

/**
 * 
 * @author Asad Khan
 * class CornerCoveyor which creates a CornerConveyor object
 */

public class CornerConveyor extends BoardEntity {
	
	/**
	 * Constructor initialises Instance variables which are inherited from the abstract class
	 * BoardEntity
	 * @param x is an x coordinate on the board
	 * @param y is a y coordinate on the board
	 * @param repr is a String which specifies the representation
	 */

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
