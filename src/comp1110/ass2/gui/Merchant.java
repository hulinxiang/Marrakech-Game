package comp1110.ass2.gui;


public class Merchant {
    int x; //X coordinate position of merchant on the board
    int y; //Y coordinate position of merchant on the board

    Direction direction;

    Merchant(int x, int y){
        this.x = x;
        this.y = y;
    }

    /*
    State of the player, IN_GAME = player still in the game, OUT_GAME = player has run out of money,
    OUT_RUGS = player has run out of rugs.
     */
    public enum PlayerState{
        IN_GAME, OUT_GAME, OUT_RUGS;
    }
    /*
    Sets the initial direction of the merchant at the start of the game.
     */
    public void firstDirection(Direction d){
        this.direction = d;

    }

    /*
    Rotates the merchant depending on the player input.
    d: The current direction the merchant is facing.
    rotateValue: -1 = left rotation, 0 = no rotation, 1 = right rotation.
     */
    public void Rotate(Direction d, int rotateValue){

    }

    /*
    Moves the required number of steps as indicated by the die, in the specific direction.
     */
    public void Move(Direction d, int steps){

    }

   /*
    Checks that the rotation is no more than 90 degrees. Returns false if illegal rotation, returns true if legal.
     */
    public static boolean checkRotation(Direction d){
        return true;
    }


}
