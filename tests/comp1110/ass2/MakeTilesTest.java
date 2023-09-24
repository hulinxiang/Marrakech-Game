package comp1110.ass2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;
import java.util.Random;


@Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
public class MakeTilesTest {

    //The full game string
    //String gameString = "Pr00803iPy01305iPc01510oA04NBc01c02n00c03c04y05p06c07c08y09c10c10y11p12p12c13y14c15c15y16c17c17r18y19c20c20y21c22c22r23r23c25c25y26c26c27c27r28r28c29y30c31c31c32y33c34c34y35c36";

    //String of tile colours
    //String colourString = "ccnccypccyccyppcyccyccryccyccrrccycccrrcycccyccyc";

    String[] randomResult = generateTestString();
    String cString = randomResult[1];
    String gameString = randomResult[0];
    Marrakech Game = new Marrakech(gameString);


    public static String[] generateTestString(){
        String theString = "";
        //First part of game string does not really matter since we are testing makeTiles hence first part
        //can be constant
        String firstPart = "Pr00803iPy01305iPc01510oA04NBn00";
        //Have the first tile be empty to make it easier to test

        //Second part randomly generates colour and determines id:
        String secondPart = "";
        String colourString = "n";
        int idCount = 1;
        boolean notNext = true; //Rug can cover two tiles.
        for(int i =1; i<49; i++){
            String colour = "";
            String id = "";
            String idCol = "";
            Random random = new Random();
            int randomNum = random.nextInt(5); //Number between 0 and 4 (inclusive)
            switch(randomNum){
                case 0:
                    colour = "c";
                    break;
                case 1:
                    colour = "y";
                    break;
                case 2:
                    colour = "p";
                    break;
                case 3:
                    colour = "r";
                    break;
                case 4:
                    colour = "n";
                    break;
            }

            colourString += colour;
            //If empty then id is 00
            if(colour == "n"){
                idCol = "n00";
                notNext = false;
            }
            else{
                if(idCount < 10){ //Adding 0 before single digit ids.
                    id = "0" + Integer.toString(idCount);
                }
                else{
                    id = Integer.toString(idCount);
                }

               idCol = colour + id;

                //Determine if rug should be covering two tiles, or only visible on one.
                int randomBoo = random.nextInt(2) +1; //1 or 2;
                if(notNext && (randomBoo == 2)){
                    idCol = idCol + idCol;
                    colourString += colour;
                    notNext = false;
                    i++;
                }
                else{
                    notNext = true;
                }
                idCount += 1;
            }

            secondPart += idCol;
        }
        theString += firstPart + secondPart;
        String[] returnS = {theString, colourString};
        return returnS;
    }

    @Test
    public void emptyTileTest(){
       // generateTestString();
        //Marrakech Game = new Marrakech(gameString);
        //Testing that the state for empty tiles is 0.In the test string above, the 3rd tile is empty.
        Assertions.assertEquals(0, Game.board.tiles[0][0].state);

        //Testing that the owner of the empty tile is null.
        Assertions.assertEquals(null, Game.board.tiles[0][0].owner);

        //Testing the colour of empty tile is "NULL".
        Assertions.assertEquals("NULL", Game.board.tiles[0][0].colour);
    }

    @Test
    public void correctPositionTest(){
        //generateTestString();
        //Marrakech Game = new Marrakech(gameString);
        for(int h=0; h<Board.BOARD_WIDTH; h++){
            for(int i=0; i<Board.BOARD_HEIGHT; i++){
                IntPair expectedPos = new IntPair(h, i);
                Assertions.assertEquals(expectedPos, Game.board.tiles[h][i].tilePosition);
            }
        }

    }

    @Test
    public void correctColourTest(){
        //generateTestString();
        //Marrakech Game = new Marrakech(gameString);
        Random random = new Random();
        //Test 20 times
        int counter = 0;
        while(counter < 20){
            int randomIndex = random.nextInt(49); //There are 49 tiles, random between 0 and 48 inclusive.
            int x = randomIndex / 7; //Using integer divsion
            int y = randomIndex % 7;
            String expectedColour = cString.substring(randomIndex, randomIndex+1).toUpperCase();
            //Get the first letter of the colour and compare with what the colour should be.
            Assertions.assertEquals(expectedColour, Game.board.tiles[x][y].colour.substring(0,1));
            counter += 1;
        }

    }


}
