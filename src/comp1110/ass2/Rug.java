package comp1110.ass2;
/*
Behaviour of the rug on the board
 */
public class Rug {

    int[][] rugPosition; //Denotes rug position. HORIZONTAL (X1, Y), (X2, Y) VERTICAL (X, Y1), (X, Y2).

    /*
    Stores the coordinates of where the rug is placed.
     */
    public void rugPlacement(){

    }

    /*
    Checks that the rug shares a border with the merchant
     */
    public boolean checkShareBoarder(){
        return true;
    }

    /*
    Checks that the rug is not placed outside the bounds of the board.
     */
    public boolean correctPlacement(){
        return true;
    }

    /*
    Checks that the rug does not fully overlap another player's rug.
     */
    public boolean overlapCheck(){
        return true;
    }


}
