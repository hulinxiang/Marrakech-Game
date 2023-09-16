package comp1110.ass2;

/**
 * All aspects of the player including its coins, and rugs, and current state.
 */
public class Player {
    // Starting number of coins
    static int START_COINS = 30;
    // Starting number of rugs;
    static int START_RUGS = 15;
    //Number of coins
    int coins;
    //Number of rugs
    int rugs;
    //The coordinates of the player's rugs on the board.
    String colour;
    //String denoting the colour of the player.
    IntPair[] rugCords;
    //The state of the player. 1 is in the game;-1 is out of game;0 is out of rugs;
    int playerState;

    /*
    /**
     * Constructor of Player
     * @param coins
     * @param rugs

    Player(String colour, int coins, int rugs, int playerState) {
        this.colour = "";
        this.coins = START_COINS;
        this.rugs = START_RUGS;
        this.playerState = 1;
    }
    */

    /**
     * Decodes player string
     */
    public void decodePlayerString(String playerString){
        //Setting player colour
        switch (playerString.substring(1,2)){ //2nd character is colour;
            case "c":
                this.colour = "cyan";
                break;
            case "y":
                this.colour = "yellow";
            case "r":
                this.colour = "red";
            case "p":
                this.colour = "purple";
        }

        this.coins = Integer.parseInt(playerString.substring(3,5)); //3rd, 4th, 5th characters denote coins.
        this.rugs = Integer.parseInt(playerString.substring(5,7)); //6th and 7th characters denote number of rugs.

        //Setting the player state:
        if(playerString.substring(7).equals("i"))
        {
            this.playerState = 1; //1 Means player is in the game.
        }
        else
        {
            this.playerState = -1; //1 Means player is out of the game.
        }


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
