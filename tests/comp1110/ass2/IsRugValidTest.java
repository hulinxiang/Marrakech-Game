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
public class IsRugValidTest {
    @Test
    public void checkSmallPayments() {
        BufferedReader fr;
        try {
            fr = new BufferedReader(new FileReader("assets/testcases/get_payment_small.txt"));
            Stream<String> testLines = fr.lines();
            for (String line : testLines.toList()) {
                String[] splitLine = line.split("@");
                // For this test, there's one argument needed to the function
                Assertions.assertEquals(splitLine[1], String.valueOf(Marrakech.getPaymentAmount(splitLine[0])), splitLine[2]);
            }
        } catch (IOException ex) {
            System.out.println("Test case file couldn't be read with error " + ex.getMessage());
        }
    }
}
