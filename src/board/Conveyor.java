package board;

import main.Player;

public class Conveyor extends BoardEntity {
	private String direction; // direction it is moving
	private Board board; // reference to the whole board

	public Conveyor(int x, int y, String repr, String direction, Board board) {
		super(x, y, repr);
		this.direction = direction;
		this.board = board;
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
				p = this.board.checkPlayerAtLocation(player.getX(), player.getY() - 1);
				break;
			case "E":
				p = this.board.checkPlayerAtLocation(player.getX() + 1, player.getY());
				break;
			case "S":
				p = this.board.checkPlayerAtLocation(player.getX(), player.getY() + 1);
				break;
			case "W":
				p = this.board.checkPlayerAtLocation(player.getX() - 1, player.getY());
				break;
		}
		return p != null;
	}
}
