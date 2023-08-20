package comp1110.ass2;


public class Merchant {
    //Position of the merchant
    IntPair merchantPosition;
    //Direction of the merchant
    Direction direction;

    /**
     * Constructor of merchant
     *
     * @param center is the center position of the board
     */
    Merchant(IntPair center) {
        this.merchantPosition = center;
    }

    /*
    Sets the initial direction of the merchant at the start of the game.
     */
    public void firstDirection(Direction d) {
        this.direction = d;

    }

    /*
    Rotates the merchant depending on the player input.
    d: The current direction the merchant is facing.
    rotateValue: -1 = left rotation, 0 = no rotation, 1 = right rotation.
     */
    public void Rotate(Direction d, int rotateValue) {

    }

    /*
    Moves the required number of steps as indicated by the die, in the specific direction.
     */
    public void Move(Direction d, int steps) {

    }

    /*
     Checks that the rotation is no more than 90 degrees. Returns false if illegal rotation, returns true if legal.
      */
    public static boolean checkRotation(Direction d) {
        return true;
    }


}