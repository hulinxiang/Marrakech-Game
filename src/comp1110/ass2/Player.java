package comp1110.ass2;

import javafx.scene.paint.Color;

/**
 * All aspects of the player including its coins, and rugs, and current state.
 */
public class Player {

    static int START_COINS = 30; // Starting number of coins

    static int START_RUGS = 15; // Starting number of rugs;

    public int dirhams; //Number of coins

    public int rugs;  //Number of rugs

    public String colour;  //String denoting the colour of the player.

    IntPair[] rugCords; //The coordinates of the player's rugs on the board.

    public int playerState; //The state of the player. 1 is in the game;-1 is out of game;0 is out of rugs;

    /**
     * Getter method of getPlayerState
     *
     * @return playerState
     */
    public int getPlayerState() {
        return playerState;
    }

    /**
     * Setter method of getPlayerState
     *
     * @param playerState
     */
    public void setPlayerState(int playerState) {
        this.playerState = playerState;
    }

    /**
     * Getter method of coins
     *
     * @return coins
     */
    public int getCoins() {
        return dirhams;
    }

    /**
     * Setter method of coins
     *
     * @param dirhams Integer value specifying the number of coins.
     */
    public void setDirhams(int dirhams) {
        this.dirhams = dirhams;
    }

    /**
     * Getter method of rugs
     *
     * @return rugs
     */
    public int getRugs() {
        return rugs;
    }

    /**
     * Setter method of rugs
     *
     * @param rugs
     */
    public void setRugs(int rugs) {
        this.rugs = rugs;
    }

    /**
     * Getter method of colour
     *
     * @return colour
     */
    public String getColour() {
        return colour;
    }

    /**
     * Setter method of colour
     *
     * @param colour
     */
    public void setColour(String colour) {
        this.colour = colour;
    }

    /**
     * Decodes player string
     */
    public static Player decodePlayerString(String playerString) {
        //Setting player colour
        Player player = new Player();
        player.colour = decodeColour(playerString.substring(1, 2));

        player.dirhams = Integer.parseInt(playerString.substring(2, 5)); //3rd, 4th, 5th characters denote coins.
        player.rugs = Integer.parseInt(playerString.substring(5, 7)); //6th and 7th characters denote number of rugs.

        //Setting the player state:
        if (playerString.substring(7).equals("i")) {
            player.playerState = 1; //1 Means player is in the game.
        } else {
            player.playerState = -1; //1 Means player is out of the game.
        }
        return player;

    }

    /**
     * Decodes the character seen in the rug and player parts of the game string.
     * @param colour String of a single character - first letter of colour.
     * @return Full string with the colour name.
     */

    public static String decodeColour(String colour) {
        //Setting player colour
        switch (colour) { //2nd character is colour;
            case "c":
                colour = "cyan";
                break;
            case "y":
                colour = "yellow";
                break;
            case "r":
                colour = "red";
                break;
            case "p":
                colour = "purple";
                break;
            default:
                throw new RuntimeException("Invalid colour");
        }
        return colour;

    }

    /**
     * Convert a String to a Color class
     *
     * @param colour String specifying the colour of the player
     * @return Color Returns colour class corresponding to the string.
     */
    public static Color getColorFromString(String colour) {
        if ("YELLOW".equalsIgnoreCase(colour)) {
            return Color.YELLOW;
        } else if ("RED".equalsIgnoreCase(colour)) {
            return Color.RED;
        } else if ("PURPLE".equalsIgnoreCase(colour)) {
            return Color.PURPLE;
        } else if ("CYAN".equalsIgnoreCase(colour)) {
            return Color.CYAN;
        } else {
            return Color.WHITE;
        }
    }


}
