package tests.junit5.ui.base_tests.selenide;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import tests.junit5.ui.base_tests.page_objects.unitickets.UtMainSelenidePage;

public class UtSelenideTest {
    @Test
    public void firstSelenideTest(){
        int expectedDayForward = 25;
        int expectedDayBack = 30;

        Selenide.open("https://uniticket.ru/");

        UtMainSelenidePage mainPage = new UtMainSelenidePage();
        mainPage.setCityFrom("Казань")
                .setCityTo("Дубай")
                .setDayForward(expectedDayForward)
                .setDayBack(expectedDayBack)
                .search()
                .waitForPage()
                .waitForTitleDisappear()
                .assertMainDayBack(expectedDayBack)
                .assertMainDayForward(expectedDayForward)
                .assertAllDaysBackShouldHaveDay(expectedDayBack)
                .assertAllDaysForwardShouldHaveDay(expectedDayForward);
    }
}
