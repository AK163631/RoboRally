package tests.board;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import board.Pit;

class PitTest {

	int x = 1;
	int y = 1;
	String repr = "2";
	Pit p1;

	@BeforeEach
	void setUp() throws Exception {
		p1 = new Pit(x, y, repr);
	}

	@Test
	void testGetX() {
		assertEquals(1, p1.getX());
		assertFalse(p1.getX() == 2);

	}

	@Test
	void testGetY() {
		assertEquals(1, p1.getY());
		assertFalse(p1.getY() == 2);
	}

	@Test
	void testToString() {
		assertEquals("2", p1.toString());
		assertFalse(p1.toString() == "repr2");
	}

	@Test
	void testSetRepr() {
		p1.setRepr("repr2");
		assertEquals("repr2", p1.toString());
		assertFalse(p1.toString() == "repr");
	}

}
