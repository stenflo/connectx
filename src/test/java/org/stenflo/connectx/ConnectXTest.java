package org.stenflo.connectx;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

public class ConnectXTest {

	private static final int YELLOW = 0;
	private static final int RED = 1;

	public ConnectXTest() {
	}

	@Test
	public void testVerticalWin() {
		ConnectX connect4 = new ConnectX();
		assertFalse(connect4.takeTurn(0, YELLOW), "Test...");
		assertFalse(connect4.takeTurn(1, RED), "Test...");
		assertFalse(connect4.takeTurn(0, YELLOW), "Test...");
		assertFalse(connect4.takeTurn(2, RED), "Test...");
		assertFalse(connect4.takeTurn(0, YELLOW), "Test...");
		assertFalse(connect4.takeTurn(3, RED), "Test...");
		assertTrue(connect4.takeTurn(0, YELLOW), "Vertical Win");
	}

	@Test
	public void testYellowHorizontalWin() {
		ConnectX connect4 = new ConnectX();
		assertFalse(connect4.takeTurn(0, YELLOW), "Test...");
		assertFalse(connect4.takeTurn(0, RED), "Test...");
		assertFalse(connect4.takeTurn(1, YELLOW), "Test...");
		assertFalse(connect4.takeTurn(0, RED), "Test...");
		assertFalse(connect4.takeTurn(2, YELLOW), "Test...");
		assertFalse(connect4.takeTurn(1, RED), "Test...");
		assertTrue(connect4.takeTurn(3, YELLOW), "Yellow Horizontal Win");
	}

	@Test
	public void testRedHorizontalWin() {
		ConnectX connect4 = new ConnectX();
		assertFalse(connect4.takeTurn(0, RED), "Test...");
		assertFalse(connect4.takeTurn(0, YELLOW), "Test...");
		assertFalse(connect4.takeTurn(1, RED), "Test...");
		assertFalse(connect4.takeTurn(0, YELLOW), "Test...");
		assertFalse(connect4.takeTurn(2, RED), "Test...");
		assertFalse(connect4.takeTurn(1, YELLOW), "Test...");
		assertTrue(connect4.takeTurn(3, RED), "Red Horizontal Win");
	}

	@Test
	public void testDiagonalAscendingWin() {
		ConnectX connect4 = new ConnectX();
		assertFalse(connect4.takeTurn(0, YELLOW), "Test...");
		assertFalse(connect4.takeTurn(1, RED), "Test...");
		assertFalse(connect4.takeTurn(1, YELLOW), "Test...");
		assertFalse(connect4.takeTurn(2, RED), "Test...");
		assertFalse(connect4.takeTurn(2, YELLOW), "Test...");
		assertFalse(connect4.takeTurn(3, RED), "Test...");
		assertFalse(connect4.takeTurn(2, YELLOW), "Test...");
		assertFalse(connect4.takeTurn(3, RED), "Test...");
		assertFalse(connect4.takeTurn(3, YELLOW), "Test...");
		assertFalse(connect4.takeTurn(0, RED), "Test...");
		assertTrue(connect4.takeTurn(3, YELLOW), "Diagonal Ascending Win");
	}

	@Test
	public void testDiagonalDescendingWin() {
		ConnectX connect4 = new ConnectX();
		assertFalse(connect4.takeTurn(0, YELLOW), "Test...");
		assertFalse(connect4.takeTurn(1, RED), "Test...");
		assertFalse(connect4.takeTurn(2, YELLOW), "Test...");
		assertFalse(connect4.takeTurn(3, RED), "Test...");
		assertFalse(connect4.takeTurn(4, YELLOW), "Test...");
		assertFalse(connect4.takeTurn(5, RED), "Test...");
		assertFalse(connect4.takeTurn(1, YELLOW), "Test...");
		assertFalse(connect4.takeTurn(2, RED), "Test...");
		assertFalse(connect4.takeTurn(3, YELLOW), "Test...");
		assertFalse(connect4.takeTurn(4, RED), "Test...");
		assertFalse(connect4.takeTurn(5, YELLOW), "Test...");
		assertFalse(connect4.takeTurn(6, RED), "Test...");
		assertFalse(connect4.takeTurn(0, YELLOW), "Test...");
		assertFalse(connect4.takeTurn(1, RED), "Test...");
		assertFalse(connect4.takeTurn(2, YELLOW), "Test...");
		assertFalse(connect4.takeTurn(3, RED), "Test...");
		assertFalse(connect4.takeTurn(4, YELLOW), "Test...");
		assertFalse(connect4.takeTurn(5, RED), "Test...");
		assertFalse(connect4.takeTurn(6, YELLOW), "Test...");
		assertFalse(connect4.takeTurn(0, RED), "Test...");
		assertTrue(connect4.takeTurn(1, YELLOW), "Diagonal Descending Win");
	}

}
