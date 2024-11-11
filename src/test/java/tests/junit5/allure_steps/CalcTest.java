package tests.junit5.allure_steps;

import io.qameta.allure.Allure;
import io.qameta.allure.Issue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class CalcTest {
    @Test
    public void sumTest() {
        CalcSteps calcSteps = new CalcSteps();
        int result = calcSteps.sum(1, 4);
        boolean isOk = calcSteps.isPositive(result);
        Assertions.assertFalse(isOk);
    }

    @Test
    @Issue("VIDEOTECH-5612")
    public void sumStepsTest() {
        int a = 5;
        int b = 4;
        AtomicInteger result = new AtomicInteger();
        Allure.step("Прибавляем " + a + " к переменной " + b, step -> {
            result.set(a + b);
        });
        Allure.step("Проверяем что результат " + result.get() + " больше нуля", step -> {
            Assertions.assertTrue(result.get() > 0);
        });
    }
}
