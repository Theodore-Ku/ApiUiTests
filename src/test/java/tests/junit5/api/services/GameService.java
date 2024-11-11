package tests.junit5.api.services;

import io.restassured.http.ContentType;
import tests.junit5.api.addons.RandomTestData;
import tests.junit5.api.assertions.AssertableResponse;
import tests.junit5.api.models.swager.DlcsItem;
import tests.junit5.api.models.swager.GamesItem;
import tests.junit5.api.models.swager.UpdField;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class GameService {
    public AssertableResponse getGames(String jwt) {
        return new AssertableResponse(given().auth().oauth2(jwt)
                .get("/user/games")
                .then());
    }

    public AssertableResponse getGame(String jwt, int id) {
        return new AssertableResponse(given().auth().oauth2(jwt)
                .pathParam("id", id)
                .get("/user/games/{id}")
                .then());
    }

    public AssertableResponse deleteGame(String jwt, int id) {
        return new AssertableResponse(given().auth().oauth2(jwt)
                .pathParam("id", id)
                .delete("/user/games/{id}")
                .then());
    }

    public AssertableResponse addRandomGame(String jwt, boolean withDlc) {
        GamesItem game;
        if (withDlc) {
            game = RandomTestData.getRandomGame();
        } else {
            game = RandomTestData.getRandomGame();
            game.setDlcs(new ArrayList<>());
        }
        return new AssertableResponse(given().contentType(ContentType.JSON)
                .auth().oauth2(jwt)
                .body(game)
                .post("/user/games")
                .then());
    }

    public AssertableResponse deleteListDlc(String jwt, int id, List<DlcsItem> dlcList) {
        return new AssertableResponse(given().contentType(ContentType.JSON)
                .auth().oauth2(jwt)
                .body(dlcList)
                .pathParam("id",id)
                .delete("/user/games/{id}/dlc")
                .then());
    }

    public AssertableResponse updateListDlc(String jwt, int id, List<DlcsItem> dlcList) {
        return new AssertableResponse(given().contentType(ContentType.JSON)
                .auth().oauth2(jwt)
                .body(dlcList)
                .pathParam("id", id)
                .put("/user/games/{id}")
                .then());
    }

    public AssertableResponse addGame(String jwt, GamesItem game) {
        return new AssertableResponse(given().contentType(ContentType.JSON)
                .auth().oauth2(jwt)
                .body(game)
                .post("/user/games")
                .then());
    }

    public AssertableResponse updateField(String jwt, int id, UpdField updField) {
        return new AssertableResponse(given().contentType(ContentType.JSON)
                .auth().oauth2(jwt)
                .body(updField)
                .pathParam("id", id)
                .put("/user/games/{id}/updateField")
                .then());
    }
}
