package comp1110.ass2;

import java.util.*;

public class Marrakech {

    //Players in game.
    public Player[] players;
    public Merchant asam;

    public Board board;

    public int numberPlayers;
    //How many players in the game.

    //Record of the rugs that have been placed (according to their id).
    ArrayList<Rug> placedRugs = new ArrayList<>();
    public final int PLAYER_STRING_LENGTH=8;
    String boardString;
    String assamString;

    /**
     * Getter method of players
     * @return players
     */
    public Player[] getPlayers() {
        return players;
    }

    /**
     * Setter method of players
     * @param players
     */

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    /**
     * Getter method of boardString
     * @return boardString
     */
    public String getBoardString() {
        return boardString;
    }

    /**
     * Setter method of boardString
     * @param boardString
     */
    public void setBoardString(String boardString) {
        this.boardString = boardString;
    }

    /**
     * Getter method of assamString
     * @return assamString
     */
    public String getAssamString() {
        return assamString;
    }

    /**
     * Setter method of assamString
     * @param assamString
     */
    public void setAssamString(String assamString) {
        this.assamString = assamString;
    }


    /**
     * Generates new instance of Marrakech as per the string input by decoding the string.
     */

    public Marrakech(String gameString) {
        int indexAsam = gameString.indexOf("A");//Position of Assam string
        int indexBoard =  gameString.indexOf("B");

        //Check the correct format of Assam string
        if (indexAsam == -1) {
            throw new RuntimeException("Invalid Game String Format");
        }

        assamString = gameString.substring(indexAsam + 1, indexBoard);
        boardString = gameString.substring(indexAsam + 5);

        //CREATING OBJECT PLAYERS:
        numberPlayers = indexAsam / PLAYER_STRING_LENGTH; //Number of players in the game.
        //Create each player
        players = new Player[numberPlayers];
        for (int i = 0; i < numberPlayers; i++) {
            players[i] = new Player();
            players[i].decodePlayerString(gameString.substring((i * PLAYER_STRING_LENGTH), (i * PLAYER_STRING_LENGTH) + PLAYER_STRING_LENGTH));
        }

        //CREATING ASAM
        asam = new Merchant();
        //Asam string denotes position (x, y) and direction.
        asam.decodeAsamString(assamString);

        //CREATING BOARD
        board = new Board();
        //Board string is from the end of the asam string to the end of game string (exclude the B when passing to method).
        //Assign owner to board using players[]

        board.tiles = makeTiles(boardString);


    }

    /**
     * Assigns the state of each tile on the board based on their ID and colour.
     */
    public Tile[][] makeTiles(String boardString) {
        Tile[][] tiles = new Tile[Board.BOARD_HEIGHT][Board.BOARD_WIDTH];

        int counter = 0;
        //Double for loop, looping through each column and row
        for (int j = 0; j < Board.BOARD_WIDTH; j++) { //LOOPING THROUGH EACH COLUMN
            for (int k = 0; k < Board.BOARD_HEIGHT; k++) { //LOOPING THROUGH EACH ROW
                IntPair tilePos = new IntPair(j, k);
                tiles[j][k] = new Tile(tilePos);

                String tileColour = boardString.substring(counter, counter + 1);
                String colour = "";
                switch (tileColour) { //2nd character is colour;
                    case "c":
                        colour = "CYAN";
                        break;
                    case "y":
                        colour = "YELLOW";
                        break;
                    case "r":
                        colour = "RED";
                        break;
                    case "p":
                        colour = "PURPLE";
                        break;
                    default:
                        colour = "NULL";
                }
                tiles[j][k].colour = colour;


                if (boardString.substring(counter, counter + 3).equals("n00")) { //EMPTY TILE
                    tiles[j][k].state = 0; //empty
                    tiles[j][k].owner = null; //empty therefore no owner
                } else {
                    tiles[j][k].state = 1; //not empty
                    tiles[j][k].owner = decodeOwner(boardString.substring(counter, counter + 1));

                    //SETTING ID
                    //Check if rug already recorded in array (since can cover two tiles).
                    int idFromStr = Integer.parseInt(boardString.substring(counter + 1, counter + 3));
                    if (placedRugs.size() == 0) {
                        Rug newRug = new Rug();
                        newRug.rugID = idFromStr;
                        placedRugs.add(newRug);
                    } else {
                        boolean booNew = true; //Whether rug has already been added to placedRugs.
                        for (Rug placedRug : placedRugs) {
                            if (placedRug.rugID == idFromStr) {
                                booNew = false;
                                break;
                            }
                        }

                        if (booNew) { //Rug has not been added to placedRugs
                            Rug newRug = new Rug();
                            newRug.rugID = idFromStr;
                            placedRugs.add(newRug);
                        }
                    }

                }
                counter += 3;

            }
        }
        return tiles;
    }

    /**
     * Assigns owner of the tile based on their colour.
     */

    public Player decodeOwner(String ownerColour) {
        Player theOwner;
        for (int i = 0; i < numberPlayers; i++) {
            if (ownerColour.equals(players[i].colour.substring(0, 1))) { //First letter of colour compared
                theOwner = players[i];
                return theOwner;
            }
        }
        return null;
    }

    /**
     * Determine whether a rug String is valid.
     * For this method, you need to determine whether the rug String is valid, but do not need to determine whether it
     * can be placed on the board (you will determine that in Task 10 ). A rug is valid if and only if all the following
     * conditions apply:
     * - The String is 7 characters long
     * - The first character in the String corresponds to the colour character of a player present in the game
     * - The next two characters represent a 2-digit ID number
     * - The next 4 characters represent coordinates that are on the board
     * - The combination of that ID number and colour is unique
     * To clarify this last point, if a rug has the same ID as a rug on the board, but a different colour to that rug,
     * then it may still be valid. Obviously multiple rugs are allowed to have the same colour as well so long as they
     * do not share an ID. So, if we already have the rug c013343 on the board, then we can have the following rugs
     * - c023343 (Shares the colour but not the ID)
     * - y013343 (Shares the ID but not the colour)
     * But you cannot have c014445, because this has the same colour and ID as a rug on the board already.
     *
     * @param gameString A String representing the current state of the game as per the README
     * @param rug        A String representing the rug you are checking
     * @return true if the rug is valid, and false otherwise.
     */
    public static boolean isRugValid(String gameString, String rug) {
        final int STRING_LENGTH = 7;
        //The board is 7x7, which means it  has 7 columns and 7 rows.
        //Because the coordination starts with 0. So max value is 6.
        final int MAX_ROW = 6;
        final int MAX_COLUMN = 6;
        if (rug.length() != STRING_LENGTH) {
            return false;
        }

        // Extract different parts of the rug string
        char color = rug.charAt(0);
        String id = rug.substring(1, 3);
        String coords = rug.substring(3, 7);
        //Location of squares in a rug
        //The row of first square
        int firstSquareRow = coords.charAt(0) - '0';
        //The column of first square
        int firstSquareColumn = coords.charAt(1) - '0';
        //The row of second square
        int secondSquareRow = coords.charAt(2) - '0';
        //The column of second square
        int secondSquareColumn = coords.charAt(3) - '0';
        // Check if these two square are connected. If not, it is not valid
        if (firstSquareRow != secondSquareRow) {
            if (firstSquareColumn != secondSquareColumn) {
                return false;
            }
        }
        //Check if they are larger than valid value
        if (firstSquareColumn > MAX_COLUMN || secondSquareColumn > MAX_COLUMN || firstSquareRow > MAX_ROW || secondSquareRow > MAX_ROW) {
            return false;
        }

        //Check if they are smaller than 0
        if (firstSquareColumn < 0 || secondSquareColumn < 0 || firstSquareRow < 0 || secondSquareRow < 0) {
            return false;
        }

        // Check if color is one of 'c, y, r, p'
        if ("cyrp".indexOf(color) == -1) {
            return false;
        }

        // Create a set to store unique rug identifiers (color + ID)
        Set<String> uniqueRugs = new HashSet<>();

        // Parse existing rugs from gameString
        // Assuming that each rug in gameString is 7 characters long, without any delimiters
        for (int i = 0; i <= gameString.length() - 7; i += 7) {
            String existingRug = gameString.substring(i, i + 7);
            // Color + ID
            String existingRugId = existingRug.substring(0, 3);
            uniqueRugs.add(existingRugId);
        }

        // Check for uniqueness of the new rug's color + ID combination
        String newRugId = color + id;
        if (uniqueRugs.contains(newRugId)) {
            return false;
        }

        // Assuming you will check if coords are valid in a different function (e.g., on-board checks)
        // FIXME: Task 4
        return true;
    }


    /**
     * Roll the special Marrakech die and return the result.
     * Note that the die in Marrakech is not a regular 6-sided die, since there
     * are no faces that show 5 or 6, and instead 2 faces that show 2 and 3. That
     * is, of the 6 faces
     * - One shows 1
     * - Two show 2
     * - Two show 3
     * - One shows 4
     * As such, in order to get full marks for this task, you will need to implement
     * a die where the distribution of results from 1 to 4 is not even, with a 2 or 3
     * being twice as likely to be returned as a 1 or 4.
     *
     * @return The result of the roll of the die meeting the criteria above
     */


    public static int rollDie() {
        //Originally had dice class (now redundant).
        // To simplify code, it is easier to implement all parts of the die's behaviour in this method....
        // instead of creating a separate class.

        int[] diceValues = {1, 2, 2, 3, 3, 4};
        //Array of integers denoting dice set-up (six sided with 1 one and four, and 2 twos and threes).

        Random random = new Random();
        int randomNum = random.nextInt(6); //Index between 0 and 5 inclusive.
        int rolledNum = diceValues[randomNum];
        return rolledNum;
    }

    /**
     * Determine whether a game of Marrakech is over
     * Recall from the README that a game of Marrakech is over if a Player is about to enter the rotation phase of their
     * turn, but no longer has any rugs. Note that we do not encode in the game state String whose turn it is, so you
     * will have to think about how to use the information we do encode to determine whether a game is over or not.
     *
     * @param currentGame A String representation of the current state of the game.
     * @return true if the game is over, or false otherwise.
     */
    public static boolean isGameOver(String currentGame) {
        // FIXME: Task 8
        return false;
    }

    /**
     * Implement Assam's rotation.
     * Recall that Assam may only be rotated left or right, or left alone -- he cannot be rotated a full 180 degrees.
     * For example, if he is currently facing North (towards the top of the board), then he could be rotated to face
     * East or West, but not South. Assam can also only be rotated in 90 degree increments.
     * If the requested rotation is illegal, you should return Assam's current state unchanged.
     *
     * @param currentAssam A String representing Assam's current state
     * @param rotation     The requested rotation, in degrees. This degree reading is relative to the direction Assam
     *                     is currently facing, so a value of 0 for this argument will keep Assam facing in his
     *                     current orientation, 90 would be turning him to the right, etc.
     * @return A String representing Assam's state after the rotation, or the input currentAssam if the requested
     * rotation is illegal.
     */
    public static String rotateAssam(String currentAssam, int rotation) {
        // FIXME: Task 9
        return "";
    }

    /**
     * Determine whether a potential new placement is valid (i.e that it describes a legal way to place a rug).
     * There are a number of rules which apply to potential new placements, which are detailed in the README but to
     * reiterate here:
     * 1. A new rug must have one edge adjacent to Assam (not counting diagonals)
     * 2. A new rug must not completely cover another rug. It is legal to partially cover an already placed rug, but
     * the new rug must not cover the entirety of another rug that's already on the board.
     *
     * @param gameState A game string representing the current state of the game
     * @param rug       A rug string representing the candidate rug which you must check the validity of.
     * @return true if the placement is valid, and false otherwise.
     */
    public static boolean isPlacementValid(String gameState, String rug) {
        // FIXME: Task 10
        return false;
    }

    /**
     * Determine the amount of payment required should another player land on a square.
     * For this method, you may assume that Assam has just landed on the square he is currently placed on, and that
     * the player who last moved Assam is not the player who owns the rug landed on (if there is a rug on his current
     * square). Recall that the payment owed to the owner of the rug is equal to the number of connected squares showing
     * on the board that are of that colour. Similarly to the placement rules, two squares are only connected if they
     * share an entire edge -- diagonals do not count.
     *
     * @param gameString A String representation of the current state of the game.
     * @return The amount of payment due, as an integer.
     */
    public static int getPaymentAmount(String gameString) {
        // FIXME: Task 11
        return -1;
    }

    /**
     * Determine the winner of a game of Marrakech.
     * For this task, you will be provided with a game state string and have to return a char representing the colour
     * of the winner of the game. So for example if the cyan player is the winner, then you return 'c', if the red
     * player is the winner return 'r', etc...
     * If the game is not yet over, then you should return 'n'.
     * If the game is over, but is a tie, then you should return 't'.
     * Recall that a player's total score is the sum of their number of dirhams and the number of squares showing on the
     * board that are of their colour, and that a player who is out of the game cannot win. If multiple players have the
     * same total score, the player with the largest number of dirhams wins. If multiple players have the same total
     * score and number of dirhams, then the game is a tie.
     *
     * @param gameState A String representation of the current state of the game
     * @return A char representing the winner of the game as described above.
     */
    public static char getWinner(String gameState) {
        // FIXME: Task 12
        return '\0';
    }

    /**
     * Implement Assam's movement.
     * Assam moves a number of squares equal to the die result, provided to you by the argument dieResult. Assam moves
     * in the direction he is currently facing. If part of Assam's movement results in him leaving the board, he moves
     * according to the tracks diagrammed in the assignment README, which should be studied carefully before attempting
     * this task. For this task, you are not required to do any checking that the die result is sensible, nor whether
     * the current Assam string is sensible either -- you may assume that both of these are valid.
     *
     * @param currentAssam A string representation of Assam's current state.
     * @param dieResult    The result of the die, which determines the number of squares Assam will move.
     * @return A String representing Assam's state after the movement.
     */
    public static String moveAssam(String currentAssam, int dieResult) {
        // FIXME: Task 13
        return "";
    }

    /**
     * Place a rug on the board
     * This method can be assumed to be called after Assam has been rotated and moved, i.e in the placement phase of
     * a turn. A rug may only be placed if it meets the conditions listed in the isPlacementValid task. If the rug
     * placement is valid, then you should return a new game string representing the board after the placement has
     * been completed. If the placement is invalid, then you should return the existing game unchanged.
     *
     * @param currentGame A String representation of the current state of the game.
     * @param rug         A String representation of the rug that is to be placed.
     * @return A new game string representing the game following the successful placement of this rug if it is valid,
     * or the input currentGame unchanged otherwise.
     */
    public static String makePlacement(String currentGame, String rug) {
        // FIXME: Task 14
        return "";
    }

    public static void main(String[] args) {
        // Test the isRugValid method
        System.out.println(isRugValid("c013343y023343", "c023444"));  // Should return true
        System.out.println(isRugValid("c013343y023343", "y013343"));  // Should return false (same ID and color)
        System.out.println(isRugValid("c013343y023343", "c013444"));  // Should return false (same ID and color)
        System.out.println(isRugValid("c013343y023343", "y023444"));  // Should return true

        //Test the rollDie method
        int[] counter = {0, 0, 0, 0};  //1s counter, 2s counter, 3s counter, 4s counter
        for (int i = 0; i < 1000; i++) {
            int num = rollDie();
            counter[num - 1] += 1;
        }
        //Print statements showing distribution (need higher percentage of 2s and 3s).
        System.out.println("% of 1s: " + counter[0] / 10);
        System.out.println("% of 2s: " + counter[1] / 10);
        System.out.println("% of 3s: " + counter[2] / 10);
        System.out.println("% of 4s: " + counter[3] / 10);

        //TEST FOR STRING DECODING

        Marrakech Game = new Marrakech("Pr00803iPy01305iPc01510oA04NBc01c02n00c03c04y05p06c07c08y09c10c10y11p12p12c13y14c15c15y16c17c17r18y19c20c20y21c22c22r23r23c25c25y26c26c27c27r28r28c29y30c31c31c32y33c34c34y35c36");

        System.out.println("The colour: " + Game.players[0].colour);
        System.out.println("Number of coins: " + Game.players[0].coins);
        System.out.println("Number of rugs: " + Game.players[0].rugs);
        System.out.println("Player state: " + Game.players[0].playerState);

        System.out.println("Merchant x: " + Game.asam.merchantPosition.x);
        System.out.println("Merchant y: " + Game.asam.merchantPosition.y);
        System.out.println("Merchant direction: " + Game.asam.direction);


        //TESTING THE RUG ID DECODER
        for (int i = 0; i < Game.placedRugs.size(); i++) {
            System.out.println(Game.board.tiles[i][i].colour);
        }

    }


}
