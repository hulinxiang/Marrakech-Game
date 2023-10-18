package comp1110.ass2;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class RugTest {
    @Test
    public void testCheckShareBorder() {
        Rug rug = new Rug();
        // assuming you have set up your rug and merchant position
        assertTrue(rug.checkShareBoarder(), "The rug should share a border with the merchant");
        // You can also add more scenarios to test when the rug doesn't share a border with the merchant
        // assertFalse statements go here
    }

    @Test
    public void testIfPlaceCorrectly() {
        Rug rug = new Rug();
        // assuming you have set up your rug within the bounds
        assertTrue(rug.ifPlaceCorrectly(), "The rug should be placed correctly within the bounds of the board");
        // You can also add more scenarios to test when the rug is placed out of bounds
        // assertFalse statements go here
    }

    @Test
    public void testIfOverlap() {
        Rug rug1 = new Rug();
        Rug rug2 = new Rug();
        // assuming you have set up your rugs
        assertFalse(rug1.ifOverlap(), "The rug should not fully overlap another player's rug");
        // You can also add more scenarios to test when the rug does fully overlap another player's rug
        // assertTrue statements go here
    }
}
