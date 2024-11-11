package tests.junit5.api.tests.swagger;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import tests.junit5.api.addons.AdminUserResolver;
import tests.junit5.api.addons.CustomTpl;
import tests.junit5.api.models.swager.FullUser;
import tests.junit5.api.services.UserService;

import static tests.junit5.api.addons.RandomTestData.getRandomUser;

@ExtendWith(AdminUserResolver.class)
public class BaseApiTest {
    protected static UserService userService;
    protected FullUser randomUser;

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "http://85.192.34.140:8080/api";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter(),
                CustomTpl.customLogFilter().withCustomTemplates());
        userService = new UserService();
    }
    @BeforeEach
    public void initTestUser() {
        randomUser = getRandomUser();
    }
}
