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
public class MakePlacementTest {
    @Test
    public void nonAdjacentTest() {
        BufferedReader fr;
        try {
            fr = new BufferedReader(new FileReader("assets/testcases/make_placement_invalid_adjacency.txt"));
            Stream<String> testLines = fr.lines();
            for (String line : testLines.toList()) {
                String[] splitLine = line.split("@");
                // For this test, there's two arguments needed to the function
                Assertions.assertEquals(splitLine[2], Marrakech.makePlacement(splitLine[0], splitLine[1]), splitLine[3]);
            }
        } catch (IOException ex) {
            System.out.println("Test case file couldn't be read with error " + ex.getMessage());
        }
    }
    @Test
    public void coveringTest() {
        BufferedReader fr;
        try {
            fr = new BufferedReader(new FileReader("assets/testcases/make_placement_invalid_covering.txt"));
            Stream<String> testLines = fr.lines();
            for (String line : testLines.toList()) {
                String[] splitLine = line.split("@");
                // For this test, there's two arguments needed to the function
                Assertions.assertEquals(splitLine[2], Marrakech.makePlacement(splitLine[0], splitLine[1]), splitLine[3]);
            }
        } catch (IOException ex) {
            System.out.println("Test case file couldn't be read with error " + ex.getMessage());
        }
    }
    @Test
    public void validTest() {
        BufferedReader fr;
        try {
            fr = new BufferedReader(new FileReader("assets/testcases/make_placement_valid.txt"));
            Stream<String> testLines = fr.lines();
            for (String line : testLines.toList()) {
                String[] splitLine = line.split("@");
                // For this test, there's two arguments needed to the function
                Assertions.assertEquals(splitLine[2], Marrakech.makePlacement(splitLine[0], splitLine[1]), splitLine[3]);
            }
        } catch (IOException ex) {
            System.out.println("Test case file couldn't be read with error " + ex.getMessage());
        }
    }
}
