package comp1110.ass2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;


public class MerchantTest {
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
                String direction=merchant.getDirection().toString().substring(0,1);
                Assertions.assertEquals(Integer.parseInt(test.substring(0,1)),x);
                Assertions.assertEquals(Integer.parseInt(test.substring(1,2)),y);
                Assertions.assertEquals(test.substring(2),direction);
            }
        }
    }
}
