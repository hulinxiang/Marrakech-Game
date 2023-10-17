package comp1110.ass2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertTrue;
public class DiceTest {
    @Test
    public void testRoll() {
        // Rolling the dice 1000 times to ensure that the results are as expected
        for (int i = 0; i < 1000; i++) {
            int result = Dice.Roll();

            // Checking if the result of the dice roll is a valid value
            assertTrue(result >= 1 && result <= 4, "Invalid dice roll: " + result);
            // checking the distribution of the results if necessary.
        }
    }

}
