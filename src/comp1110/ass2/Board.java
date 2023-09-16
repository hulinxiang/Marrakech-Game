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
    Tile[][] tiles;
    //Merchant on the board.
    Merchant merchant;
    //Denotes the location of the mosaic circles on the board.
    IntPair[] mosaic;

    /**
     * Sets up the initial state of the board.
     */
    public static void boardSetUp() {
        //[ROWS][COLUMNS]
        Tile[][] tiles = new Tile[BOARD_HEIGHT][BOARD_WIDTH];
    }

    /**
     * Decodes the board string to set the state of the board as specified by the game string.
     */
    public void decodeBoardString(String boardString){
        //[ROWS][COLUMNS]
        Tile[][] tiles = new Tile[BOARD_HEIGHT][BOARD_WIDTH];

        int counter = 0;
        //Double for loop, looping through each column and row
        for(int j=0; j<BOARD_WIDTH; j++){ //LOOPING THROUGH EACH COLUMN
            for(int k=0; k<BOARD_HEIGHT; k++){ //LOOPING THROUGH EACH ROW
                IntPair tilePos = new IntPair(j,k);
                tiles[j][k] = new Tile(tilePos);
                tiles[j][k].owner.decodeColour(boardString.substring(counter,counter+1)); //setting the owner of the tile
                counter +=1;

            }
        }

    }

    /**
     * This method is to convert an Intpair to the tile on this Intpair;
     *
     * @param position the position of a tile
     * @return tile with the position
     */
    public Tile getTileFromPos(IntPair position) {
        return new Tile(new IntPair(1, 1));
    }

    /**
     * This method is to check if an Intpair is within the board.
     *
     * @param intPair
     * @return boolean that denotes if this Intpair is within the board.
     */
    public boolean withInBoard(IntPair intPair) {
        return true;
    }
}
