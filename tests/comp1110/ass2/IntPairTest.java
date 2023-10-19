package comp1110.ass2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class IntPairTest {


    @Test
    public void testAdd() throws IOException {
        BufferedReader fr;
        //Read data from file
        fr =  new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("testdata/intpair_add.txt")));
        String str;
        IntPair first = null;
        IntPair second = null;
        while ((str = fr.readLine()) != null) {
            String[] testString = str.split("@");
            for (int i = 0; i < testString.length; i++) {
                int index = testString[i].indexOf(",");
                if (i == 0) {
                    first = new IntPair(Integer.parseInt(testString[i].substring(0, index)),
                            Integer.parseInt(testString[i].substring(index + 1)));
                } else {
                    second = new IntPair(Integer.parseInt(testString[i].substring(0, index)),
                            Integer.parseInt(testString[i].substring(index + 1)));
                }
            }
            assert second != null;
            Assertions.assertEquals(first.x + second.x, first.add(second).x, "x adding operation is wrong");
            Assertions.assertEquals(first.y + second.y, first.add(second).y, "y adding operation is wrong");
        }

        fr.close();
    }
}
