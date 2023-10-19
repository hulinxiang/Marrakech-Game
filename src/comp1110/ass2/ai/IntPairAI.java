package comp1110.ass2.ai;

import comp1110.ass2.IntPair;

public class IntPairAI {

    public double x;
    public double y;


    public IntPairAI(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * A method for getting a new intPairAI with x=(x1+x2)/2, y=(y1+y2)/2
     *
     * @param first  fist intPairAI
     * @param second second intPairAI
     * @return a new intPairAI with average x and y
     */
    public static IntPairAI average(IntPairAI first, IntPairAI second) {
        return new IntPairAI((first.x + second.x) / 2, (first.y + second.y) / 2);
    }


    /**
     * A method for getting a intPairAI according to the coordinates of first tile
     *
     * @param rug a string of rug state
     * @return intPairAI
     */
    public static IntPairAI getFirstIntpair(String rug) {
        double x = Double.parseDouble(rug.substring(3, 4));
        double y = Double.parseDouble(rug.substring(4, 5));
        return new IntPairAI(x, y);
    }


    /**
     * A method for getting a intPairAI according to the coordinates of second tile
     *
     * @param rug a string of rug state
     * @return intPairAI
     */
    public static IntPairAI getSecondIntpair(String rug) {
        double x = Double.parseDouble(rug.substring(5, 6));
        double y = Double.parseDouble(rug.substring(6, 7));
        return new IntPairAI(x, y);
    }

    /**
     * A method for calculating the loss between average position of the rug and a tile on the board
     *
     * @param averageTile the position of average position of rug
     * @param tile    the position of a tile on the board
     * @return the loss between them
     */
    public static Double calLoss(IntPairAI averageTile, IntPair tile) {
        Double loss = Math.pow(averageTile.x - tile.getX(), 2) + Math.pow(averageTile.y - tile.getY(), 2);
        return loss;
    }


}
