package comp1110.ass2;

/**
 * All aspects of the player including its coins, and rugs, and current state.
 */
public class Player {
    // Starting number of coins
    final int START_COINS = 30;
    // Starting number of rugs;
    final int START_RUGS = 15;
    //Number of coins
    int coins;
    //Number of rugs
    int rugs;
    //The coordinates of the player's rugs on the board.
    IntPair[] rugCords;
    //The state of the player. 1 is in the game;-1 is out of game;0 is out of rugs;
    int playerState;

    /**
     * Constructor of Player
     * @param coins
     * @param rugs
     */
    Player(int coins, int rugs) {
        this.coins = START_COINS;
        this.rugs = START_RUGS;
    }

    /**
     * Player pays other player money, updates this.coins field.
     */
    public void makePayment() {

    }

    /**
     * Player receives money from another players.
     */
    public void getPayment() {

    }

    /**
     * Counts how many rugs of the player is on the board.
     */
    public int rugCount() {
        return 0;
    }

    /**
     * Places the rug of the player on the board.
     */
    public void placeRug() {

    }

    /**
     * Checks that the player has enough money.
     *
     * @return boolean denoting true if the player has enough, false if not.
     */
    public boolean moneyCheck() {
        return true;
    }

    /**
     * Checks if the player has rugs left to place on the bord.
     *
     * @return boolean denoting true if the player still has rugs to place, false if not.
     */
    public boolean rugCheck() {
        return true;
    }

    /**
     * Counts how many rugs of the player are connected.
     *
     * @param rug is the rug we want to check the connections
     * @return int is the number of connected tile of this rug
     */
    public int Connected(Rug rug) {
        return 0;
    }

    /**
     * Calculates the player's score
     *
     * @return int is the score of the player
     */
    public int Score() {
        return 0;
    }

}
