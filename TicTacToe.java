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
        EMPTY(0, " "),
        CIRCLE(1, "\u25CB"),
        CROSS(2, "\u2A2F");

        /** Integer value of the playing symbol. */
        private int value;

        /** Character representation of the playing symbol */
        private String character;

        /** 
         * Creates an instance of {@link Symbol}.
         *
         * @param value Integer value
         * @param character Character representation
         */
        private Symbol(int value, String character) {
            this.value = value;
            this.character = character;
        }

        /**
         * Gets the integer value of the playing symbol.
         * 
         * @return The integer value
         */
        public int getValue() {
            return value;
        }

        /**
         * Gets the character representation of the playing symbol.
         * 
         * @return The character representation
         */
        public String getCharacter() {
            return character;
        }

        /**
         * Gets the character representation of the playing symbol given its integer value.
         * 
         * @param value Integer value
         * @return The character representation
         */
        public static String getCharacterFromValue(int value) {
            for (Symbol symbol : Symbol.values()) {
                if (symbol.value == value) {
                    return symbol.getCharacter();
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

    /** Minimum number of plays before one of the players can win. */
    private static final int MINIMUM_PLAYS = 5;

    /**
     * Indexes used to navigate through the matrixes with information regarding rows, columns and
     * diagonals.
     */
    private static final int FILLED = 0;
    private static final int SYMBOLS = 1;
    private static final int COORDINATES = 2;
    private static final int COORDINATES_Y = 3;

    /** Random number generator. */
    private static final Random RANDOM = new Random();

    /** Standard input reader. */
    private static final Scanner SCANNER = new Scanner(System.in);

    /**
     * List of playing symbols used for random assignment to players.
     * The empty position value is ignored.
     */
    private static final Symbol[] SYMBOL_VALUES = Symbol.values();
    private static final int SYMBOL_SIZE = SYMBOL_VALUES.length - 1;

    /** Indexes used to navigate through the matrix with the system messages. */
    private static final int INDEX_MACHINE = 0;
    private static final int INDEX_HUMAN = 1;
    private static final int INDEX_MOVE = 0;
    private static final int INDEX_WIN = 1;

    /** Messages for both computer and human players. */
    private static final String[][] MESSAGES = new String[][]{
        {"Computer says: (%d,%d)", "Game over! Bow before your new machine overlord!"},
        {"Puny human played: (%d,%d)", "Game over! Humans rule!"}
    };

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
     * Creates an instance of {@link TicTacToe}.
     */
    public TicTacToe() {
        grid = new int[SIZE][SIZE];
        rows = new int[SIZE][SIZE];
        columns = new int[SIZE][SIZE];
        diagonals = new int[DIAGONALS][SIZE + 1];
        plays = 0;
    }

    /**
     * Game engine that alternates between computer and human generated moves.
     */
    public void play() {
        int symbolMachine = getRandomSymbol();
        int symbolHuman = (symbolMachine == Symbol.CIRCLE.getValue()
            ? Symbol.CROSS.getValue()
            : Symbol.CIRCLE.getValue());

        boolean machineTurn = RANDOM.nextBoolean();
        boolean gameInProgress = true;
        int symbol;

        while (gameInProgress) {
            symbol = (machineTurn ? symbolMachine : symbolHuman);
            gameInProgress = move(symbol, machineTurn);
            machineTurn = !machineTurn;
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
     * @return The coordinates of the position that a human player considers to be the best possible
     *         move
     */
    private int[] readMove() {
        int x = -1;
        int y = -1;

        while (true) {
            System.out.print("Please insert a valid pair of coordinates: ");

            try {
                if (SCANNER.hasNextInt()) {
                    x = SCANNER.nextInt() - 1;
                } else {
                    SCANNER.next();
                }

                if (SCANNER.hasNextInt()) {
                    y = SCANNER.nextInt() - 1;
                } else {
                    SCANNER.next();
                }
            } catch (InputMismatchException e) {
                System.err.println("\nERROR: An incorrect number was read.\n");
            } catch (NoSuchElementException e) {
                System.err.println("\nERROR: There is no more input to read.\n");
            } catch (IllegalStateException e) {
                System.err.println("\nERROR: Cannot read any more input.\n");
            } finally {
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
    private int[] chooseMove(int symbol) {

        int[] position = null;

        // Case 1:
        // If the grid is still empty or if only one move has been played by the opponent, randomly
        // choose one of the corners and play
        if (isGridEmpty() || plays == 1) {
            do {
                position = getRandomCorner();
            }
            while (!isPositionEmpty(position[0], position[1]));
        }

        // Case 2:
        // Check if there is a near win situation and take it, thus defeating the puny human and
        // claiming bragging righs over the Human race!
        // Alas, the puny human might be in a near win situation. If so, proceeds to cutting him off
        // by filling the last position.
        if (position == null) {
            position = checkForNearWin(symbol);
        }

        // Case 3:
        // Look for free corners in order to increase the chances of setting up two simultaneous
        // near win situations.
        // The puny human will only be able to prevent one of them and will inevitably lose the game.
        if (position == null) {
            do {
                position = getRandomCorner();
            }
            while (!isPositionEmpty(position[0], position[1]));
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
     * Performs a move in the grid.
     * The chosen move and the updated grid are displayed.
     *
     * @param symbol Playing symbol of the current player
     * @param machineTurn Flag that indicates if it's the machine's turn to play
     * @return True if the move doesn't end the game; false otherwise
     */
    private boolean move(int symbol, boolean machineTurn) {
        int[] position = (machineTurn ? chooseMove(symbol) : readMove());
        fillPosition(position[0], position[1], symbol);
        int index = (machineTurn ? INDEX_MACHINE : INDEX_HUMAN);
        System.out.println(String.format(MESSAGES[index][INDEX_MOVE], ++position[0], ++position[1]));
        System.out.println(this);
        if (isGameOver()) {
            if (!isGridFull()) {
                System.out.println(MESSAGES[index][INDEX_WIN]);
            }

            return false;
        }

        return true;
    }

    /**
     * Checks if the game is over.
     *
     * @return True if a winning configuration has been reached or if the grid is full;
     *         false otherwise
     */
    private boolean isGameOver() {
        if (plays < MINIMUM_PLAYS) {
            return false;
        }

        if (isGridFull()) {
            return true;
        }

        for (int i = 0; i < SIZE; i++) {
            if (isFullWin(rows, i)
                || isFullWin(columns, i)
                || (i < SIZE - 1 && isFullWin(diagonals, i))) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if there is a full win configuration in the given structure, which might be related to
     * rows, columns or diagonals.
     *
     * @param structure Structure 
     * @param index Index of the structure entry
     * @return True if there is a new win configuration; false otherwise
     */
    private boolean isFullWin(int[][] structure, int index) {
        return structure[index][FILLED] == SIZE && structure[index][SYMBOLS] % SIZE == 0;
    }

    /**
     * Checks if there is a near win configuration in the given structure, which might be related to
     * rows, columns or diagonals.
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
     * Searches the grid to see if the opponent is in a near win situation, with two values already
     * filled in a row, column or diagonal. If so, determines the coordinates of the remaining
     * position that needs to be filled in order to prevent the opponent to win.
     *
     * @param symbol Playing symbol
     * @return The coordinates of the position that needs to be filled in order to prevent the
     *         opponent to win
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
            if (i < DIAGONALS) {
                if (isNearWin(diagonals, i)) {
                    result = new int[]{
                        SIZE - diagonals[i][COORDINATES],
                        SIZE - diagonals[i][COORDINATES_Y]
                    };

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
        return SYMBOL_VALUES[RANDOM.nextInt(SYMBOL_SIZE) + 1].getValue();
    }

    /**
     * Prints the contents of the grid.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("\n");
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                builder.append(j == 0 ? " " : "| ")
                    .append(Symbol.getCharacterFromValue(grid[i][j]))
                    .append(" ");
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
