package board;

import main.Player;

/**
 * This class initialises the gear objects on the Board
 * @author Asad Khan
 *
 */

public class Gears extends BoardEntity {
	
	/**
	 * the constructor initialises all instace variables
	 * note the super() method which uses BoardEntity's constructor due to it being the parent class
	 * @param x is the x coordinate of the gear object
	 * @param y is the y coordinate of the gear object
	 * @param repr is a strinng which defines the representation
	 */

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
