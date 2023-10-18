package comp1110.ass2;

import javafx.scene.paint.Color;

/**
 * Tile coordinates and their state on the board.
 */
public class Tile {
    //Denote the position of each tile
    public final IntPair tilePosition;
    //Denote the owner of the tile
    Player owner;
    //Denote the state of the tile. 0 means empty and 1 means occupied

    String id;
    int state;

    String colour;


    /**
     * @return Colour
     */
    public String getColour() {
        return this.colour;
    }

    /**
     * Set the colour of one tile
     *
     * @param colour
     */
    public void setColour(String colour) {
        this.colour = colour;
    }

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
     * Checks the current owner of the tile.
     */
    public Player checkOccupation() {
        return null;
    }


    /**
     * A method for converting a string to a Color
     *
     * @param colour, of type String
     * @return Color, of type Color
     */
    public static Color getColorFromString(String colour) {
        if ("YELLOW".equalsIgnoreCase(colour)) {
            return Color.YELLOW;
        } else if ("RED".equalsIgnoreCase(colour)) {
            return Color.RED;
        } else if ("PURPLE".equalsIgnoreCase(colour)) {
            return Color.PURPLE;
        } else if ("CYAN".equalsIgnoreCase(colour)) {
            return Color.CYAN;
        } else {
            return Color.WHITE;
        }
    }
}
