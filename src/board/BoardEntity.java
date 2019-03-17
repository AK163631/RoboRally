package board;

import main.Player;

/**
 * this is an abstract class to create a BoardEntity 
 * 
 * @author Asad Khan
 *
 */

public abstract class BoardEntity {

	private int x;
	private int y;
	private String finalRepr;
	private String repr;
	
	/**
	 * Constructor that initialises the instance variables
	 *
	 * @param x is the x value of the Board entity
	 * @param y is the y value of the Board Entity
	 * @param repr representation of board entity
	 * @see #setRepr(String)
	 * @see #getX()
	 */

	public BoardEntity(int x, int y, String repr) {
		this.x = x;
		this.y = y;
		this.repr = repr;
		this.finalRepr = repr;
	}
	
	/**
	 * will act on activation
	 * @param player is an object of class Player
	 * @param prevEntity is an object of class BoardEntity
	 */

	public void act(Player player, BoardEntity prevEntity) {
		// will act on activation
	}
	
	/**
	 *will act on Entry of a robot
	 * @param player is an Object of class Player
	 * @param prevEntity is an Object of Class BoardEntity
	 */

	public void actOnEntry(Player player, BoardEntity prevEntity) {
		// act when robot enters
		// most classes don't need it
	}
	
	/**
	 * restores the original representation
	 */

	public void RestorRepr() {
		// Restores original representation
		this.repr = this.getFinalRepr();
	}
	
	/**
	 * sets a new representation
	 * @param repr is a String which defines the representation
	 */

	public void setRepr(String repr) {
		this.repr = repr;
	}
	
	/**
	 * gets the X value
	 * @return a <code> integer </code> specifying
	 * the x value
	 */

	public int getX() {
		return this.x;
	}
	
	/**
	 * gets the y value
	 * @return a <code> integer </code> specifying
	 * the y value
	 */

	public int getY() {
		return this.y;
	}

	/**
	 * gets the final representation
	 * @return a <code> String </code> specifying 
	 * the final representation
	 */
	
	public String getFinalRepr() {
		return finalRepr;
	}
	

	@Override
	public String toString() {
		return this.repr;
	}
}
