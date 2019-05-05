package tests.board;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import board.Gears;

class GearsTest {

	int x = 1;
	int y = 1;
	String repr = "2";
	Gears g1;

	@BeforeEach
	void setUp() throws Exception {
		g1 = new Gears(x, y, repr);
	}

	@Test
	void testGetX() {
		assertEquals(1, g1.getX());

	}

	@Test
	void testGetY() {
		assertEquals(1, g1.getY());
	}

	@Test
	void testToString() {
		assertEquals("2", g1.toString());
		assertFalse(g1.toString().contentEquals("repr2"));
	}

	@Test
	void testSetRepr() {
		g1.setRepr("repr2");
		assertEquals("repr2", g1.toString());
		assertFalse(g1.toString().contentEquals("repr"));
	}
}
