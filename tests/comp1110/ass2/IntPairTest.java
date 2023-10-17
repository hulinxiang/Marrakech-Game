package comp1110.ass2;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
public class IntPairTest {
    @Test
    public void testAdd() {
        // Test case 1
        IntPair pair1 = new IntPair(1, 2);
        IntPair pair2 = new IntPair(3, 4);
        IntPair result = pair1.add(pair2);

        assertEquals(4, result.getX(), "The x value of the resulting pair is incorrect.");
        assertEquals(6, result.getY(), "The y value of the resulting pair is incorrect.");

        // Test case 2: Testing with negative numbers
        IntPair pair3 = new IntPair(-1, -2);
        IntPair pair4 = new IntPair(3, 4);
        IntPair result2 = pair3.add(pair4);

        assertEquals(2, result2.getX(), "The x value of the resulting pair is incorrect with negative numbers.");
        assertEquals(2, result2.getY(), "The y value of the resulting pair is incorrect with negative numbers.");

        // Additional test cases can be added here as needed
    }
}
