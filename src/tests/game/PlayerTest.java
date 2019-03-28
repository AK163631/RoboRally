package tests.game;


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

import static org.junit.jupiter.api.Assertions.*;


class TestPlayers {

	private Game g;
	private ArrayList<Player> players;

	private TestPlayers() throws InvalidBoardException, InvalidPlayerConfigurationException, IOException {
		atStart();
	}

	@BeforeEach
	private void atStart() throws InvalidBoardException, InvalidPlayerConfigurationException, IOException {
		HashMap<String, ArrayList<String>> hm = new HashMap<>();
		hm.put("Alice", new ArrayList<>(Arrays.asList("FWFLF", "FWLRL")));
		hm.put("Bob", new ArrayList<>(Arrays.asList("FWFLB", "FLFLF")));
		ArrayList<String> al = new ArrayList<>(Arrays.asList(
				"format 1", // format string required otherwise offset incorrect
				".....+......",
				".1.s<<w.E>>S",
				"...v.2^.^..v",
				"...e>>n.N<<W",
				"...-..[..].(",
				".x ....x.....",
				".....CABD..)"
		));
		g = new Game(hm, al);
		players = g.getPlayers();
	}

	@Test
	void testHealth() {
		// tests to see players health
		for (Player p : players) {
			assertEquals(p.getHealth(), 5);
		}
	}

	@Test
	void testDecreaseHealth() {
		// tests decreaseHealth to see if health decreases
		int deathHealth = 3;
		for (Player p : players) {
			p.decreaseHealth(deathHealth);
			assertEquals(p.getHealth(), 2);
		}
	}

	@Test
	void testCheckWin() {
		Player p = players.get(0);
		p.moveForward();
		p.setDirection("W");
		p.moveForward();
		p.moveForward();
		p.moveForward();
		p.setDirection("N");
		p.moveForward();
		p.setDirection("W");
		p.moveForward();
		p.setDirection("N");
		p.moveForward();
		p.moveForward();
		p.moveForward();
		p.activateEntity();
		//System.out.println(g);
		//first flag found
		p.setDirection("E");
		p.moveForward();
		p.moveForward();
		p.moveForward();
		p.moveForward();
		p.setDirection("S");
		p.moveForward();
		p.activateEntity();
		//second flag found
		//System.out.println(g);
		assertTrue(p.checkWin());

	}

	@Test
	void testAddFlag() {
		//think i tested Incorrect thing
		Player p = players.get(0);
		p.moveForward();
		p.moveForward();
		p.moveForward();
		p.moveForward();
		p.activateEntity();
		//on Second flag (incorrectly)
		p.setDirection("W");
		p.moveForward();
		p.moveForward();
		p.moveForward();
		p.moveForward();
		p.setDirection("N");
		p.moveForward();
		p.activateEntity();
		assertFalse(p.checkWin());
	}

	/*@Test
	void testAddFlag() {
		//Don't know how to test
	}*/

	@Test
	void testFireLaser() {
		Player p1 = players.get(0);
		Player p2 = players.get(1);
		p1.setDirection("W");
		p1.moveForward();
		p1.moveForward();
		p1.setDirection("E");
		p1.fireLaser();
		//tested pits to see if health dropped
		assertEquals(p2.getHealth(), 4);
	}

	@Test
	void testRestPlayer() {
		Player p = players.get(0);
		p.decreaseHealth(3);
		assertEquals(p.getHealth(), 2);
		p.restPlayer();
		assertEquals(p.getHealth(), 5);
	}

	@Test
	void testTurnACW90() {
		Player p = players.get(0);
		assertEquals(p.getDirection(), "N");
		p.turnACW90(1);
		assertEquals(p.getDirection(), "W");
		p.turnACW90(2);
		assertEquals(p.getDirection(), "E");
		p.turnACW90(3);
		assertEquals(p.getDirection(), "S");
		p.turnACW90(4);
		assertEquals(p.getDirection(), "S");

	}

	@Test
	void testTurnCW90() {
		Player p = players.get(0);
		assertEquals(p.getDirection(), "N");
		p.turnCW90(1);
		assertEquals(p.getDirection(), "E");
		p.turnCW90(2);
		assertEquals(p.getDirection(), "W");
		p.turnCW90(3);
		assertEquals(p.getDirection(), "S");
		p.turnCW90(4);
		assertEquals(p.getDirection(), "S");
	}

}