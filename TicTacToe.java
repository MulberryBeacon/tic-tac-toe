import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

/**
 * A simple tic-tac-toe solver.
 *
 * @author Eduardo Ferreira
 */
public class TicTacToe {

	/** Playing symbols. */
	private enum Symbol { 
		EMPTY(0, " "), CIRCLE(1, "\u25CB"), CROSS(2, "X");
		//EMPTY(0, " "), CIRCLE(1, "\u25EF"), CROSS(2, "X");
		//EMPTY(0, " "), CIRCLE(1, "\u3007"), CROSS(2, "X");
		
		/** Integer representation of the playing symbol. */
		private int value;

		private String text;

		/** 
		 * Creates an instance of {@Symbol}.
		 *
		 * @param value
		 * @param text
		 */
		private Symbol(int value, String text) {
			this.value = value;
			this.text = text;
		}

		/**
		 * Gets the value.
		 * 
		 * @return The integer representation of a playing symbol
		 */
		public int getValue() {
			return value;
		}

		/**
		 * Gets the text.
		 * 
		 * @return The integer representation of a playing symbol
		 */
		public String getText() {
			return text;
		}

		/**
		 * Gets the XML tag for the given textual convention name.
		 * 
		 * @param name Name of the textual convention
		 * @return The XML tag
		 */
		public static String getTextFromValue(int value) {
			for (Symbol symbol : Symbol.values()) {
				if (symbol.value == value) {
					return symbol.getText();
				}
			}

			return null;
		}
	}

	/** Size of the grid. */
	private static final int SIZE = 3;

	/** Number of positions in the grid. */
	private static final int POSITIONS = SIZE * SIZE;

	/** Indicates a near win configuration for the opponent. */
	private static final int OPPONENT = -1;

	/** X and Y coordinate value of the central position in the grid. */
	private static final int CENTRE = 1;

	/** Number of diagonals in the grid. */
	private static final int DIAGONALS = 2;

	/** Indexes used to navigate through the matrixes with information regarding rows, columns and diagonals. */
	private static final int FILLED = 0;
	private static final int SYMBOLS = 1;
	private static final int COORDINATES = 2;
	private static final int COORDINATES_Y = 3;

	/** Random number generator. */
	private static final Random RANDOM = new Random();

	/** Standard input reader. */
	private static final Scanner scanner = new Scanner(System.in);

	/**  */
	private static final Symbol[] SYMBOL_VALUES = Symbol.values();
	private static final int SYMBOL_SIZE = SYMBOL_VALUES.length;

	/** Game grid. */
	private int[][] grid;

	/**
	 * For each row, the following information is kept:
	 * Index 0: number of filled positions
	 * Index 1: sum of the int values of the playing symbols in the filled positions
	 * Index 2: sum of the y-coordinate values of the filled positions
	 */
	private int[][] rows;

	/**
	 * For each column, the following information is kept:
	 * Index 0: number of filled positions
	 * Index 1: sum of the int values of the playing symbols in the filled positions
	 * Index 2: sum of the x-coordinate values of the filled positions
	 */
	private int[][] columns;

	/**
	 * For each diagonal, the following information is kept:
	 * Index 0: number of filled positions
	 * Index 1: sum of the int values of the playing symbols in the filled positions
	 * Index 2: sum of the x-coordinate values of the filled positions
	 * Index 3: sum of the y-coordinate values of the filled positions
	 */
	private int[][] diagonals;

	/** Counter to keep track of the number of plays. */
	private int plays;

	/**
	 * Creates an instance of {@TicTacToe}.
	 */
	public TicTacToe() {
		grid = new int[SIZE][SIZE];
		rows = new int[SIZE][SIZE];
		columns = new int[SIZE][SIZE];
		diagonals = new int[DIAGONALS][SIZE + 1];
		plays = 0;
	}

	/**
	 * Game engine that alternates between computer generated and human input moves.
	 */
	public void play() {
		int[] position = null;
		int symbol = getRandomSymbol();

		while (true) {
			position = chooseMove(Symbol.CIRCLE.getValue());
			fillPosition(position[0], position[1], Symbol.CIRCLE.getValue());
			System.out.println("Computer says: (" + ++position[0] + "," + ++position[1] + ")");
			System.out.println(this);
			if (isGameOver()) {
				break;
			}

			position = readMove();
			fillPosition(position[0], position[1], Symbol.CROSS.getValue());
			System.out.println("Puny human played: (" + ++position[0] + "," + ++position[1] + ")");
			System.out.println(this);
			if (isGameOver()) {
				break;
			}
		}
	}

	/**
	 * Fills a position in the grid.
	 *
	 * @param x X-coordinate of the grid position
	 * @param y Y-coordinate of the grid position
	 * @param symbol Playing symbol
	 */
	private void fillPosition(int x, int y, int symbol) {
		grid[x][y] = symbol;
		++plays;

		++rows[x][FILLED];
		rows[x][SYMBOLS] += symbol;
		rows[x][COORDINATES] += y;

		++columns[y][FILLED];		
		columns[y][SYMBOLS] += symbol;
		columns[y][COORDINATES] += x;

		if (isPartOfDiagonal(x, y)) {
			int start = (x == y ? 0 : 1);
			int end = (x == CENTRE && y == CENTRE ? DIAGONALS : start + 1);

			for (int i = start; i < end; i++) {
				++diagonals[i][FILLED];
				diagonals[i][SYMBOLS] += symbol;
				diagonals[i][COORDINATES] += x;
				diagonals[i][COORDINATES_Y] += y;
			}
		}
	}

	/**
	 * Reads the coordinates inserted by a human player in the console.
	 *
	 * @return The coordinates of the position that a human player considers to be the best possible move
	 */
	public int[] readMove() {
		int x = -1;
		int y = -1;

		while (true) {
			System.out.print("Please insert a valid pair of coordinates: ");

			try {
				if (scanner.hasNextInt()) {
					x = scanner.nextInt() - 1;
				}
				else {
					scanner.next();
				}

				if (scanner.hasNextInt()) {
					y = scanner.nextInt() - 1;
				}
				else {
					scanner.next();
				}
			}
			catch (InputMismatchException e) {
				System.err.println("\nERROR: An incorrect number was read.\n");
			}
			catch (NoSuchElementException e) {
				System.err.println("\nERROR: There is no more input to read.\n");
			}
			catch (IllegalStateException e) {
				System.err.println("\nERROR: Cannot read any more input.\n");
			}
			finally {
				if (!isPositionValid(x, y)) {
					System.err.println("\nERROR: The position you chose has invalid coordinates.\n");
					continue;
				}

				if (!isPositionEmpty(x, y)) {
					System.err.println("\nERROR: The position you chose is not empty.\n");
					continue;
				}

				return new int[]{x, y};
			}
		}
	}

	/**
	 * Chooses the best possible move.
	 *
	 * @param symbol Playing symbol
	 * @return The coordinates of the position that is the best possible move
	 */
	public int[] chooseMove(int symbol) {

		int[] position = null;

		// Case 1:
		// If the grid is still empty or if only one move has been played by the opponent, randomly choose one of the
		// corners and play
		if (isGridEmpty() || plays == 1) {
			position = getRandomCorner();
		}

		// Case 2:
		// Check if there is a near win situation and take it, thus defeating the puny human and claiming bragging
		// righs over the Human race!
		// Alas, the puny human might be in a near win situation. If so, proceeds to cutting him off by filling the
		// last position.
		if (position == null) {
			position = checkForNearWin(symbol);
		}

		// Case n:
		// When all else fails, choose a random position and play
		if (position == null) {
			do {
				position = getRandomPosition();
			}
			while (!isPositionEmpty(position[0], position[1]));
		}

		return position;
	}

	/**
	 * Checks if the game is over.
	 *
	 * @return True if a winning configuration has been reached or if the grid is full; false otherwise
	 */
	public boolean isGameOver() {
		if (isGridFull()) {
			return true;
		}

		for (int i = 0; i < SIZE; i++) {
			if (isFullWin(rows, i) || isFullWin(columns, i) || (i < SIZE - 1 && isFullWin(diagonals, i))) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Checks if there is a full win configuration in the given structure, which might be related to rows, columns or
	 * diagonals.
	 *
	 * @param structure Structure 
	 * @param index Index of the structure entry
	 * @return True if there is a new win configuration; false otherwise
	 */
	private boolean isFullWin(int[][] structure, int index) {
		return structure[index][FILLED] == SIZE && structure[index][SYMBOLS] % SIZE == 0;
	}

	/**
	 * Checks if there is a near win configuration in the given structure, which might be related to rows, columns or
	 * diagonals.
	 *
	 * @param structure Structure 
	 * @param index Index of the structure entry
	 * @return True if there is a new win configuration; false otherwise
	 */
	private boolean isNearWin(int[][] structure, int index) {
		return structure[index][FILLED] == SIZE - 1 && structure[index][SYMBOLS] % (SIZE - 1) == 0;
	}

	/**
	 * Checks if a position is part of a diagonal row.
	 *
	 * @param x X-coordinate of the grid position
	 * @param y Y-coordinate of the grid position
	 * @return True if the position is part of a diagonal row; false otherwise
	 */
	private boolean isPartOfDiagonal(int x, int y) {
		return (x != CENTRE && y != CENTRE) || (x == CENTRE && y == CENTRE);
	}

	/**
	 * Checks if the grid is empty.
	 *
	 * @return True if the grid is empty; false otherwise
	 */
	private boolean isGridEmpty() {
		return plays == 0;
	}

	/**
	 * Checks if the grid is full.
	 *
	 * @return True if the grid is full; false otherwise
	 */
	private boolean isGridFull() {
		return plays == POSITIONS;
	}

	/**
	 * Checks if a position in the grid is empty.
	 * 
	 * @param x X-coordinate of the grid position
	 * @param y Y-coordinate of the grid position
	 * @return True if the grid position is empty; false otherwise
	 */
	private boolean isPositionEmpty(int x, int y) {
		return grid[x][y] == Symbol.EMPTY.getValue();
	}

	/**
	 * Checks if a position in the grid is valid.
	 * 
	 * @param x X-coordinate of the grid position
	 * @param y Y-coordinate of the grid position
	 * @return True if the grid position is valid; false otherwise
	 */
	private boolean isPositionValid(int x, int y) {
		return x >= 0 && x < SIZE && y >= 0 && y < SIZE;
	}

	/**
	 * Searches the grid to see if the opponent is in a near win situation, with two values already filled in a row,
	 * column or diagonal. If so, determines the coordinates of the remaining position that needs to be filled in order
	 * to prevent the opponent to win.
	 *
	 * @param symbol Playing symbol
	 * @return The coordinates of the position that needs to be filled in order to prevent the opponent to win
	 */
	private int[] checkForNearWin(int symbol) {
		int[] result = null;

		for (int i = 0; i < SIZE; i++) {

			// Checks the current row
			if (isNearWin(rows, i)) {
				result = new int[]{i, SIZE - rows[i][COORDINATES]};
				if (rows[i][SYMBOLS] == symbol * (SIZE - 1)) {
					return result;
				}
			}

			// Checks the current column
			if (isNearWin(columns, i)) {
				result = new int[]{SIZE - columns[i][COORDINATES], i};
				if (columns[i][SYMBOLS] == symbol * (SIZE - 1)) {
					return result;
				}
			}

			// Checks the current diagonal
			if (i < SIZE - 1) {
				if (isNearWin(diagonals, i)) {
					result = new int[]{SIZE - diagonals[i][COORDINATES], SIZE - diagonals[i][COORDINATES_Y]};
					if (diagonals[i][SYMBOLS] == symbol * (SIZE - 1)) {
						return result;
					}
				}
			}
		}

		return result;
	}

	/**
	 * Chooses a random position in the grid.
	 *
	 * @return The coordinates of a random position in the grid
	 */
	private int[] getRandomPosition() {
		int x = RANDOM.nextInt(SIZE);
		int y = RANDOM.nextInt(SIZE);
		return new int[]{x, y};
	}

	/**
	 * Chooses a random corner in the grid.
	 *
	 * @return The coordinates of a random corner in the grid
	 */
	private int[] getRandomCorner() {
		int[] corners = new int[]{0, SIZE - 1};
		int x = corners[RANDOM.nextInt(corners.length)];
		int y = corners[RANDOM.nextInt(corners.length)];
		return new int[]{x, y};
	}

	/**
	 * Chooses a random playing symbol from the available set.
	 *
	 * @return The value of the random playing symbol
	 */
	private int getRandomSymbol() {
		int symbol = SYMBOL_VALUES[RANDOM.nextInt(SYMBOL_SIZE)].getValue();
		return symbol;
	}

	/**
	 * Prints the contents of the grid.
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("\n");
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				builder.append(j == 0 ? " " : "| ").append(Symbol.getTextFromValue(grid[i][j])).append(" ");
			}

			builder.append("\n");
			if (i < SIZE - 1) {
				builder.append("---+---+---\n");
			}
		}

		builder.append("\n");
		return builder.toString();
	}

	/**
	 * For testing purposes.
	 */
	public static void main(String[] args) {
		TicTacToe tictactoe = new TicTacToe();
		tictactoe.play();
	}
}
