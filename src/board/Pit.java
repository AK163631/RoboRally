package board;

import main.Player;

/**
 * This class is Place Holder class for a Pit
 * 
 * @author ihamz
 *
 */

public class Pit extends BoardEntity{
	
	/**
	 * Constructor inherits the Superclass BoardEntity
	 * @param x is the x coordinate of the pit
	 * @param y is the y coordinate of the pit
	 * @param repr is a String that sets the Representation
	 */

	public Pit(int x, int y, String repr) {
		super(x, y, repr);
	}
	
	/**
	 * 
	 */

	@Override
	public void actOnEntry(Player player, BoardEntity prevEntity) {
		player.restPlayer();
	}
}
