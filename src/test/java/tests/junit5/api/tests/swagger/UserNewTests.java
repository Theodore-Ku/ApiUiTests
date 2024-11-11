package tests.junit5.api.tests.swagger;


import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tests.junit5.api.addons.AdminUser;
import tests.junit5.api.assertions.Conditions;
import tests.junit5.api.models.swager.FullUser;
import tests.junit5.api.models.swager.Info;

import java.util.List;

import static tests.junit5.api.addons.RandomTestData.getAdminUser;

public class UserNewTests extends BaseApiTest {

    @Test
    public void positiveRegisterTest() {
        userService.register(randomUser)
                .should(Conditions.hasStatusCode(201))
                .should(Conditions.hasMessage("User created"));
    }

    @Test
    public void positiveRegisterWithGamesTest() {
        Response response = userService.register(randomUser)
                .should(Conditions.hasStatusCode(201))
                .should(Conditions.hasMessage("User created"))
                .asResponse();
        Info info = response.jsonPath().getObject("info", Info.class);

        SoftAssertions softAssertions = new SoftAssertions();

        softAssertions.assertThat(info.getMessage())
                .as("Сообщение об ошибке было неверное")
                .isEqualTo("фейк меседж");

        softAssertions.assertThat(response.statusCode())
                .as("Статус код не был 200")
                .isEqualTo(201);
        softAssertions.assertAll();
    }

    @Test
    public void negativeRegisterLoginExistsTest() {
        userService.register(randomUser);
        userService.register(randomUser)
                .should(Conditions.hasStatusCode(400))
                .should(Conditions.hasMessage("Login already exist"));
    }

    @Test
    public void negativeRegisterNoPasswordTest() {
        randomUser.setPass(null);

        userService.register(randomUser)
                .should(Conditions.hasStatusCode(400))
                .should(Conditions.hasMessage("Missing login or password"));
    }

    @Test
    public void positiveAdminAuthTest(@AdminUser FullUser admin) {
        String token = userService.auth(admin)
                .should(Conditions.hasStatusCode(200))
                .asJwt();

        Assertions.assertNotNull(token);
    }

    @Test
    public void positiveNewUserAuthTest() {
        userService.register(randomUser);

        String token = userService.auth(randomUser)
                .should(Conditions.hasStatusCode(200))
                .asJwt();

        Assertions.assertNotNull(token);
    }

    @Test
    public void negativeAuthTest() {
        userService.auth(randomUser)
                .should(Conditions.hasStatusCode(401));
    }

    @Test
    public void positiveGetUserInfoTest() {
        FullUser user = getAdminUser();
        String token = userService.auth(user).asJwt();
        userService.getUserInfo(token)
                .should(Conditions.hasStatusCode(200));
    }

    @Test
    public void negativeGetUserInfoInvalidJwtTest() {
        userService.getUserInfo("fake jwt")
                .should(Conditions.hasStatusCode(401));
    }

    @Test
    public void negativeGetUserInfoWithoutJwtTest() {
        userService.getUserInfo()
                .should(Conditions.hasStatusCode(401));
    }

    @Test
    public void positiveChangeUserPassTest() {
        String oldPassword = randomUser.getPass();
        userService.register(randomUser);

        String token = userService.auth(randomUser).asJwt();

        String updatedPassValue = "newpassUpdated";

        userService.updatePass(updatedPassValue, token)
                .should(Conditions.hasStatusCode(200))
                .should(Conditions.hasMessage("User password successfully changed"));

        randomUser.setPass(updatedPassValue);

        token = userService.auth(randomUser).should(Conditions.hasStatusCode(200)).asJwt();

        FullUser updatedUser = userService.getUserInfo(token).as(FullUser.class);

        Assertions.assertNotEquals(oldPassword, updatedUser.getPass());
    }

    @Test
    public void negativeChangeAdminPasswordTest() {
        FullUser user = getAdminUser();

        String token = userService.auth(user).asJwt();

        String updatedPassValue = "newpassUpdated";
        userService.updatePass(updatedPassValue, token)
                .should(Conditions.hasStatusCode(400))
                .should(Conditions.hasMessage("Cant update base users"));
    }

    @Test
    public void negativeDeleteAdminTest() {
        FullUser user = getAdminUser();

        String token = userService.auth(user).asJwt();

        userService.deleteUser(token)
                .should(Conditions.hasStatusCode(400))
                .should(Conditions.hasMessage("Cant delete base users"));
    }

    @Test
    public void positiveDeleteNewUserTest() {
        userService.register(randomUser);
        String token = userService.auth(randomUser).asJwt();

        userService.deleteUser(token)
                .should(Conditions.hasStatusCode(200))
                .should(Conditions.hasMessage("User successfully deleted"));
    }

    @Test
    public void positiveGetAllUsersTest() {
        List<String> users = userService.getAllUsers().asList(String.class);
        Assertions.assertTrue(users.size() >= 3);
    }
}
