package comp1110.ass2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;
import java.util.Random;
import java.util.stream.Stream;

@Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
public class MakeTilesTest {

    //The full game string
    String gameString = "Pr00803iPy01305iPc01510oA04NBc01c02n00c03c04y05p06c07c08y09c10c10y11p12p12c13y14c15c15y16c17c17r18y19c20c20y21c22c22r23r23c25c25y26c26c27c27r28r28c29y30c31c31c32y33c34c34y35c36";

    //String of tile colours
    String colourString = "ccnccypccyccyppcyccyccryccyccrrccycccrrcycccyccyc";
    Marrakech Game = new Marrakech(gameString);
    @Test
    public void emptyTileTest(){
        //Testing that the state for empty tiles is 0.In the test string above, the 3rd tile is empty.
        Assertions.assertEquals(0, Game.board.tiles[0][2].state);

        //Testing that the owner of the empty tile is null.
        Assertions.assertEquals(null, Game.board.tiles[0][2].owner);

        //Testing the colour of empty tile is "NULL".
        Assertions.assertEquals("NULL", Game.board.tiles[0][2].colour);
    }

    @Test
    public void correctPositionTest(){
        for(int h=0; h<Board.BOARD_WIDTH; h++){
            for(int i=0; i<Board.BOARD_HEIGHT; i++){
                IntPair expectedPos = new IntPair(h, i);
                Assertions.assertEquals(expectedPos, Game.board.tiles[h][i].tilePosition);
            }
        }

    }

    @Test
    public void correctColourTest(){
        Random random = new Random();
        //Test 20 times
        int counter = 0;
        while(counter < 20){
            int randomIndex = random.nextInt(49); //There are 49 tiles, random between 0 and 48 inclusive.
            int x = randomIndex / 7; //Using integer divsion
            int y = randomIndex % 7;
            String expectedColour = colourString.substring(randomIndex, randomIndex+1).toUpperCase();
            //Get the first letter of the colour and compare with what the colour should be.
            Assertions.assertEquals(expectedColour, Game.board.tiles[x][y].colour.substring(0,1));
            counter += 1;
        }

    }

    @Test
    public void coverIDTest(){

    }

}
