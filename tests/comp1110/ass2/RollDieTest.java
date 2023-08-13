package comp1110.ass2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * This class implements the test for the rollDie method and associated task. Note that it contains
 * code which you will likely not be able to read until we've covered unit A06 in lectures, which will happen
 * well after the release of the assignment. You are not expected to be able to understand this code to complete
 * the assignment. The error messages should do a good job of explaining where your code has gone wrong, and if
 * you have any questions not explained by those messages, please ask your tutor in a lab session.
 */
@Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
public class RollDieTest {
    static HashMap<Integer, Integer> getDistribution() {
        HashMap<Integer, Integer> outMap = new HashMap<>();
        for (int i = 0; i < 1000; i++) {
            int res = Marrakech.rollDie();
            int currCount = outMap.getOrDefault(res, 0);
            outMap.put(res, currCount+1);
        }
        return outMap;
    }

    @Test
    public void checkBounds() {
        HashMap<Integer, Integer> distribution = getDistribution();
        for (int face : distribution.keySet()) {
            Assertions.assertTrue((face >= 1 && face <= 4), "Expected that dice should only return values between 1 and 4, but got " + face);
        }
    }
    @Test
    public void checkDistribution() {
        HashMap<Integer, Integer> distribution = getDistribution();
        for (int face : distribution.keySet()) {
            Assertions.assertTrue((face >= 1 && face <= 4), "Expected that dice should only return values between 1 and 4, but got " + face);
        }
        Assertions.assertEquals(distribution.get(1), distribution.get(4), 100, "Expected that 1 and 4 should appear a roughly equal number of times, but there were "
                + distribution.get(1) + " 1s, and " + distribution.get(4) + " 4s.");
        Assertions.assertEquals(distribution.get(2), distribution.get(3), 100, "Expected that 2 and 3 should appear a roughly equal number of times, but there were "
                + distribution.get(2) + " 2s, and " + distribution.get(3) + " 3s.");
        Assertions.assertEquals((2 * distribution.get(1)), distribution.get(2), 100, "Expected roughly twice as many 2s as 1s, but there were "
                + distribution.get(1) + " 1s, and " + distribution.get(2) + " 2s.");
    }
}
