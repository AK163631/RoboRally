package board;

import main.Player;

/**
 * this class is used to create flag objects on the board
 * @author Asad Khan
 *
 */

public class Flag extends BoardEntity {
	private int number;
	
	/**
	 * Constructor initialises instance variables 
	 * BoardEntity methods used through the super() method
	 * @param x sets the x coordinate of the Flag
	 * @param y sets the y coordinate of the Flag
	 * @param repr is a string defining the representation
	 */
	public Flag(int x, int y, String repr) {
		super(x, y, repr);
		this.number = Integer.parseInt(repr);
	}

	/**
	 * gets the value of number
	 * @return a <code> integer </code> specifying the number
	 */
	public int getValue() {
		return this.number;
	}
	


	@Override
	public void act(Player player, BoardEntity prevEntity) {
		player.addFlag(this);
	}

}
