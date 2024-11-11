package tests.junit5.reruns;

import listeners.RetryListenerJunit5;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(RetryListenerJunit5.class)
public class SimpleTests {
    @AfterAll
    public static void saveFailed(){
        RetryListenerJunit5.saveFailedTests();
    }
    @Test
    public void exampleOfRerunTest(){
        int b = 6;
        System.out.println(b);
        Assertions.assertEquals(2,b);
    }
}