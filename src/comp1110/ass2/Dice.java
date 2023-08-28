package comp1110.ass2;

/**
 * Defines the behaviour of the dice. REDUNDANT CLASS.
 */
public class Dice {
    static int[] diceValues = {1, 2, 2, 3, 3, 4};
    //Array of integers denoting dice set-up (six sided with 1 one and four, and 2 twos and threes).

    /**
     * Rolls a value according to the dice set up to decide who plays first and the movement of the merchant.
     * @return rolledInt is the result of the rotation
     */
    public static int Roll() {
        int randomNum = (int) Math.floor(Math.random() *6)+1; //Index between 1 and 6 inclusive.
        int rolledNum = diceValues[randomNum];
        return rolledNum;
    }
}
