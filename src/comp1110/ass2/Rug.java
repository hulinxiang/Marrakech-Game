package comp1110.ass2;

/**
 * Behaviour of the rug on the board
 */
public class Rug {
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
        return false;
    } //Which rug is at the topmost level?


}
