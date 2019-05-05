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


class PlayerTests {

	private Game g;
	private ArrayList<Player> players;

	private PlayerTests() throws InvalidBoardException, InvalidPlayerConfigurationException, IOException {
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
			assertEquals(5, p.getHealth());
		}
	}

	@Test
	void testDecreaseHealth() {
		// tests decreaseHealth to see if health decreases
		int deathHealth = 3;
		for (Player p : players) {
			p.decreaseHealth(deathHealth);
			assertEquals(2, p.getHealth());
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
		assertEquals(4, p2.getHealth());
	}

	@Test
	void testRestPlayer() {
		Player p = players.get(0);
		p.decreaseHealth(3);
		assertEquals(2, p.getHealth());
		p.restPlayer();
		assertEquals(5, p.getHealth());
	}

	@Test
	void testTurnACW90() {
		Player p = players.get(0);
		assertEquals("N", p.getDirection());
		p.turnACW90(1);
		assertEquals("W", p.getDirection());
		p.turnACW90(2);
		assertEquals("E", p.getDirection());
		p.turnACW90(3);
		assertEquals("S", p.getDirection());
		p.turnACW90(4);
		assertEquals("S", p.getDirection());

	}

	@Test
	void testTurnCW90() {
		Player p = players.get(0);
		assertEquals("N", p.getDirection());
		p.turnCW90(1);
		assertEquals("E", p.getDirection());
		p.turnCW90(2);
		assertEquals("W", p.getDirection());
		p.turnCW90(3);
		assertEquals("S", p.getDirection());
		p.turnCW90(4);
		assertEquals("S", p.getDirection());
	}

	@Test
	void testMoveForward() {
		Player p = this.g.getPlayers().get(0);
		p.moveForward();
		//test Y
		assertEquals(2, p.getY());
		p.setDirection("W");
		p.moveForward();
		// test X
		assertEquals(2, p.getX());
	}

	@Test
	void testSetLocation() {
		Player p = this.g.getPlayers().get(0);
		assertEquals(3, p.getX());
		assertEquals(3, p.getY());
		//testing the setLocation method
		//COULD BE AN ERROR IF YOU USE (4,4) DUE TO IT BEING A INVALID LOCATION
		p.setLocation(2, 2);
		assertEquals(2, p.getX());
		assertEquals(2, p.getY());
	}

	@Test
	void testGetX() {
		Player p = this.g.getPlayers().get(0);
		//testing to see if the player is on the default X coordinate
		assertEquals(3, p.getX());
	}

	@Test
	void testGetY() {
		Player p = this.g.getPlayers().get(0);
		//testing to see if the player is on the default Y coordinate
		assertEquals(3, p.getY());
	}

	@Test
	void testSetName() {
		Player p = this.g.getPlayers().get(0);
		//Testing to see the default name
		assertEquals("Alice", p.getName());
		p.setName("Jeff");
		//testing the setName method
		assertEquals("Jeff", p.getName());
	}

	@Test
	void testGetName() {
		Player p = this.g.getPlayers().get(0);
		//test to see if the default name is set
		assertEquals("Alice", p.getName());
	}

	@Test
	void testGetRepr() {
		Player p = this.g.getPlayers().get(0);
		//test should produce "A"
		assertEquals("A", p.getRepr());
	}

	@Test
	void testSetDirection() {
		Player p = this.g.getPlayers().get(0);
		assertEquals("N", p.getDirection());
		p.setDirection("E");
		//testing player facing East
		assertEquals("E", p.getDirection());
		p.setDirection("S");
		assertEquals("S", p.getDirection());
		p.setDirection("W");
		assertEquals("W", p.getDirection());

	}

	@Test
	void testGetDirection() {
		Player p = this.g.getPlayers().get(0);
		assertEquals("N", p.getDirection());
		p.setDirection("S");
		assertEquals("S", p.getDirection());
	}

}