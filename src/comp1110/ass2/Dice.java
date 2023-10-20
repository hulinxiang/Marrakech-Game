package comp1110.ass2;


/**
 * @author groupwork
 * Defines the behaviour of the dice. REDUNDANT CLASS.
 */
public class Dice {
    static int[] diceValues = {1, 2, 2, 3, 3, 4};
    //Array of integers denoting dice set-up (six sided with 1 one and four, and 2 twos and threes).

    /**
     * Rolls a value according to the dice set up to decide who plays first and the movement of the merchant.
     *
     * @return rolledInt is the result of the rotation
     */
    public static int Roll() {
        int randomNum = (int) Math.floor(Math.random() * 6); //Index between 0 and 5.
        int rolledNum = diceValues[randomNum];
        return rolledNum;
    }
}
