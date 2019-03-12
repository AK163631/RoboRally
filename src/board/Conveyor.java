package board;

import main.Player;

public class Conveyor extends BoardEntity {
	private String direction; // direction it is moving

	public Conveyor(int x, int y, Board board, String repr, String direction) {
		super(x, y, board, repr); // TODO only class that uses board
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
		switch (this.direction) {
			case "N":
				p = this.getBoard().checkPlayerAtLocation(player.getX(), player.getY() - 1);
				break;
			case "E":
				p = this.getBoard().checkPlayerAtLocation(player.getX() + 1, player.getY());
				break;
			case "S":
				p = this.getBoard().checkPlayerAtLocation(player.getX(), player.getY() + 1);
				break;
			case "W":
				p = this.getBoard().checkPlayerAtLocation(player.getX() - 1, player.getY());
				break;
		}
		return p != null;
	}
}
