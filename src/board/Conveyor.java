package board;

import main.Player;

public class Conveyor extends BoardEntity {
	private String direction; // direction it is moving

	public Conveyor(int x, int y, Board board, String repr, String direction) {
		super(x, y, board, repr);
		this.direction = direction;
	}

	@Override
	public void act(Player player, BoardEntity prevEntity) {
		if (!this.checkPlayerInDirection(player)) {
			// if not player in next location
			player.setDirection(this.direction);
			player.moveForward();
		}
		// if player found do nothing
	}

	private Boolean checkPlayerInDirection(Player player) {
		// checks if a player in target direction
		Player p = null;
		if (this.direction == "N") {
			p = this.getBoard().checkPlayerAtLocation(player.getX(), player.getY() - 1);
		} else if (this.direction == "E") {
			p = this.getBoard().checkPlayerAtLocation(player.getX() + 1, player.getY());
		} else if (this.direction == "S") {
			p = this.getBoard().checkPlayerAtLocation(player.getX(), player.getY() + 1);
		} else if (this.direction == "W") {
			p = this.getBoard().checkPlayerAtLocation(player.getX() - 1, player.getY());
		}
		if (p == null) {
			return false;
		}
		return true;
	}
}
