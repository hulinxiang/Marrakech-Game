package comp1110.ass2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.fail;


public class MerchantTest {
    private final Merchant merchant = new Merchant();

    //Test decodeAssamString with invalid string format
    @Test
    public void decodeAsamString() throws IOException {
        BufferedReader fr;
        fr = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("testdata/decode_assam_string.txt")));
        String s;
        while ((s = fr.readLine()) != null) {
            String[] testString = s.split("@");
            for (String test : testString) {
                merchant.decodeAsamString(test);
                int x = merchant.merchantPosition.getX();
                int y = merchant.merchantPosition.getY();
                String direction = merchant.getDirection().toString().substring(0, 1);
                Assertions.assertEquals(Integer.parseInt(test.substring(0, 1)), x);
                Assertions.assertEquals(Integer.parseInt(test.substring(1, 2)), y);
                Assertions.assertEquals(test.substring(2), direction);
            }
        }
        fr.close();
    }

    //Test decodeAssamString with invalid string format
    @Test
    public void decodeAssamStringInvalid() throws IOException {
        BufferedReader fr;
        fr = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("testdata/decode_assam_string_invalid.txt")));
        String s;
        while ((s = fr.readLine()) != null) {
            String[] testString = s.split("@");
            for (String test : testString) {
                try {
                    merchant.decodeAsamString(test);
                    fail("No exception thrown.");
                } catch (Exception e) {
                    Assertions.assertTrue(e.getMessage().contains("Out of board") || e.getMessage().contains("Invalid Asam Direction"));
                }
            }
        }
        fr.close();
    }

    @Test
    public void rotate() throws IOException {
        BufferedReader fr;
        fr = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("testdata/rotate_assam_valid.txt")));
        String s;
        while ((s = fr.readLine()) != null) {
            String[] testString = s.split("@");
            for (int i = 0; i < testString.length; i += 3) {
                Assertions.assertEquals(testString[i + 2].charAt(0), Merchant.rotate(testString[i].charAt(0), Integer.parseInt(testString[i + 1])));
            }
        }
    }

    @Test
    public void rotateInvalid() throws IOException {
        BufferedReader fr;
        fr = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("testdata/rotate_assam_invalid.txt")));
        String s;
        while ((s = fr.readLine()) != null) {
            String[] testString = s.split("@");
            for (int i = 0; i < testString.length; i += 2) {
                try {
                    Merchant.rotate(testString[i].charAt(0), Integer.parseInt(testString[i + 1]));
                    fail("No exception thrown.");
                } catch (RuntimeException e) {
                    Assertions.assertTrue(e.getMessage().contains("invalid direction"));
                }
            }
        }
        fr.close();
    }


}
