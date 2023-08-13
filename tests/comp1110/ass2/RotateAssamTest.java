package comp1110.ass2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
public class RotateAssamTest {
    /**
     * Check that rotations of Assam that are legal according to the rules result in the correct eventual facing
     */
    @Test
    public void checkLegalLeftRotations() {
        BufferedReader fr;
        try {
            fr = new BufferedReader(new FileReader("assets/testcases/legal_left_rotations.txt"));
            Stream<String> testLines = fr.lines();
            for (String line : testLines.toList()) {
                String[] splitLine = line.split("@");
                // For this test, there's two arguments needed to the function
                Assertions.assertEquals(splitLine[2], Marrakech.rotateAssam(splitLine[0], Integer.parseInt(splitLine[1])), splitLine[3]);
            }
        } catch (IOException ex) {
            System.out.println("Test case file couldn't be read with error " + ex.getMessage());
        }
    }

    @Test
    public void checkLegalRightRotations() {
        BufferedReader fr;
        try {
            fr = new BufferedReader(new FileReader("assets/testcases/legal_right_rotations.txt"));
            Stream<String> testLines = fr.lines();
            for (String line : testLines.toList()) {
                String[] splitLine = line.split("@");
                // For this test, there's two arguments needed to the function
                Assertions.assertEquals(splitLine[2], Marrakech.rotateAssam(splitLine[0], Integer.parseInt(splitLine[1])), splitLine[3]);
            }
        } catch (IOException ex) {
            System.out.println("Test case file couldn't be read with error " + ex.getMessage());
        }
    }

    /**
     * Check that rotations of Assam that are *not* legal according to the rules result in no change to Assam's state
     */
    @Test
    public void checkIllegalRotations() {
        Assertions.assertEquals("A22N", Marrakech.rotateAssam("A22N", 180));
        Assertions.assertEquals("A01W", Marrakech.rotateAssam("A01W", 55));
        // more to come..
    }
}
