package tests.junit5.api.assertions.conditions;

import io.restassured.response.ValidatableResponse;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import tests.junit5.api.assertions.Condition;
import tests.junit5.api.models.swager.Info;

@RequiredArgsConstructor
public class MessageCondition implements Condition {
    private final String expectedMessage;
    @Override
    public void check(ValidatableResponse response) {
        Info info = response.extract().jsonPath().getObject("info", Info.class);
        Assertions.assertEquals(expectedMessage, info.getMessage());
    }
}
