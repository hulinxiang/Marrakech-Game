package comp1110.ass2;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class RugTest {
    @Test
    public void checkSharedBoard(){
        Rug rug = new Rug();
        assertTrue(rug.checkShareBoarder(),"the rug should share a board with merchant");
    }
    @Test
    public void ifPlaceCorrectly(){
        Rug rug = new Rug();
        assertTrue(rug.ifPlaceCorrectly(), "the rug should place correctly");
    }
    @Test
    public void ifOverlap(){
        Rug rug1 = new Rug();
        Rug rug2 = new Rug();
        assertTrue(rug1.ifOverlap(), "rug should not overlap on player's rug");
    }
}
