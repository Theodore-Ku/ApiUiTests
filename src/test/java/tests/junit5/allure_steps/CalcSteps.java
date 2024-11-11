package tests.junit5.allure_steps;

import io.qameta.allure.Step;

public class CalcSteps {
    @Step("Складываем числа {a} и {b}")
    public int sum(int a, int b){
        return a+b;
    }

    @Step("Проверяем, что число {result} больше чем 0")
    public boolean isPositive(int result){
        return result>0;
    }
}
