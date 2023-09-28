package comp1110.ass2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.fail;


public class MerchantTest {
    //Test decodeAssamString with invalid string format
    @Test
    public void decodeAsamString() throws IOException {
        BufferedReader fr;
        fr = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("testdata/decode_assam_string.txt")));
        String s;
        while ((s = fr.readLine()) != null) {
            String[] testString = s.split("@");
            for (String test : testString) {
                Merchant merchant = new Merchant();
                merchant.decodeAsamString(test);
                int x = merchant.merchantPosition.getX();
                int y = merchant.merchantPosition.getY();
                String direction = merchant.getDirection().toString().substring(0, 1);
                Assertions.assertEquals(Integer.parseInt(test.substring(0, 1)), x);
                Assertions.assertEquals(Integer.parseInt(test.substring(1, 2)), y);
                Assertions.assertEquals(test.substring(2), direction);
            }
        }
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
                Merchant merchant = new Merchant();
                try {
                    merchant.decodeAsamString(test);
                    fail("No exception thrown.");
                } catch (Exception e) {
                    Assertions.assertTrue(e.getMessage().contains("Invalid Asam String"));
                }
            }
        }
    }
}
