package comp1110.ass2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {
    @Test
    public void testDecodeColour(){
        Player player = new Player();
        String[] colour = new String[]{"C", "Y", "P", "R"};
        for (int i =0; i <colour.length; i++){
            String str = player.decodeColour(colour[i]);
            assertEquals(colour[i], str.substring(0,1));
        }


    }
}