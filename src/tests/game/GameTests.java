package tests.game;

import board.Board;
import board.InvalidBoardException;
import game.Game;
import game.InvalidPlayerConfigurationException;
import game.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTests {
	private Game g;
	private ArrayList<Player> players;

	public GameTests() throws InvalidBoardException, InvalidPlayerConfigurationException, IOException {
		atStart();
	}

	@BeforeEach
	public void atStart() throws InvalidBoardException, InvalidPlayerConfigurationException, IOException {

		g = new Game("docs\\brdFile.txt", "docs\\prgFile.txt");
		players = g.getPlayers();
	}

	@Test
	void testHasNext() throws InvalidBoardException, InvalidPlayerConfigurationException {
		HashMap<String, ArrayList<String>> hm = new HashMap<>();
		hm.put("Bob", new ArrayList<>(Arrays.asList("FRFUB", "RFLFL")));
		hm.put("Alice", new ArrayList<>(Arrays.asList("FBFBF", "BFBFB")));
		ArrayList<String> al = new ArrayList<>(Arrays.asList(
				"format 1", // format string required otherwise offset incorrect
				"........",
				"...+21.-",
				"........",
				"...A...B",
				"........"
		));
		g = new Game(hm, al);

		g.step();
		g.step();
		//1st instructions by both players
		g.step();
		g.step();
		//2nd instructions by both players
		g.step();
		g.step();
		//3rd instructions by both players
		g.step();
		g.step();
		//4th instructions by both players
		g.step();
		g.step();
		//5th instructions by both players
		g.step();
		g.step();
		//6th instructions by both players
		g.step();
		g.step();
		//7th instructions by both players
		g.step();
		g.step();
		//8th instructions by both players
		g.step();
		g.step();
		//testing to see if instructions are complete or a player has won
		assertEquals(true, g.hasNext());

	}

	@Test
	void testStep() throws InvalidBoardException, InvalidPlayerConfigurationException {
		HashMap<String, ArrayList<String>> hm = new HashMap<>();
		hm.put("Bob", new ArrayList<>(Arrays.asList("FRFUB", "RFRFL")));
		hm.put("Alice", new ArrayList<>(Arrays.asList("FBFBF", "FBFBF")));
		ArrayList<String> al = new ArrayList<>(Arrays.asList(
				"format 1", // format string required otherwise offset incorrect
				"........",
				"...+21.-",
				"........",
				"...A...B",
				"........"
		));
		g = new Game(hm, al);
		Player p = g.getPlayers().get(0);
		g.step();
		g.step();
		//player "bob" Y coordinate should decrease to 2
		assertEquals(2, p.getY());
		g.step();
		g.step();
		g.step();
		g.step();
		//Player "bob" X coordinate should be 4
		assertEquals(4, p.getX());
	}

	@Test
	void testGetWinner() throws InvalidBoardException, InvalidPlayerConfigurationException {
		HashMap<String, ArrayList<String>> hm = new HashMap<>();
		hm.put("Bob", new ArrayList<>(Arrays.asList("FRFUB", "RFLFL")));
		hm.put("Alice", new ArrayList<>(Arrays.asList("FBFBF", "BFBFB")));
		ArrayList<String> al = new ArrayList<>(Arrays.asList(
				"format 1", // format string required otherwise offset incorrect
				"........",
				"...+21.-",
				"........",
				"...A...B",
				"........"
		));
		g = new Game(hm, al);
		Player p = g.getPlayers().get(0);
		g.step();
		g.step();
		//1st instructions by both players
		g.step();
		g.step();
		//2nd instructions by both players
		g.step();
		g.step();
		//3rd instructions by both players
		g.step();
		g.step();
		//4th instructions by both players
		g.step();
		g.step();
		//5th instructions by both players
		g.step();
		g.step();
		//6th instructions by both players
		g.step();
		g.step();
		//7th instructions by both players
		g.step();
		g.step();
		//8th instructions by both players

		//should result in null because g.getWinner() isn't assigned until a player has won
		assertEquals(null, g.getWinner());

		g.step();
		g.step();
		//9th instruction by both players
		g.step();
		g.step();
		//p.getWinner should be player who won, not null
		assertEquals(g.getWinner(), p);
	}

	@Test
	void testGetBoard() throws InvalidBoardException, InvalidPlayerConfigurationException {
		HashMap<String, ArrayList<String>> hm = new HashMap<>();
		hm.put("Bob", new ArrayList<>(Arrays.asList("FRFUB", "RFLFL")));
		hm.put("Alice", new ArrayList<>(Arrays.asList("FBFBF", "BFBFB")));
		ArrayList<String> al = new ArrayList<>(Arrays.asList(
				"format 1", // format string required otherwise offset incorrect
				"........",
				"...+21.-",
				"........",
				"...A...B",
				"........"
		));
		g = new Game(hm, al);
		Board b = g.getBoard();
		//test to see if board is loaded
		assertEquals(g.getBoard(), b);
	}

	@Test
	void testGetPlayer() throws InvalidBoardException, InvalidPlayerConfigurationException {
		HashMap<String, ArrayList<String>> hm = new HashMap<>();
		hm.put("Bob", new ArrayList<>(Arrays.asList("FRFUB", "RFLFL")));
		hm.put("Alice", new ArrayList<>(Arrays.asList("FBFBF", "BFBFB")));
		ArrayList<String> al = new ArrayList<>(Arrays.asList(
				"format 1", // format string required otherwise offset incorrect
				"........",
				"...+21.-",
				"........",
				"...A...B",
				"........"
		));
		g = new Game(hm, al);
		Player p1 = g.getPlayers().get(0);
		Player p2 = g.getPlayers().get(1);
		List<Player> playerList = new ArrayList<Player>();
		playerList.add(p1);
		playerList.add(p2);
		//compare players manually input to Game class method "g.getPlayer()"
		assertEquals(g.getPlayers(), playerList);
	}

}