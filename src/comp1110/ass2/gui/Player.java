package comp1110.ass2.gui;
/*
All aspects of the player including its coins, and rugs, and current state.
 */
public class Player {
    final int START_COINS = 30; //Starting number of coins
    final int START_RUGS = 15; //Starting number of rugs;

    int coins; //Number of coins
    int rugs; //Number of rugs

    Player(int coins, int rugs){
        this.coins = START_COINS;
        this.rugs = START_RUGS;
    }

    /*
    Player pays other player money, updates this.coins field.
     */
    public void makePayment(){

    }

    /*
    Player receives money from another players.
     */
    public void getPayment(){

    }
    /*
    Counts how many rugs of the player is on the board.
     */
    public int rugCount(){
        return 0;
    }

    /*
    Places the rug of the player on the board.
     */
    public void placeRug(){

    }

    /*
    Checks that the player has enough money.
     */
    public boolean moneyCheck(){
        return true;  //Boolean denoting true if the player has enough, false if not.
    }

    /*
    Checks if the player has rugs left to place on the bord.
     */
    public boolean rugCheck(){
        return true; //Boolean denoting true if the player still has rugs to place, false if not.
    }

    /*
    Counts how many rugs of the player are connected.
     */
    public int Connected(int[][] rugCoords){
        return 0;
    }

}
