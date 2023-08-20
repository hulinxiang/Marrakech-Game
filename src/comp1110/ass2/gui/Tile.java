package comp1110.ass2.gui;
/*
Tile coordinates and their state on the board.
 */
public class Tile {
    int x; //x coordinate of the tile
    int y; //y coordinate of the tile
    Player owner;

    Tile(int x, int y){
        this.x = x;
        this.y = y;
    }
    public enum TileState{
        EMPTY, OCCUPIED;
    }

    /*
    Sets the owner whose rug is on top of the tile.
     */
    public void occupiedBy(Player player){
        this.owner = player;
    }

    /*
    Checks the current owner of the tile.
     */
    public Player checkOccupation(){
        return null;
    }
}
