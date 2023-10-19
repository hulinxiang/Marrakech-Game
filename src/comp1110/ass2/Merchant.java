package comp1110.ass2;


public class Merchant {
    public static int x = 0;
    public static int y = 0;
    //Position of the merchant
    public IntPair merchantPosition;
    //Direction of the merchant
    public Direction direction;

    public Merchant() {

    }

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
    public void decodeAsamString(String asamString) {
        if(String.valueOf(asamString.charAt(0)).equals("A")){
            asamString = asamString.substring(1);
        }
        this.x = Integer.parseInt(String.valueOf(asamString.charAt(0)));
        this.y = Integer.parseInt(String.valueOf(asamString.charAt(1)));
        //Check if the Asam is out of game board
        if (x > 6 || y > 6 || x < 0 || y < 0) {
            throw new RuntimeException("Invalid Asam String");
        }
        this.merchantPosition = new IntPair(x, y);

        switch (asamString.charAt(2)) {
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
     * Sets the initial direction of the merchant at the start of the game.
     */
    public void firstDirection(Direction d) {
        this.direction = d;

    }


    /**
     * Moves the required number of steps as indicated by the die, in the specific direction.
     */
    public Merchant(int startX, int startY) {
        x = startX;
        y = startY;

    }

    public void Move(Direction d, int steps) {
        switch (d) {
            case NORTH:
                y -= steps;
                break;
            case SOUTH:
                y += steps;
                break;
            case EAST:
                x -= steps;
                break;
            case WEST:
                x += steps;
                break;
            default:
        }

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     Checks that the rotation is no more than 90 degrees. Returns false if illegal rotation, returns true if legal.
     */

    /**
     * @param currentDirection
     * @param intendedDirection
     * @return
     */
    public static boolean checkRotation(Direction currentDirection, Direction intendedDirection) {
        int differences = Math.abs(currentDirection.ordinal() - intendedDirection.ordinal());
        return differences == 1 || differences == 3;
    }

    public String getString() {
        StringBuilder sb = new StringBuilder();
        sb.append("A");
        sb.append(getX());
        sb.append(getY());
        sb.append(direction.name().substring(0,1));
        return sb.toString();
    }

    public static char rotate(char assamDirection, int rotation) {
        //turn to left
        if (rotation == -90||rotation==270) {
            switch (assamDirection) {
                case 'N':
                    return 'W';
                case 'S':
                    return 'E';
                case 'W':
                    return 'S';
                case 'E':
                    return 'N';
                default:
            }
        }
        //turn to right
        if (rotation == 90) {
            switch (assamDirection) {
                case 'N':
                    return 'E';
                case 'S':
                    return 'W';
                case 'W':
                    return 'N';
                case 'E':
                    return 'S';
                default:
            }
        }
        //Remain same
        if (rotation == 0) {
            return assamDirection;
        }
        //Return 'i' if the requested rotation is illegal
        return 'i';
    }
}

