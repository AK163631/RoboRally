package tests.board;

import board.Board;
import board.Flag;
import board.InvalidBoardException;
import game.Game;
import game.InvalidPlayerConfigurationException;
import game.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

class BoardTest {

	private Board board;
	private List<Player> players;
	private List<Flag> flags;
	private int xLen;
	private int yLen;

	@BeforeEach
	void setUp() {
		Game g = fromCustom("format 1", ".....+......", ".1.s<<w.E>>S", "...v.2^.^..v", "...e>>n.N<<W", "...-..[..].(",
				".x ....x.....", ".....CABD..)");
		this.board = g.getBoard();
		this.players = g.getPlayers();
		this.flags = this.board.getFlags();
		this.xLen = this.board.getXLen();
		this.yLen = this.board.getYLen();
	}

	private static Game fromCustom(String... board) {

		HashMap<String, ArrayList<String>> hm = new HashMap<>();
		hm.put("Alice", new ArrayList<>(Arrays.asList("FWFLF", "FWLRL")));
		hm.put("Bob", new ArrayList<>(Arrays.asList("FWFLB", "FLFLF")));
		ArrayList<String> al = new ArrayList<>(Arrays.asList(board));
		try {
			return new Game(hm, al);
		} catch (InvalidBoardException | InvalidPlayerConfigurationException e) {
			return null;
		}
	}

	@Test
	void testValidateBoard() {
		Game g = fromCustom("format 1", ".....", ".....", "..AB.");
		System.out.println(g);

	}

}