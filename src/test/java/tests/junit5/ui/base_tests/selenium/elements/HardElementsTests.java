package tests.junit5.ui.base_tests.selenium.elements;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.util.List;


public class HardElementsTests {
    private WebDriver driver;
    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }
    @AfterEach
    public void tearDown() {
        driver.close();
    }
    @Test
    public void basicAuthTest() {
        driver.get("https://admin:admin@the-internet.herokuapp.com/basic_auth");
        String h3 = driver.findElement(By.xpath("//h3")).getText();
        Assertions.assertEquals("Basic Auth", h3);
    }

    @Test
    public void alertOk() {
        String expectedText = "I am a JS Alert";
        String expectedResult = "You successfully clicked an alert";

        driver.get("https://the-internet.herokuapp.com/javascript_alerts");
        driver.findElement(By.xpath("//button[@onclick='jsAlert()']")).click();
        String actualText = driver.switchTo().alert().getText();
        driver.switchTo().alert().accept();
        String result = driver.findElement(By.id("result")).getText();
        Assertions.assertEquals(expectedText, actualText);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    public void iframeTest() {
        driver.get("https://mail.ru/");
        driver.findElement(By.xpath("//button[@data-testid='enter-mail-primary']")).click();

        WebElement iframeAuth = driver.findElement(By.xpath("//iframe[@class='ag-popup__frame__layout__iframe']"));
        driver.switchTo().frame(iframeAuth);

        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("aqa@mail.ru");
    }

    @Test
    public void sliderTest() {
        driver.get("http://85.192.34.140:8081/");
        WebElement elementsCard = driver.findElement(By.xpath("//div[@class='card-body']//h5[text()='Widgets']"));
        elementsCard.click();

        WebElement elementsTextBox = driver.findElement(By.xpath("//span[text()='Slider']"));
        elementsTextBox.click();

        WebElement slider = driver.findElement(By.xpath("//input[@type='range']"));

        Actions actions = new Actions(driver);
        actions.dragAndDropBy(slider, 50, 0).build().perform();

        int expectedValue = 85;
        int currentValue = Integer.parseInt(slider.getAttribute("value"));
        int valueToMove = expectedValue - currentValue;
        for (int i = 0; i < valueToMove; i++) {
            slider.sendKeys(Keys.ARROW_RIGHT);
        }

        WebElement sliderValue = driver.findElement(By.id("sliderValue"));
        int actualValue = Integer.parseInt(sliderValue.getAttribute("value"));
        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    public void hoverTest() {
        driver.get("http://85.192.34.140:8081/");
        WebElement elementsCard = driver.findElement(By.xpath("//div[@class='card-body']//h5[text()='Widgets']"));
        elementsCard.click();

        WebElement elementsTextBox = driver.findElement(By.xpath("//span[text()='Menu']"));
        elementsTextBox.click();

        WebElement menuItemMiddle = driver.findElement(By.xpath("//a[text()='Main Item 2']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(menuItemMiddle).build().perform();

        WebElement subSubList = driver.findElement(By.xpath("//a[text()='SUB SUB LIST »']"));
        actions.moveToElement(subSubList).build().perform();

        List<WebElement> lastElements = driver.findElements(By.xpath("//a[contains(text(),'Sub Sub Item')]"));
        Assertions.assertEquals(2, lastElements.size());
    }

}
