package tests.board;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import board.Board;
import board.Conveyor;

class ConveyorTest {

	Conveyor c1;
	Board board;
	String dir = "N";
	String dir2 = "S";
	String dir3 = "E";
	String dir4 = "W";
	String repr = "Repr1";
	String finalRepr = "frep";
	int x = 1;
	int y = 1;


	@BeforeEach
	void setUp() throws Exception {


		c1 = new Conveyor(x, y, repr, dir, board);

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
	//@Test
	//void testPlayerInDir() {


//	}

	@Test
	void testSetRepr() {
		c1.setRepr("rep2");
		assertEquals("rep2", c1.toString());
		assertFalse(c1.toString().contentEquals("repr"));
	}


}
