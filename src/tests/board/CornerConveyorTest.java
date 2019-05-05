package tests.board;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import board.CornerConveyor;

class CornerConveyorTest {

	CornerConveyor c1;

	int x = 1;
	int y = 1;
	String repr = "repr";

	@BeforeEach
	void setUp() throws Exception {

		c1 = new CornerConveyor(x, y, repr);
	}

	@Test
	void testGetX() {
		assertEquals(1, c1.getX());
		assertFalse(c1.getX() == 2);
	}

	@Test
	void testGetY() {
		assertEquals(1, c1.getY());
		assertFalse(c1.getY() == 2);
	}

	@Test
	void testToString() {
		assertEquals("repr", c1.toString());
		assertFalse(c1.toString().contentEquals("repr2"));
	}

	@Test
	void testSetRepr() {
		c1.setRepr("repr2");
		assertEquals("repr2", c1.toString());
		assertFalse(c1.toString().contentEquals("repr"));

	}

}
