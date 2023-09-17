package comp1110.ass2;

/**
 * Tile coordinates and their state on the board.
 */
public class Tile {
    //Denote the position of each tile
    private final IntPair tilePosition;
    //Denote the owner of the tile
    Player owner;
    //Denote the state of the tile. 0 means empty and 1 means occupied

    Rug id;
    int state;

    String colour;


    /**
     * Constructor of tile
     * Generate initial tile
     */
    public Tile(IntPair tilePosition) {
        this.tilePosition = tilePosition;
        this.owner = null;
        this.colour = null;
    }

    /**
     * Sets the owner whose rug is on top of the tile.
     *
     * @param player who is the owner of this tile
     */
    public void occupiedBy(Player player) {
        this.owner = player;
    }

    /**
     *Checks the current owner of the tile.
     */
    public Player checkOccupation() {
        return null;
    }
}
