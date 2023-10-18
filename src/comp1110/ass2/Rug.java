package comp1110.ass2;

/**
 * Behaviour of the rug on the board
 */
public class Rug { //Maybe useful... which rug has occupied tile?
    //If placed, the rug has an id (integer denoting when the rug was placed).
    int rugID;
    //Denote the in position of rug
    /*
    private final IntPair[] rugPosition;

    /*public Rug(IntPair[] rugPosition) {
        this.rugPosition = rugPosition;
    }

    /**
     * Stores the coordinates of where the rug is placed.
     */
    public void rugPlacement() {

    }

    /**
     * Checks that the rug shares a border with the merchant
     *
     * @return boolean
     */
    public boolean checkShareBoarder() {
        return true;
    }

    /**
     * Checks that the rug is not placed outside the bounds of the board.
     *
     * @return boolean
     */
    public boolean ifPlaceCorrectly() {
        return true;
    }

    /**
     * Checks that the rug does not fully overlap another player's rug.
     *
     * @return boolean
     */
    public boolean ifOverlap() {
        return true;
    } //Which rug is at the topmost level?


}
