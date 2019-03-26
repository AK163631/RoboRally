package board;

import game.Player;

import java.util.Arrays;

/**
 * Will turn player to the same direction as the conveyor
 * if the player has just come from an a adjacent conveyor
 *
 * @author Asad Khan
 */
public class CornerConveyor extends BoardEntity {

	/**
	 * Corner conveyor constructor
	 *
	 * @param x    is the x value of the Board entity
	 * @param y    is the y value of the Board Entity
	 * @param repr representation of board entity
	 */
	public CornerConveyor(int x, int y, String repr) {
		super(x, y, repr);
	}

	/**
	 * Checks if player has just come from an adjacent converter
	 * if so turns player otherwise has no effect
	 *
	 * @param player     Player to act on
	 * @param prevEntity board entity the player was previously on top of
	 */
	@Override
	public void act(Player player, BoardEntity prevEntity) {
		// TODO un-sure if this also moves a player forward

		if (!Arrays.asList("^", "v", ">", "<").contains(prevEntity.getFinalRepr())) {
			return;
		}
		player.setDirection(this.getFinalRepr().toUpperCase()); // sets direction of player
	}

}
