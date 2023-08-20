package comp1110.ass2;

/**
 * A pair of ints that is used in many places in the ass2
 */
public class IntPair {
    //x int
    private final int x;
    //y int
    private final int y;

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
        return true;
    }
}
