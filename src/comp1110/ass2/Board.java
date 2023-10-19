package comp1110.ass2;

/**
 * This class defines the shape (width and height) of the game board.
 */
public class Board {

    //The fixed height of board
    public final static int BOARD_HEIGHT = 7;
    //The fixed width of board
    public final static int BOARD_WIDTH = 7;
    //Array of tiles that the board is made up of.
    public Tile[][] tiles;


    /**
     * Sets up the initial state of the board.
     */
    public static void boardSetUp() {
        //[ROWS][COLUMNS]
        Tile[][] tiles = new Tile[BOARD_HEIGHT][BOARD_WIDTH];
    }

    /**
     * Check that the board has been set up.
     */
    public boolean checkBoard() {
        return true;
    }


    /**
     * This method is to check if an Intpair is within the board.
     *
     * @param intPair
     * @return boolean that denotes if this Intpair is within the board.
     */
    public boolean withInBoard(IntPair intPair) {
        int x = intPair.getX();
        int y = intPair.getY();
        return x >= 0 && x <= BOARD_WIDTH && y >= 0 && y <= BOARD_HEIGHT;
    }
}
