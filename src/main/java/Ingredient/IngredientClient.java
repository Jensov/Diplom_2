package Ingredient;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class IngredientClient {
    public static Response getIngredientsList() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .and()
                .when()
                .get("/api/ingredients");
    }
}
