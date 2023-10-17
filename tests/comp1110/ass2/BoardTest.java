package comp1110.ass2;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class BoardTest {
    @Test
    public void testWithinBoard(){
        Board board = new Board();
        //Assuming IntPair has a constructor that take 2 integers
        IntPair insidePair = new IntPair(3, 3);
        IntPair outsidePair = new IntPair(10, 10);
        assertTrue(board.withInBoard(insidePair),"Point should be within board");
        assertFalse(board.withInBoard(outsidePair), "point should be outside the board");
    }
    @Test
    public void testCheckBoard(){
        Board board = new Board();
        board.boardSetUp();
        assertTrue(board.checkBoard(), "Board should be properly set up");
    }
}
