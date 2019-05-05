package tests.board;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import board.Flag;

class FlagTest {

	Flag f1;
	int x = 1;
	int y = 1;
	String repr = "2";
	int number = Integer.parseInt(repr);

	@BeforeEach
	void setUp() throws Exception {

		f1 = new Flag(x, y, repr);
	}

	@Test
	void testGetX() {
		assertEquals(1, f1.getX());
		assertFalse(f1.getX() == 3);
	}

	@Test
	void testGetY() {
		assertEquals(1, f1.getY());
		assertFalse(f1.getY() == 4);
	}

	@Test
	void testGetValue() {
		assertEquals(2, f1.getValue());
		assertFalse(f1.getValue() == 1);
	}

	@Test
	void testToString() {
		assertEquals("2", f1.toString());
		assertFalse(f1.toString().contentEquals("repr2"));
	}

	@Test
	void testSetRepr() {
		f1.setRepr("repr2");
		assertEquals("repr2", f1.toString());
		assertFalse(f1.toString().contentEquals("repr"));
	}

}
