package tests.board;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import board.LaserMarker;

class LaserMarkerTest {
	int x = 1;
	int y = 1;
	String repr;

	LaserMarker l1;

	@BeforeEach
	void setUp() throws Exception {
		l1 = new LaserMarker(x, y, repr);
	}

	@Test
	void testgetX() {
		assertEquals(1, l1.getX());
		assertFalse(l1.getX() == 2);
	}

	@Test
	void testGetY() {
		assertEquals(1, l1.getY());
		assertFalse(l1.getY() == 2);
	}

	@Test
	void testToString() {
		assertEquals("2", l1.toString());
		assertFalse(l1.toString().contentEquals("repr2"));
	}

	@Test
	void testSetRepr() {
		l1.setRepr("repr2");
		assertEquals("repr2", l1.toString());
		assertFalse(l1.toString().contentEquals("repr"));
	}

}
