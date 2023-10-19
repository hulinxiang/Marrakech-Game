package comp1110.ass2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class PlayerTest {
    String globalDirection = "E:/comp6710/comp1110-ass2/tests/comp1110/ass2/testdata/";

    @Test
    public void testDecodeColour() throws IOException {
        BufferedReader fr = new BufferedReader(new FileReader(globalDirection + "decode_colour.txt"));
        String[] testData = fr.readLine().split("@");
        for (int i = 0; i < testData.length; i += 2) {
            Assertions.assertEquals(testData[i + 1], Player.decodeColour(testData[i]));
        }
        fr.close();
    }

    @Test
    public void testDecodeColourInvalid() throws IOException {
        BufferedReader fr = new BufferedReader(new FileReader(globalDirection + "decode_colour_invalid.txt"));
        String[] testData = fr.readLine().split("@");
        try {
            for (String testDatum : testData) {
                Player.decodePlayerString(testDatum);
            }
        } catch (RuntimeException e) {
            Assertions.assertTrue(e.getMessage().contains("Invalid colour"));
        }
        fr.close();
    }


}