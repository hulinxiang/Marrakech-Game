package comp1110.ass2;

import com.sun.source.tree.IfTree;

import java.util.*;

public class Marrakech {

    //Players in game
    public Player[] players;
    public Merchant asam;

    public Board board;

    public int numberPlayers;
    //How many players in the game.

    //Record of the rugs that have been placed (according to their id).
    ArrayList<String> placedRugs = new ArrayList<>();
    public final int PLAYER_STRING_LENGTH = 8;
    String boardString;
    String assamString;

    ArrayList<Direction> arrayDirections = new ArrayList<Direction>((Arrays.asList(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST)));

    Tile[][] tiles = new Tile[Board.BOARD_HEIGHT][Board.BOARD_WIDTH];

    /**
     * Getter method of players
     *
     * @return players
     */
    public Player[] getPlayers() {
        return players;
    }

    /**
     * Setter method of players
     *
     * @param players
     */

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    /**
     * Getter method of boardString
     *
     * @return boardString
     */
    public String getBoardString() {
        return boardString;
    }

    /**
     * Setter method of boardString
     *
     * @param boardString
     */
    public void setBoardString(String boardString) {
        this.boardString = boardString;
    }

    /**
     * Getter method of assamString
     *
     * @return assamString
     */
    public String getAssamString() {
        return assamString;
    }

    /**
     * Setter method of assamString
     *
     * @param assamString
     */
    public void setAssamString(String assamString) {
        this.assamString = assamString;
    }


    /**
     * Returns an integer value representing how method rugs are on the board.
     */
    public int placedNumber() {
        return placedRugs.size();
    }

    /**
     * Generates new instance of Marrakech as per the string input by decoding the string.
     */

    public Marrakech(String gameString) {
        int indexAsam = gameString.indexOf("A");//Position of Assam string
        assamString = decodeAssamString(gameString);
        boardString = decodeBoardString(gameString);

        //CREATING OBJECT PLAYERS:
        numberPlayers = indexAsam / PLAYER_STRING_LENGTH; //Number of players in the game.
        //Create each player
        players = new Player[numberPlayers];
        for (int i = 0; i < numberPlayers; i++) {
            players[i] = new Player();
            players[i] = Player.decodePlayerString(gameString.substring((i * PLAYER_STRING_LENGTH), (i * PLAYER_STRING_LENGTH) + PLAYER_STRING_LENGTH));
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

    public void decodeMarrakech(String gameString) {
        for (int i = 0; i < numberPlayers; i++) {
            this.players[i] = Player.decodePlayerString(gameString.substring((i * PLAYER_STRING_LENGTH), (i * PLAYER_STRING_LENGTH) + PLAYER_STRING_LENGTH));
        }

        String asamString = decodeAssamString(gameString);
        this.asam.decodeAsamString(asamString);

        String boardString = decodeBoardString(gameString);
        this.board.tiles = makeTiles(boardString);

    }

    /**
     * Method for decode assam string from game string
     *
     * @param gameString
     * @return assam string
     */
    public static String decodeAssamString(String gameString) {
        int indexAsam = gameString.indexOf("A");//Position of Assam string
        int indexBoard = gameString.indexOf("B");
        if (indexAsam == -1) {
            throw new RuntimeException("Invalid Game String Format");
        }
        return gameString.substring(indexAsam + 1, indexBoard);
    }

    /**
     * Method for decode boardString from game string
     *
     * @param gameString
     * @return board string
     */
    public static String decodeBoardString(String gameString) {
        int indexAsam = gameString.indexOf("A");//Position of Assam string
        return gameString.substring(indexAsam + 5);
    }


    /**
     * Method for returning the x position of assam
     *
     * @param assamString
     * @return x position
     */
    public static int getAssamPositionX(String assamString) {
        return Integer.parseInt(assamString.substring(0, 1));
    }

    /**
     * Method for returning the y position of assam
     *
     * @param assamString
     * @return y position
     */
    public static int getAssamPositionY(String assamString) {
        return Integer.parseInt(assamString.substring(1, 2));
    }

    /**
     * A method for decoding rug string
     *
     * @param rugString
     * @return IntPair of two squares of the rug
     */
    public static IntPair[] decodeRugString(String rugString) {
        IntPair[] ans = new IntPair[2];
        ans[0] = new IntPair(Integer.parseInt(rugString.substring(3, 4)),
                Integer.parseInt(rugString.substring(4, 5)));
        ans[1] = new IntPair(Integer.parseInt(rugString.substring(5, 6)),
                Integer.parseInt(rugString.substring(6, 7)));
        return ans;
    }

    /**
     * Assigns the state of each tile on the board based on their ID and colour.
     */
    public Tile[][] makeTiles(String boardString) {

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
                    tiles[j][k].id = "00";
                } else {
                    tiles[j][k].state = 1; //not empty
                    tiles[j][k].owner = decodeOwner(boardString.substring(counter, counter + 1));

                    //SETTING ID
                    //Check if rug already recorded in array (since can cover two tiles).
                    String rugID = boardString.substring(counter + 1, counter + 3);
                    tiles[j][k].id = rugID;

                    if (placedRugs.size() == 0) {
                        placedRugs.add(rugID);
                    } else {
                        boolean booNew = true; //Whether rug has already been added to placedRugs.
                        for (String placedRug : placedRugs) {
                            if (placedRug == rugID) {
                                booNew = false;
                                break;
                            }
                        }

                        if (booNew) { //Rug has not been added to placedRugs
                            placedRugs.add(rugID);
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
     * returns a Player.
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
        int expectedLength = 7;
        //Check if the rug string length is 7
        if (rug.length() != expectedLength) {
            return false;
        }
        int firstSquareX = getFirstSquareX(rug);
        int firstSquareY = getFirstSquareY(rug);
        int secondSquareX = getSecondSquareX(rug);
        int secondSquareY = getSecondSquareY(rug);
        //Check if two squares of the rug is connected
        if (firstSquareX != secondSquareX && firstSquareY != secondSquareY) {
            return false;
        }
        //Check if it is out of bound
        if (firstSquareX > 6 || firstSquareY > 6 || secondSquareX > 6 || secondSquareY > 6) {
            return false;
        }
        //possibleColour is an ArrayList that contains possible colour
        ArrayList<String> possibleColour = new ArrayList<>();
        possibleColour.add("c");
        possibleColour.add("y");
        possibleColour.add("p");
        possibleColour.add("r");
        String colour = rug.substring(0, 1);
        //Check if the colour of the rug is valid
        if (!possibleColour.contains(colour)) {
            return false;
        }
        //Check if have the same id and colour for the first square
        String str = rug.substring(0, 3);
        ArrayList<String> splitedRugStrings = splitBoardString(gameString);
        return !splitedRugStrings.contains(str);
    }

    /**
     * A method for abstract the x coordinate of the first tile in the rug string
     *
     * @param rug a rug string
     * @return the x position of first tile in rug string
     */
    public static int getFirstSquareX(String rug) {
        return Integer.parseInt(rug.substring(3, 4));
    }

    /**
     * A method for abstract the y coordinate of the first tile in the rug string
     *
     * @param rug a rug string
     * @return the y position of first tile in rug string
     */
    public static int getFirstSquareY(String rug) {
        return Integer.parseInt(rug.substring(4, 5));
    }

    /**
     * A method for abstract the x coordinate of the second tile in the rug string
     *
     * @param rug a rug string
     * @return the x position of second tile in rug string
     */
    public static int getSecondSquareX(String rug) {
        return Integer.parseInt(rug.substring(5, 6));
    }

    /**
     * A method for abstract the y coordinate of the second tile in the rug string
     *
     * @param rug a rug string
     * @return the y position of second tile in rug string
     */
    public static int getSecondSquareY(String rug) {
        return Integer.parseInt(rug.substring(6));
    }

    /**
     * A method for getting the tile coordinates according to the index in the board string
     *
     * @param index index in the boarding string
     * @return the coordinates on the board
     */
    public static String getCoordinateFromIndex(int index) {
        int x = index % 7;
        int y = index / 7;
        return "" + x + y;
    }


    /**
     * It is a method that can help to split gameString into Abbreviated Rug Strings
     * for operations in isRugValid method
     *
     * @param gameString the String needs to be split
     * @return an ArrayList that contains these Abbreviated Rug Strings
     */

    public static ArrayList<String> splitBoardString(String gameString) {
        int boardIndex = gameString.indexOf("B");
        String boardString = gameString.substring(boardIndex + 1);
        ArrayList<String> ans = new ArrayList<>();
        //Split the gameString three by three
        //we can obtain the Abbreviated Rug Strings of each tile
        for (int i = 0; i < boardString.length(); i += 3) {
            ans.add(boardString.substring(i, i + 3));
        }
        return ans;
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
     * Counts the number of players
     */
    public int countPlayers() {
        return players.length;
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
        for (int player = 0; player < 4; player++) {
            int startIndex = player * 8;
            if (currentGame.charAt(startIndex + 7) == 'i' && (currentGame.charAt(startIndex + 5) != '0'
                    || currentGame.charAt(startIndex + 6) != '0')) {
                return false;
            }
        }
        return true;
        // FIXME: Task 8
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
        char assamDirection = getAssamDirection(currentAssam);
        char newAssamDirection = Merchant.rotate(assamDirection, rotation);
        // FIXME: Task 9
        //'i' means "invalid". In the rotate method, return 'i' if the requested rotation is illegal
        if (newAssamDirection == 'i') {
            return currentAssam;
        }
        return getNewAssamString(currentAssam, newAssamDirection);
    }

    /**
     * A method for getting assamDirection according to currentAssam
     *
     * @param currentAssam A String representing Assam's current state
     * @return 'W' if it faces to West
     * 'E' if it faces to East
     * 'N' if it faces to North
     * 'S' if it faces to South
     */
    public static char getAssamDirection(String currentAssam) {
        return currentAssam.charAt(3);
    }

    /**
     * A method for getting newAssamDirection if it rotates to a new direction
     *
     * @param currentAssam
     * @param newAssamDirection
     * @return
     */
    public static String getNewAssamString(String currentAssam, char newAssamDirection) {
        return currentAssam.substring(0, 3) + newAssamDirection;
    }

    public Direction possibleDirections(Direction currentDirection, int whichWay) {
        Direction possibleDirection;
        int currentIndex = arrayDirections.indexOf(currentDirection);
        //Making sure that index is not out of bounds.
        if (currentIndex == 0 && whichWay == -1) { //If at start wrap around to end.
            possibleDirection = arrayDirections.get(arrayDirections.size() + whichWay);
        } else if (currentIndex == arrayDirections.size() - 1 && whichWay == 1) { //if at end wrap around to start.
            possibleDirection = arrayDirections.get(0);
        } else {
            possibleDirection = arrayDirections.get(currentIndex + whichWay);
        }

        return possibleDirection;
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
        if (!isRugValid(gameState, rug)) {
            return false;
        }
        // FIXME: Task 10
        int assamX = getAssamPositionX(decodeAssamString(gameState));
        int assamY = getAssamPositionY(decodeAssamString(gameState));
        IntPair[] rugPosition = decodeRugString(rug);
        //Check situation 1. A new rug must have one edge adjacent to Assam
        if (!ifConnectedAssam(rugPosition[0].getX(), rugPosition[0].getY(), rugPosition[1].getX(), rugPosition[1].getY(), assamX, assamY)) {
            return false;
        }
        //Check situation 2.  A new rug must not completely cover another rug.
        //Get the position of information about the original rug.
        //If the original rug strings share the same color and id, return false.(Except that they are both "n00")
        List<String> splitedRugStrings = splitBoardString(gameState);
        int indexOfFirst = getPositionFromCoordinates(rugPosition[0].getX(), rugPosition[0].getY());
        int indexOfSecond = getPositionFromCoordinates(rugPosition[1].getX(), rugPosition[1].getY());
        if (splitedRugStrings.get(indexOfFirst).equals(splitedRugStrings.get(indexOfSecond))) {
            return "n00".equals(splitedRugStrings.get(indexOfFirst));
        }
        return true;
    }

    /**
     * A method for convert the coordinates of a tile into the index in the BoardString
     *
     * @param x coordinate x of the tile
     * @param y coordinate y of the tile
     * @return the index
     */
    public static int getPositionFromCoordinates(int x, int y) {
        return 7 * x + y;
    }

    /**
     * Method for checking if a square of the rug is connected to assam
     *
     * @param x1     the x position of the first square
     * @param y1     the y position of the first square
     * @param x2     the x position of the second square
     * @param y2     the y position of the second square
     * @param assamX the x position of assam
     * @param assamY the y position of assam
     * @return true if connected; else, return false;
     */
    public static boolean ifConnectedAssam(int x1, int y1, int x2, int y2, int assamX, int assamY) {
        //first square cannot overlap the asam
        if (x1 == assamX && y1 == assamY) {
            return false;
        }
        //second square cannot overlap the asam
        if (x2 == assamX && y2 == assamY) {
            return false;
        }
        //check if connected
        else if (x1 != assamX && x2 != assamX && y1 != assamY && y2 != assamY) {
            return false;
        }
        return true;
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
        // Parse the gameString to get the board state, Assam's position, etc.
        Marrakech marrakech = new Marrakech(gameString);
        Tile[][] tiles = marrakech.board.tiles;
        IntPair assamPosition = marrakech.asam.getMerchantPosition();

        // Get the color of the rug Assam landed on
        String landedColor = tiles[assamPosition.getX()][assamPosition.getY()].getColour();

        // If Assam landed on a square without a rug, return 0
        if (landedColor == null || landedColor.isEmpty()) {
            return 0;
        }

        // Use DFS to count the number of connected squares of the same color
        boolean[][] visited = new boolean[tiles.length][tiles[0].length];
        int paymentAmount = dfs(assamPosition.getX(), assamPosition.getY(), tiles, visited, landedColor);

        return paymentAmount;
    }

    private static int dfs(int x, int y, Tile[][] tiles, boolean[][] visited, String color) {
        if (x < 0 || x >= tiles.length || y < 0 || y >= tiles[0].length || visited[x][y] || !tiles[x][y].getColour().equals(color)) {
            return 0;
        }

        visited[x][y] = true;

        int count = 1; // Count the current square
        count += dfs(x + 1, y, tiles, visited, color); // Right
        count += dfs(x - 1, y, tiles, visited, color); // Left
        count += dfs(x, y + 1, tiles, visited, color); // Down
        count += dfs(x, y - 1, tiles, visited, color); // Up

        return count;
        // FIXME: Task 11

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
        //If the game is not over
        if (!ifGameOver(gameState)) {
            return 'n';
        } else {
            return calWinner(gameState);
        }
    }

    /**
     * Method for checking if the game is over
     *
     * @param gameState String for denoting the game state
     * @return true, if the game is over; false, if the game is not over
     */
    public static boolean ifGameOver(String gameState) {
        Marrakech marrakech = new Marrakech(gameState);
        int num = marrakech.numberPlayers;
        //The array to denote the state of every player
        //1: in the game;0: out of game
        Integer[] playerState = new Integer[num];
        Arrays.fill(playerState, 1);
        //Condition 1: check game state of every player
        for (int i = 0; i < num; i++) {
            //playerState==-1 denotes that he is out of game
            if (marrakech.players[i].playerState == -1) {
                playerState[i] = 0;
            }
        }
        //Condition 2: Check if he is out of rugs
        for (int i = 0; i < num; i++) {
            //rugs denote the rugs that one player have
            if (marrakech.players[i].rugs == 0) {
                playerState[i] = 0;
            }
        }
        //Check the number of players out of game
        int count = 0;
        for (Integer i : playerState) {
            if (i == 0) {
                count++;
            }
        }
        if (count == num) {
            return true;
        }
        return false;
    }

    /**
     * A method to calculate the game winner according to the game string if the game is over
     *
     * @param gameState the string to denote the game state
     * @return winner's colour;If tied, return 't';
     */
    public static char calWinner(String gameState) {
        Marrakech marrakech = new Marrakech(gameState);
        //A hashmap to denote the play's colour and his coins
        HashMap<Character, Integer> dirhams = new HashMap<>();
        for (Player p : marrakech.players) {
            dirhams.put(p.colour.charAt(0), p.coins);
        }
        //A hashmap to denote the play's colour and his squares
        HashMap<Character, Integer> squares = calSquares(splitBoardString(gameState));
        //A hashmap to denote the play's colour and his property(squares+dirhams)
        HashMap<Character, Integer> scores = new HashMap<>();
        for (Map.Entry<Character, Integer> entry : squares.entrySet()) {
            scores.put(entry.getKey(), dirhams.get(entry.getKey()) + squares.get(entry.getKey()));
        }
        //If a player is out of game, the score he has will not take into account.
        for (int i = 0; i < marrakech.numberPlayers; i++) {
            //If he is out of game
            if (marrakech.players[i].playerState == -1) {
                scores.remove(marrakech.players[i].colour.charAt(0));
                squares.remove(marrakech.players[i].colour.charAt(0));
                dirhams.remove(marrakech.players[i].colour.charAt(0));
            }
        }
        //The variable to denote if there is a tied situation that all scores are same.
        boolean tied = false;
        //The score is the highest scores that one player has
        int score = Integer.MIN_VALUE;
        for (int i = 0; i < scores.values().size(); i++) {
            if (getValueFromHashSet(scores, i) > score) {
                score = getValueFromHashSet(scores, i);
                tied = false;
            } else if (getValueFromHashSet(scores, i) == score) {
                tied = true;
            }
        }
        if (tied) {
            StringBuilder palyersWithSameScores = new StringBuilder();
            //Get the colour the players with same total scores
            for (int i = 0; i < scores.size(); i++) {
                if (getValueFromHashSet(scores, i) == score) {
                    palyersWithSameScores.append(getKeyFromHashSet(scores, i));
                }
            }
            //Check if players with same scores have different dirhams
            boolean differentDirhams = false;
            //Make the default dirhamsNum be the number of dirhams that the first player among the players with same total scores has
            int dirhamNum = dirhams.get(palyersWithSameScores.charAt(0));
            for (int i = 1; i < palyersWithSameScores.length(); i++) {
                if (dirhams.get(palyersWithSameScores.charAt(i)) > dirhamNum) {
                    differentDirhams = false;
                    dirhamNum = squares.get(palyersWithSameScores.charAt(i));
                } else if (dirhams.get(palyersWithSameScores.charAt(i)) == dirhamNum) {
                    differentDirhams = true;
                }
            }
            if (!differentDirhams) {
                return getKeyByValue(squares, dirhamNum);
            } else {
                return 't';
            }
        }
        return getKeyByValue(scores, score);

    }

    /**
     * A method for calculating the dirhams on the board
     *
     * @param splitedBoardString ArrayList with the collection of all abbreviated rugs strings
     * @return HashMap, keys are colour of players and integers are number of their squares on the board
     */
    public static HashMap<Character, Integer> calSquares(ArrayList<String> splitedBoardString) {
        //var is used to collect all chars that denotes colours
        StringBuilder var = new StringBuilder();
        //Character is the colour of one player
        //Integer is the number of squares the one has
        HashMap<Character, Integer> ans = new HashMap<>();
        for (String s : splitedBoardString) {
            var.append(s.charAt(0));
        }
        for (int i = 0; i < var.length(); i++) {
            char colour = var.charAt(i);
            if (colour == 'n') {
                continue;
            }
            //If this is a new colour
            if (!ans.containsKey(colour)) {
                ans.put(colour, 1);
            } else {
                //Update HashMap
                ans.replace(colour, ans.get(colour) + 1);
            }
        }
        return ans;

    }

    /**
     * A method for returning a key according to value in hashmap
     *
     * @param map   a hash map
     * @param value a value in hashmap
     * @return key
     */
    public static Character getKeyByValue(HashMap<Character, Integer> map, Integer value) {
        for (int i = 0; i < map.size(); i++) {
            if (getValueFromHashSet(map, i).equals(value)) {
                return getKeyFromHashSet(map, i);
            }
        }
        throw new RuntimeException("The score is invalid");
    }

    /**
     * A method for getting ith value in the value set
     *
     * @param map the hashMap
     * @param i   the index of the value we want
     * @return ith value in the value set of map
     */
    public static Integer getValueFromHashSet(HashMap<Character, Integer> map, int i) {
        Integer val = 0;
        ArrayList<Integer> values = new ArrayList<>(map.values());
        val = values.get(i);
        return val;
    }

    /**
     * A method for getting ith value in the key set
     *
     * @param map the hashMap
     * @param i   the index of the key we want
     * @return ith value in the key set of map
     */
    public static Character getKeyFromHashSet(HashMap<Character, Integer> map, int i) {
        Character key = '0';
        ArrayList<Character> keys = new ArrayList<>(map.keySet());
        key = keys.get(i);
        return key;
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
        // Assam's current position and direction
        int x = Character.getNumericValue(currentAssam.charAt(1));
        int y = Character.getNumericValue(currentAssam.charAt(2));
        char direction = currentAssam.charAt(3);

        //move Assam based on the die result and direction
        for (int i = 0; i < dieResult; i++) {
            switch (direction) {
                case 'N':
                    y--;
                    if (y < 0 && x % 2 != 0) {
                        y = 0;
                        x--;
                        direction = 'S'; //Adjust direction based on the tracks
                    } else if (y < 0 && x != 6) {
                        y = 0;
                        x++;
                        direction = 'S';
                    } else if (y < 0) {
                        y = 0;
                        x--;
                        x += 1;
                        direction = 'W';

                    }
                    break;
                case 'E':
                    x++;
                    if (x > 6 && y % 2 != 0) {
                        x = 6;
                        y++;
                        direction = 'W';
                    } else if (x > 6 && y != 0) {
                        x = 6;
                        y--;
                        direction = 'W';
                    } else if (x > 6) {
                        x = 6;
                        y++;
                        y -= 1;
                        direction = 'S';

                    }
                    break;
                case 'S':
                    y++;
                    if (y > 6 && x % 2 != 0) {
                        y = 6;
                        x++;
                        direction = 'N';
                    } else if (y > 6 && x != 0) {
                        y = 6;
                        x--;
                        direction = 'N';

                    } else if (y > 6) {
                        y = 6;
                        x++;
                        x -= 1;
                        direction = 'E';

                    }
                    break;
                case 'W':
                    x--;
                    if (x < 0 && y % 2 != 0) {
                        x = 0;
                        y--;
                        direction = 'E';
                    } else if (x < 0 && y != 6) {
                        x = 0;
                        y++;
                        direction = 'E';

                    } else if (x < 0) {
                        x = 0;
                        y--;
                        y += 1;
                        direction = 'N';

                    }
                    break;
                default:
            }

        }
        // FIXME: Task 13
        return "A" + x + y + direction;
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
        if (!isRugValid(currentGame, rug)) {
            return currentGame;
        }
        if (!isPlacementValid(currentGame, rug)) {
            return currentGame;
        }
        String playerStrng = decodedPlayerString(currentGame);
        //decodeAsamString returns a string without 'A'. So add it at head
        String AssamString = 'A' + decodeAssamString(currentGame);
        ArrayList<String> splitedBoadString = splitBoardString(currentGame);
        String rugColourId = rugColourId(rug);
        int indexOfFirstTile = getFirstTileIndex(rug);
        int indexOfSecondTile = getSecondTileIndex(rug);
        //Update PlayerString
        playerStrng = updatePlayerString(playerStrng, rugColour(rug));
        //Update BoardString
        splitedBoadString.set(indexOfFirstTile, rugColourId);
        splitedBoadString.set(indexOfSecondTile, rugColourId);
        //splitBoardString returns a ArrayList without 'B'. So add it at head
        StringBuilder newBoardString = new StringBuilder("B");
        for (String s : splitedBoadString) {
            newBoardString.append(s);
        }
        //Return new gameString. Because Assam String is unmoved, assamString is same as before
        return playerStrng + AssamString + newBoardString;
    }


    /**
     * A method for updating player string
     *
     * @param playerString string of all player's strings
     * @param player       the colour of the playerString that needs updates
     * @return a new string of all playerStrings
     */
    public static String updatePlayerString(String playerString, char player) {
        int indexOfColour = playerString.indexOf(player);
        int rugsRemained = Integer.parseInt(playerString.substring(indexOfColour + 4, indexOfColour + 6));
        //Because the player has placed a rug, the number of remained rugs -1;
        rugsRemained--;
        if (rugsRemained < 10) {
            return playerString.substring(0, indexOfColour + 4) + "0" + rugsRemained + playerString.substring(indexOfColour + 6);
        }
        return playerString.substring(0, indexOfColour + 4) + rugsRemained + playerString.substring(indexOfColour + 6);

    }


    /**
     * A method returns rug's colour and Id
     *
     * @param rug A string representation of rug state.
     * @return rug's colour and id
     */
    public static String rugColourId(String rug) {
        return rug.substring(0, 3);
    }

    /**
     * A method returns rug's colour
     *
     * @param rug A string representation of rug state.
     * @return rug's colour
     */
    public static char rugColour(String rug) {
        return rug.charAt(0);
    }


    /**
     * Get all playerStrings from the game string
     *
     * @return playerString
     */
    public static String decodedPlayerString(String gameString) {
        int indexA = gameString.indexOf('A');
        return gameString.substring(0, indexA);
    }

    /**
     * A method for getting the first tile position in the boardString using rug string
     *
     * @param rug string of a rug
     * @return index of the first rug in the boardString
     */
    public static int getFirstTileIndex(String rug) {
        //x coordinates
        int x = Integer.parseInt(rug.substring(3, 4));
        //y coordinates
        int y = Integer.parseInt(rug.substring(4, 5));
        return getPositionFromCoordinates(x, y);
    }

    /**
     * A method for getting the second tile position in the boardString using rug string
     *
     * @param rug
     * @return index of the second rug in the boardString
     */
    public static int getSecondTileIndex(String rug) {
        //x coordinates
        int x = Integer.parseInt(rug.substring(5, 6));
        //y coordinates
        int y = Integer.parseInt(rug.substring(6, 7));
        return getPositionFromCoordinates(x, y);
    }

    public String generateGameString() {
        String gameString = "";
        //Create the player string
        for (int i = 0; i < numberPlayers; i++) {
            String zeroes = "0"; //Number of 0s depend on number of digits.

            String colour = players[i].getColour().substring(0, 1).toLowerCase(Locale.ROOT);

            String dirhams = Integer.toString(players[i].getCoins());
            dirhams = zeroes.repeat(3 - dirhams.length()) + dirhams; //Update through concatenation

            String rugs = Integer.toString(players[i].getRugs());
            rugs = zeroes.repeat(2 - rugs.length()) + rugs;

            String state;
            int stateInt = players[i].getPlayerState();
            if (stateInt == 1) {
                state = "i";
            } else {
                state = "o";
            }

            //Concatonate all together to form player string
            gameString += "P" + colour + dirhams + rugs + state;

        }

        //Creating board string
        String boardString = "";
        for (int j = 0; j < Board.BOARD_WIDTH; j++) { //LOOPING THROUGH EACH COLUMN
            for (int k = 0; k < Board.BOARD_HEIGHT; k++) { //LOOPING THROUGH EACH ROW
                String tileColour;
                if (this.board.tiles[j][k].colour == null) {
                    tileColour = "n";
                } else {
                    tileColour = this.board.tiles[j][k].getColour().substring(0, 1).toLowerCase();

                }
                String tileOwner = this.board.tiles[j][k].id;
                boardString += tileColour + tileOwner;

            }
        }
        //Add asam string and board string
        gameString += this.asam.getString() + "B" + boardString;

        //Add board string
        return gameString;
    }

    public String generateRugString(String colour, IntPair firstCord, IntPair secCord) {
        String rugString = "";

        //Add colour code
        rugString += colour;

        //Generate potential id
        int recentID;
        if (placedRugs.size() > 0) {
            recentID = Integer.parseInt(placedRugs.get(placedRugs.size() - 1)); //Get id of most recent added rug
        } else {
            recentID = 0; //Get id of most recent added rug
        }
        String potentialID = Integer.toString(recentID + 1);
        potentialID = "0".repeat(2 - potentialID.length()) + potentialID;

        rugString += potentialID + firstCord.x + firstCord.y + secCord.x + secCord.y;


        return rugString;

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
        //String gameString = "Pr00803iPy01305iPc01510oA04NBc01c02n00c03c04y05p06c07c08y09c10c10y11p12p12c13y14c15c15y16c17c17r18y19c20c20y21c22c22r23r23c25c25y26c26c27c27r28r28c29y30c31c31c32y33c34c34y35c36";


        System.out.println("The colour: " + Game.players[0].colour);
        System.out.println("Number of coins: " + Game.players[0].coins);
        System.out.println("Number of rugs: " + Game.players[0].rugs);
        System.out.println("Player state: " + Game.players[0].playerState);

        System.out.println("Merchant x: " + Game.asam.merchantPosition.x);
        System.out.println("Merchant y: " + Game.asam.merchantPosition.y);
        System.out.println("Merchant direction: " + Game.asam.direction);

        System.out.println(Game.board.tiles[0][0]);


    }


}
