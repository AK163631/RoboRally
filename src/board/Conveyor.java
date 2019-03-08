package board;

import main.Player;

public class Conveyor extends BoardEntity {

	public Conveyor(int x, int y, Board board, String repr) {
		super(x, y, board, repr);
	}

	@Override
	public void act(Player player, BoardEntity prevEntity) {
		// moves player in direction of conveyor
		if (this.getFinalRepr() == ">") {
			player.setDirection("E");
		} else if (this.getFinalRepr() == "<") {
			player.setDirection("W");
		} else if (this.getFinalRepr() == "^") {
			player.setDirection("N");
		} else if (this.getFinalRepr() == "V") {
			player.setDirection("S");

		}
		player.moveForward();
	}
}
