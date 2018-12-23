package org.stenflo.connectx;

/**
 * @author Erik Stenflo
 */
public class ConnectX {

	public enum GameState {
		STATE_INIT,
		STATE_GAME_ON,
		STATE_GAME_OVER;
	}

	private int columns;
	private int rows;
	private int winningCount;
	private int players;
	private int winningPlayer = -1;
	private int playersTurn = -1;
	private int leadingPlayer = -1;
	private int leadingCount = -1;
	private boolean leadMatch = false;
	private boolean verbose = false;

	private GameState gameState;
	private Integer[][] board;

	/**
	 * The default constructor provides the standard ConnectFour game, as we know it,
	 * with 7 columns, 6 rows, 2 players, and where 4 in a row constitute a win.
	 */
	public ConnectX() {
		this(7,6,4,2);
	}

	/**
	 * This constructor provides a game configuration based on the ConnectFour game
	 * but with variable rows, columns, number of players and winning configuration.
	 */
	public ConnectX(int columns, int rows, int winningCount, int players) {
		if (columns < 2 || rows < 2 || winningCount < 2 || winningCount > columns || winningCount > rows || players < 2) {
			throw new RuntimeException("Invalid game dimensions.");
		}
		this.columns = columns;
		this.rows = rows;
		this.winningCount = winningCount;
		this.players = players;
		this.board = new Integer[columns][rows];
		this.gameState = GameState.STATE_INIT;
	}

	public boolean takeTurn(int col, int player) {
		if (this.gameState == GameState.STATE_GAME_OVER) {
			throw new RuntimeException("Invalid game state.");
		}
		if (col < 0 || col > columns-1) {
			throw new RuntimeException("Invalid column.");
		}
		if (player < 0 || player > players-1) {
			throw new RuntimeException("Invalid player.");
		}
		if (this.gameState == GameState.STATE_GAME_ON && player != this.playersTurn) {
			throw new RuntimeException("Sorry player, it\'s not your turn.");
		}
		if (this.board[col][rows-1] != null) {
			throw new RuntimeException("Column is full. Illegal Move.");
		}
		executeTurn(col, player);
		printBoard();
		if (this.gameState == GameState.STATE_GAME_OVER) {
			return true;
		}
		return false;
	}

	private void updateBoard(int slot, int player) {
		for (byte row = 0; row < rows; row++) {
			if (this.board[slot][row] == null) {
				this.board[slot][row] = player;
				break;
			}
		}
	}

	private void executeTurn(int slot, int player) {
		if (this.gameState == GameState.STATE_INIT) {
			this.gameState = GameState.STATE_GAME_ON;
			this.playersTurn = player;
			this.leadingCount = 1;
			this.leadingPlayer = player;
			System.out.println("Player" + (player+1) + " started the game.");
		}
		else {
			System.out.println("Player" + (player+1) + " is taking their turn.");
		}
		updateBoard(slot, player);
		// check verticals
		for (int col = 0; col < this.columns; col++) {
			StringBuffer sb = new StringBuffer();
			sb.append(debug(col, 0));
			if (this.board[col][0] == null) continue;
			int cnt = 1;
			for (int row = 1; row < this.rows; row++) {
				sb.append(debug(col, row));
				if (this.board[col][row] == null) {
					break;
				}
				else if (this.board[col][row] != this.board[col][row-1]) {
					cnt = 1;
				}
				else cnt++;
				if (this.leadingCount < cnt) {
					this.leadingCount = cnt;
					this.leadingPlayer = this.board[col][row];
					this.leadMatch = false;
				}
				else if (this.leadingCount == cnt && this.leadingPlayer != this.board[col][row]) {
					this.leadMatch = true;
				}
				if (cnt == this.winningCount) {
					this.winningPlayer = this.board[col][row];
					this.gameState = GameState.STATE_GAME_OVER;
					return;
				}
			}
			if (verbose) System.out.println("COL:" + sb.toString());
		}
		// check horizontals
		for (int row = 0; row < this.rows; row++) {
			StringBuffer sb = new StringBuffer();
			int cnt = 0;
			sb.append(debug(0, row));
			if (this.board[0][row] != null) cnt++;
			for (int col = 1; col < this.columns; col++) {
				sb.append(debug(col, row));
				if (this.board[col][row] == null) {
					cnt = 0;
					continue;
				}
				else if (this.board[col][row] != this.board[col-1][row]) {
					cnt = 1;
				}
				else cnt++;
				if (this.leadingCount < cnt) {
					this.leadingCount = cnt;
					this.leadingPlayer = this.board[col][row];
					this.leadMatch = false;
				}
				else if (this.leadingCount == cnt && this.leadingPlayer != this.board[col][row]) {
					this.leadMatch = true;
				}
				if (cnt == this.winningCount) {
					this.winningPlayer = this.board[col][row];
					this.gameState = GameState.STATE_GAME_OVER;
					return;
				}
			}
			if (verbose) System.out.println("ROW:" + sb.toString());
		}
		// check ascending diagonals
		for (int rowOffset = this.rows - 1; rowOffset > 0; rowOffset--) {
			int cnt = 0;
			StringBuffer sb = new StringBuffer();
			sb.append(debug(0, rowOffset));
			if (this.board[0][rowOffset] != null) cnt = 1;
			for (int index = 1; index < this.rows - rowOffset && index < this.columns; index++) {
				sb.append(debug(index, index+rowOffset));
				if (this.board[index][index+rowOffset] == null) {
					cnt = 0;
					continue;
				}
				else if (this.board[index][index+rowOffset] != this.board[index-1][index+rowOffset-1]) {
					cnt = 1;
				}
				else cnt++;
				if (this.leadingCount < cnt) {
					this.leadingCount = cnt;
					this.leadingPlayer = this.board[index][index+rowOffset];
					this.leadMatch = false;
				}
				else if (this.leadingCount == cnt && this.leadingPlayer != this.board[index][index+rowOffset]) {
					this.leadMatch = true;
				}
				if (cnt == this.winningCount) {
					this.winningPlayer = this.board[index][index+rowOffset];
					this.gameState = GameState.STATE_GAME_OVER;
					return;
				}
			}
			if (verbose) System.out.println("ASC:" + sb.toString());
		}
		for (int colOffset = 0; colOffset < this.columns; colOffset++) {
			int cnt = 0;
			StringBuffer sb = new StringBuffer();
			sb.append(debug(colOffset, 0));
			if (this.board[colOffset][0] != null) cnt = 1;
			for (int index = 1; index < this.rows && index < this.columns - colOffset; index++) {
				sb.append(debug(index+colOffset, index));
				if (this.board[index+colOffset][index] == null) {
					cnt = 0;
					continue;
				}
				else if (this.board[index+colOffset][index] != this.board[index+colOffset-1][index-1]) {
					cnt = 1;
				}
				else cnt++;
				if (this.leadingCount < cnt) {
					this.leadingCount = cnt;
					this.leadingPlayer = this.board[index+colOffset][index];
					this.leadMatch = false;
				}
				else if (this.leadingCount == cnt && this.leadingPlayer != this.board[index+colOffset][index]) {
					this.leadMatch = true;
				}
				if (cnt == this.winningCount) {
					this.winningPlayer = this.board[index+colOffset][index];
					this.gameState = GameState.STATE_GAME_OVER;
					return;
				}
			}
			if (verbose) System.out.println("ASC:" + sb.toString());
		}
		// check descending diagonals
		for (int rowOffset = this.rows - 1; rowOffset >= 0; rowOffset--) {
			int cnt = 0;
			StringBuffer sb = new StringBuffer();
			sb.append("[0," + (this.rows-rowOffset-1) + "]");
			if (this.board[0][this.rows-rowOffset-1] != null) cnt = 1;
			for (int index = 1; index < this.rows - rowOffset && index < this.columns; index++) {
				sb.append(debug(index, this.rows-rowOffset-index-1));
				if (this.board[index][this.rows-rowOffset-index-1] == null) {
					cnt = 0;
					continue;
				}
				else if (this.board[index][this.rows-rowOffset-index-1] != this.board[index-1][this.rows-rowOffset-index]) {
					cnt = 1;
				}
				else cnt++;
				if (this.leadingCount < cnt) {
					this.leadingCount = cnt;
					this.leadingPlayer = this.board[index][this.rows-rowOffset-index-1];
					this.leadMatch = false;
				}
				else if (this.leadingCount == cnt && this.leadingPlayer != this.board[index][this.rows-rowOffset-index-1]) {
					this.leadMatch = true;
				}
				if (cnt == this.winningCount) {
					this.winningPlayer = this.board[index][this.rows-rowOffset-index-1];
					this.gameState = GameState.STATE_GAME_OVER;
					return;
				}
			}
			if (verbose) System.out.println("DSC:" + sb.toString());
		}
		for (int colOffset = 1; colOffset < this.columns; colOffset++) {
			int cnt = 0;
			StringBuffer sb = new StringBuffer();
			sb.append(debug(this.columns-colOffset, this.rows-1));
			if (this.board[this.columns-colOffset][this.rows-1] != null) cnt = 1;
			for (int index = 1; index < this.rows && index < this.columns - colOffset; index++) {
				sb.append(debug(colOffset+index, this.rows-index-1));
				if (this.board[colOffset+index][this.rows-index-1] == null) {
					cnt = 0;
					continue;
				}
				else if (this.board[colOffset+index][this.rows-index-1] != this.board[colOffset+index-1][this.rows-index]) {
					cnt = 1;
				}
				else cnt++;
				if (this.leadingCount < cnt) {
					this.leadingCount = cnt;
					this.leadingPlayer = this.board[colOffset+index][this.rows-index-1];
					this.leadMatch = false;
				}
				else if (this.leadingCount == cnt && this.leadingPlayer != this.board[colOffset+index][this.rows-index-1]) {
					this.leadMatch = true;
				}
				if (cnt == this.winningCount) {
					this.winningPlayer = this.board[colOffset+index][this.rows-index-1];
					this.gameState = GameState.STATE_GAME_OVER;
					return;
				}
			}
			if (verbose) System.out.println("DSC:" + sb.toString());
		}
		this.gameState = GameState.STATE_GAME_ON;
		if (player == this.players-1) this.playersTurn = 0;
		else this.playersTurn++;
	}

	private static final String ANSI_YELLOW = "\u001B[33m";
	private static final String ANSI_RED = "\u001B[31m";
	private static final String ANSI_GREEN = "\u001B[32m";
	private static final String ANSI_BLUE = "\u001B[34m";
	private static final String ANSI_PURPLE = "\u001B[35m";
	private static final String ANSI_RESET = "\u001B[0m";

	private String debug(int col, int row) {
		if (board[col][row] == null) return "[" + col + "," + row + "]";
		switch (board[col][row]) {
		case 0:
			return ANSI_YELLOW + "[" + col + "," + row + "]" + ANSI_RESET;
		case 1:
			return ANSI_RED + "[" + col + "," + row + "]" + ANSI_RESET;
		case 2:
			return ANSI_GREEN + "[" + col + "," + row + "]" + ANSI_RESET;
		case 3:
			return ANSI_BLUE + "[" + col + "," + row + "]" + ANSI_RESET;
		default:
			return ANSI_PURPLE + "[" + col + "," + row + "]" + ANSI_RESET;
		}
	}

	public void printBoard() {
		for (int row = this.rows - 1; row >= 0; row--) {
			StringBuffer sb = new StringBuffer();
			for (int col = 0; col < this.columns; col++) {
				if (board[col][row] == null) sb.append(" . ");
				else switch (board[col][row]) {
				case 0:
					sb.append(ANSI_YELLOW + " " + (board[col][row]+1) + " " + ANSI_RESET);
					break;
				case 1:
					sb.append(ANSI_RED + " " + (board[col][row]+1) + " " + ANSI_RESET);
					break;
				case 2:
					sb.append(ANSI_GREEN + " " + (board[col][row]+1) + " " + ANSI_RESET);
					break;
				case 3:
					sb.append(ANSI_BLUE + " " + (board[col][row]+1) + " " + ANSI_RESET);
					break;
				default:
					sb.append(ANSI_PURPLE + " " + (board[col][row]+1) + " " + ANSI_RESET);
				}
			}
			System.out.println(sb.toString());
		}
		System.out.println(getGameStatus());
	}

	public String getGameStatus() {
		if (this.gameState == GameState.STATE_INIT) {
			return "The game hasn\'t started yet. Anyone can take the first turn.";
		}
		else if (this.gameState == GameState.STATE_GAME_ON && this.leadMatch) {
			return "Player" + (this.playersTurn+1)
					+ "\'s turn. Multiple players are leading with "
					+ this.leadingCount + " items in a row.";
		}
		else if (this.gameState == GameState.STATE_GAME_ON && !this.leadMatch) {
			return "Player" + (this.playersTurn+1) + "\'s turn. Player"
					+ (this.leadingPlayer+1) + " is leading with "
					+ this.leadingCount + " items in a row.";
		}
		else if (this.gameState == GameState.STATE_GAME_OVER) {
			return "Game Over. Player" + (this.winningPlayer+1) + " is the winner!";
		}
		else return "Game Error";
	}

	public int getWinningPlayer() {
		if (this.gameState != GameState.STATE_GAME_OVER) {
			throw new RuntimeException("Game is not over yet.");
		}
		return this.winningPlayer;
	}

	public int getLeadingPlayer() {
		if (this.gameState == GameState.STATE_INIT) {
			throw new RuntimeException("Game hasn\'t started yet.");
		}
		return this.leadingPlayer;
	}

	public int getLeadingCount() {
		if (this.gameState == GameState.STATE_INIT) {
			throw new RuntimeException("Game hasn\'t started yet.");
		}
		return this.leadingCount;
	}

	public int getPlayersTurn() {
		if (this.gameState == GameState.STATE_INIT) {
			throw new RuntimeException("Game hasn\'t started yet.");
		}
		return this.playersTurn;
	}

}
