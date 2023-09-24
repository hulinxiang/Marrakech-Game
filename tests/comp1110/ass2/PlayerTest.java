package comp1110.ass2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {
    @Test
    public void testDecodeColour(){
        Player player = new Player();
        String[] colour = new String[]{"c", "y", "p", "r"};
        for (int i =0; i <colour.length; i++){
            String ans = null ;
            switch (colour[i]){
                case "c":
                    ans = "cyan";
                    break;
                case "y":
                    ans = "yellow";
                    break;
                case "p":
                    ans = "purple";
                    break;
                case "r":
                    ans = "red";
                    break;
                default:
            }
            assertEquals(ans,player.decodeColour(colour[i]));
        }
    }
}