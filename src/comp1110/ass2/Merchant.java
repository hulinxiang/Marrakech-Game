package comp1110.ass2;



public class Merchant {
    //Position of the merchant
    IntPair merchantPosition;
    //Direction of the merchant
    Direction direction;
    public IntPair getMerchantPosition() {
        return merchantPosition;
    }

    public void setMerchantPosition(IntPair merchantPosition) {
        this.merchantPosition = merchantPosition;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * Constructor of merchant
     *
     * @param center is the center position of the board
     */
    /*Merchant(IntPair center) {
        this.merchantPosition = center;
    }
    */

    /**
     * Decodes the merchant string
     */
    public void decodeAsamString(String asamString){
        int x = Integer.parseInt(String.valueOf(asamString.charAt(0)));
        int y = Integer.parseInt(String.valueOf(asamString.charAt(1)));
        //Check if the Asam is out of game board
        if (x>6 || y>6 || x < 0 || y < 0){
            throw new RuntimeException("Invalid Asam String");
        }
        this.merchantPosition = new IntPair(x, y);

        switch (asamString.charAt(2)){
            case 'N':
                direction = Direction.NORTH;
                break;
            case 'E':
                direction = Direction.EAST;
                break;
            case 'S':
                direction = Direction.SOUTH;
                break;
            case 'W':
                direction = Direction.WEST;
                break;
            default:
                throw new RuntimeException("Invalid Asam String");
        }
    }

    /**
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
