package board;

import main.Player;

public class Laser extends BoardEntity {
	// TODO change so it only damages first player in its path
	
	private BoardEntity bEAtLocation;

	public Laser(int x, int y, Board board, String repr) {
		super(x, y, board, repr);
	}

	@Override
	public void act(Player player, BoardEntity prevEntity) {
		this.bEAtLocation.act(player, prevEntity); // calls act of object it on top of
		if(player.getOnTopOf() instanceof Laser) {
			// checks if player is still in laser
			player.decreaseHealth(1); // decrease health by one 
		}
	}

	@Override
	public void actOnEntry(Player player, BoardEntity prevEntity) {
		this.bEAtLocation.actOnEntry(player, prevEntity); // calls objects on entry function
		
	}
	
	public void WrapBe(BoardEntity bE) {
		// wraps original board entity
		this.setFinalRepr(bE.getFinalRepr());
		this.setRepr(bE.getFinalRepr());
		this.bEAtLocation = bE;
	}

}
