package comp1110.ass2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
public class MoveAssamTest {
    /**
     * Check that movements of Assam which do not involve the edge of the board produce correct results
     */
    @Test
    public void checkSimpleMovements() {
        BufferedReader fr;
        fr = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("testdata/assam_movement_simple.txt")));
        Stream<String> testLines = fr.lines();
        for (String line : testLines.toList()) {
            String[] splitLine = line.split("@");
            // For this test, there's two arguments needed to the function
            Assertions.assertEquals(splitLine[2], Marrakech.moveAssam(splitLine[0], Integer.parseInt(splitLine[1])), splitLine[3]);
        }
    }

    /**
     * Check that movements of Assam which involve the edge of the board produce correct results
     */
    @Test
    public void checkComplexMovements() {
        BufferedReader fr;
        fr = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("testdata/assam_movement_complex.txt")));
        Stream<String> testLines = fr.lines();
        for (String line : testLines.toList()) {
            String[] splitLine = line.split("@");
            // For this test, there's two arguments needed to the function
            Assertions.assertEquals(splitLine[2], Marrakech.moveAssam(splitLine[0], Integer.parseInt(splitLine[1])), splitLine[3]);
        }
    }
}
