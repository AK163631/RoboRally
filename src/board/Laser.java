package board;

import main.Player;

public class Laser extends BoardEntity {
	
	private BoardEntity bEAtLocation;

	public Laser(int x, int y, Board board, String repr) {
		super(x, y, board, repr);
	}

	@Override
	public void act(Player player, BoardEntity prevEntity) {
		this.bEAtLocation.act(player, prevEntity); // calls act of object it on top of
		if(player.getX() == this.getX() && player.getY() == this.getY()) {
			// checks if still in-front of laser
			player.decreaseHealth(1); // decrease health by one 
		}
	}
	
	public void WrapBe(BoardEntity bE) {
		// wraps original board entity
		this.setFinalRepr(bE.getFinalRepr());
		this.setRepr(bE.getFinalRepr());
		this.bEAtLocation = bE;
	}

}
