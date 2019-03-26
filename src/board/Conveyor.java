package board;

import main.Player;

/**
 * Will move player one step in the direction of the conveyor
 * upon activation only if there is not a another player its way
 *
 * @author Asad Khan
 */
public class Conveyor extends BoardEntity {
	private String direction; // direction it is moving
	private Board board; // reference to the whole board

	/**
	 * Conveyor Constructor
	 *
	 * @param x    is the x value of the Board entity
	 * @param y    is the y value of the Board Entity
	 * @param repr representation of board entity
	 */
	public Conveyor(int x, int y, String repr, String direction, Board board) {
		super(x, y, repr);
		this.direction = direction;
		this.board = board;
	}

	/**
	 * Checks if another player is in conveyor path.
	 * Moves player if path is clear otherwise does nothing
	 *
	 * @param player     Player to act on
	 * @param prevEntity board entity the player was previously on top of
	 * @see Conveyor#checkPlayerInDirection()
	 */
	@Override
	public void act(Player player, BoardEntity prevEntity) {
		if (!this.checkPlayerInDirection()) {
			// if not player in next location
			player.setDirection(this.direction);
			player.moveForward();
		}
		// if player found do nothing
	}

	/**
	 * Checks if a player is in direction of conveyor
	 *
	 * @return {@code true} = player found, {@code false} = player not found
	 */
	private Boolean checkPlayerInDirection() {
		// checks if a player in target direction
		Player p = null;
		switch (this.direction) {
			case "N":
				p = this.board.checkPlayerAtLocation(this.getX(), this.getY() - 1);
				break;
			case "E":
				p = this.board.checkPlayerAtLocation(this.getX() + 1, this.getY());
				break;
			case "S":
				p = this.board.checkPlayerAtLocation(this.getX(), this.getY() + 1);
				break;
			case "W":
				p = this.board.checkPlayerAtLocation(this.getX() - 1, this.getY());
				break;
		}
		return p != null;
	}
}
