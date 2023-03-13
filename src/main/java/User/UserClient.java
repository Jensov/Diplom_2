package User;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserClient {
    public final static String ROOT = "/api/auth/";

    public static Response createUser(User user) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .and()
                .body(user)
                .when()
                .post(ROOT + "register");
    }

    public static Response deleteUser(UserResponse userResponse) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .header("authorization", userResponse.getToken())
                .when()
                .delete(ROOT + "user");
    }

    public static Response loginUser(User user) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .and()
                .body(user)
                .when()
                .post(ROOT + "login");

    }

    public static Response patchUserDataWithoutToken(User user) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .and()
                .body(user)
                .when()
                .patch(ROOT + "user");
    }

    public static Response patchUserWithToken(UserResponse userResponse, User user) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .header("authorization", userResponse.getToken())
                .body(user)
                .when()
                .patch(ROOT + "user");
    }
}