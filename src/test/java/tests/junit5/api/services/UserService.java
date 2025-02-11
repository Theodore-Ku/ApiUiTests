package tests.junit5.api.services;

import io.restassured.http.ContentType;
import tests.junit5.api.assertions.AssertableResponse;
import tests.junit5.api.models.swager.FullUser;
import tests.junit5.api.models.swager.JwtAuthData;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class UserService {
    public AssertableResponse register(FullUser user){
        return new AssertableResponse(given().contentType(ContentType.JSON)
                .body(user)
                .post("/signup")
                .then());
    }

    public AssertableResponse getUserInfo(String jwt){
        return new AssertableResponse(given().auth().oauth2(jwt)
                .get("/user")
                .then());
    }

    public AssertableResponse getUserInfo(){
        return new AssertableResponse(given()
                .get("/user")
                .then());
    }

    public AssertableResponse updatePass(String newPassword, String jwt){
        Map<String, String> password = new HashMap<>();
        password.put("password", newPassword);

        return new AssertableResponse(given().contentType(ContentType.JSON)
                .auth().oauth2(jwt)
                .body(password)
                .put("/user")
                .then());
    }

    public AssertableResponse deleteUser(String jwt){
        return new AssertableResponse(given().auth().oauth2(jwt)
                .delete("/user")
                .then());
    }

    public AssertableResponse auth(FullUser fullUser){
        JwtAuthData data = new JwtAuthData(fullUser.getLogin(), fullUser.getPass());
        return new AssertableResponse(given().contentType(ContentType.JSON)
                .body(data)
                .post("/login")
                .then());
    }

    public AssertableResponse getAllUsers(){
        return new AssertableResponse(given()
                .get("/users")
                .then());
    }
}
