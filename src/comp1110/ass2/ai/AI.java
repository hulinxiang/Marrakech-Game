package comp1110.ass2.ai;

import comp1110.ass2.*;


import java.util.ArrayList;
import java.util.Random;


public class AI extends Player {

    public int diceResult;


    public static int[] rotateNum = {90, -90, 270};


    public String id;


    public AI() {
        Random random = new Random();
        //Colour of AI will always be cyan.
        this.colour = "c";
        //Initialize the id for computer rug. Every round the id+1.
        //For example, first time the computer placed a rug, the rug id is "00".
        //Second time it placed a rug, the id would be "01".
        this.id = "00";
    }

    /**
     * Roll dice
     *
     * @return an integer denotes how many steps the computer needs to move
     */
    public int rolling() {
        this.diceResult = Dice.Roll();
        return this.diceResult;
    }


    /**
     * Rotate the assam
     *
     * @param assamString A string representation of assam state.
     * @return A String representing Assam's state after the rotation.
     */
    public String rotateAssamAI(String assamString) {
        char assamDirection = assamString.charAt(3);
        int random = (int) (rotateNum.length * Math.random());
        //update assam direction by rotation
        assamDirection = Merchant.rotate(assamDirection, rotateNum[random]);
        // Replace the last character of assamString with assamDirection
        assamString = assamString.substring(0, assamString.length() - 1) + assamDirection;

        return assamString;
    }


    /**
     * Move the assam
     *
     * @param gameString A string representation of game state.
     * @param dieResult  The result of the die, which determines the number of squares Assam will move.
     * @return A String representing Assam's state after the movement.
     */
    public String moveAssamAI(String gameString, int dieResult) {
        //Because the result of currentAssam has no "A" at head, add "A" at head position
        String currentAssam = "A" + Marrakech.decodeAssamString(gameString);
        return Marrakech.moveAssam(currentAssam, dieResult);
    }

    /**
     * A method for randomly pick a possible rug string
     *
     * @param gameString A string representation of game state.
     * @return a possible rug string
     */
    public String randomPlace(String gameString) {
        //An integer to randomly pick a rug string from all possible rug string
        ArrayList<String> possibleRugString = generatePossibleRugs(gameString);
        int random = (int) (possibleRugString.size() * Math.random());
        return possibleRugString.get(random);
    }


    /**
     * A method for generating all possible rug strings
     *
     * @param gameString A string representation of game state.
     * @return a collection of all possible rug strings
     */
    public ArrayList<String> generatePossibleRugs(String gameString) {
        ArrayList<String> splitedBoardString = Marrakech.splitBoardString(gameString);
        //They store the coordinates of two tiles.
        String firstTile;
        String secondTile;
        //It stores all possible rugString that the computer can place on the board.
        ArrayList<String> possibleRugString = new ArrayList<>();
        String rugString;
        for (int i = 0; i < splitedBoardString.size(); i++) {
            for (int j = i + 1; j < splitedBoardString.size(); j++) {
                firstTile = Marrakech.getCoordinateFromIndex(i);
                secondTile = Marrakech.getCoordinateFromIndex(j);
                rugString = this.colour + this.id + firstTile + secondTile;
                //If it is a valid rug string and it can be placed, add it to possibleRugString
                if (Marrakech.isRugValid(gameString, rugString) && Marrakech.isPlacementValid(gameString, rugString)) {
                    possibleRugString.add(rugString);
                }
            }
        }
        this.id = idIncrease(this.id);
        return possibleRugString;
    }


    /**
     * A method for smartly picking rug string
     *
     * @param gameString A string representation of game state.
     * @return a better rug string
     */
    public String smartPlace(String gameString) {
        ArrayList<String> splitedBoardString = Marrakech.splitBoardString(gameString);
        ArrayList<String> possibleRugString = generatePossibleRugs(gameString);
        IntPair curTile;
        IntPairAI firstTile;
        IntPairAI secondTile;
        IntPairAI averageTile;
        String bestRug = null;
        double bestLoss = Double.MAX_VALUE;
        double loss = 0d;
        for (String s : possibleRugString) {
            firstTile = IntPairAI.getFirstIntpair(s);
            secondTile = IntPairAI.getSecondIntpair(s);
            averageTile = IntPairAI.average(firstTile, secondTile);
            for (int j = 0; j < splitedBoardString.size(); j++) {
                if (!splitedBoardString.get(j).substring(0, 1).equals(this.colour)) {
                    continue;
                }
                String coordinates = Marrakech.getCoordinateFromIndex(j);
                curTile = IntPair.getIntPair(coordinates);
                loss += IntPairAI.calLoss(averageTile, curTile);
            }
            if (loss < bestLoss) {
                bestLoss = loss;
                bestRug = s;
            }
        }
        return bestRug;

    }


    /**
     * A method for make placement after assam rotations and movement
     *
     * @param currentGame A String representation of the current state of the game.
     * @param rug         A String representation of the rug that is to be placed.
     * @returnA new game string representing the game following the successful placement of this rug if it is valid,
     * or the input currentGame unchanged otherwise.
     */
    public static String makePlacementAI(String currentGame, String rug) {
        return Marrakech.makePlacement(currentGame, rug);
    }

    /**
     * A method for adding id by 1
     *
     * @param id a string denotes the id
     * @return a new id string after update
     */
    public static String idIncrease(String id) {
        int num = Integer.parseInt(id);
        num++;
        //If the id number is <10, add "0" before it.
        if (num < 10) {
            return "0" + num;
        }
        return Integer.toString(num);
    }


}

