package comp1110.ass2;

/**
 * A pair of ints that is used in many places in the ass2
 */
public class IntPair {
    //x int
    public int x;
    //y int
    public int y;

    /**
     * Constructor to create an instance of IntPair
     *
     * @param x
     * @param y
     */
    public IntPair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * getter method for x
     *
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * getter method for y
     *
     * @return y
     */
    public int getY() {
        return y;
    }

    /**
     * setter method for x
     *
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * setter method for y
     *
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Creates a new IntPair object that is the element-wise sum of the old and the other
     *
     * @param other Intpair to add to this Intpair
     * @return a new IntPair which is the element-wise addition of this and other
     */
    public IntPair add(IntPair other) {
        IntPair res = new IntPair(this.x + other.getX(), this.y + other.getY());
        return res;
    }

    /**
     * Override the equals method to check if two Intpairs are equal
     *
     * @param o
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntPair intPair = (IntPair) o;
        return x == intPair.x && y == intPair.y;
    }
}
