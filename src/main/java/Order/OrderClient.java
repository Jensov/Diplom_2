package Order;

import User.UserResponse;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderClient {
    private final static String ROOT = "/api/orders";
    public static Response createOrderWithAuthorization(UserResponse userResponse, Order order) {
        if (null != order) {
            return given().log().all()
                    .contentType(ContentType.JSON)
                    .header("authorization", userResponse.getToken())
                    .and()
                    .body(order)
                    .when()
                    .post(ROOT);
        }
        return given().log().all()
                .contentType(ContentType.JSON)
                .header("authorization", userResponse.getToken())
                .when()
                .post(ROOT);
    }

    public static Response createOrderWithoutAuthorization(Order order) {
        if (null != order) {
            return given().log().all()
                    .contentType(ContentType.JSON)
                    .and()
                    .body(order)
                    .when()
                    .post(ROOT);
        }
        return given().log().all()
                .contentType(ContentType.JSON)
                .when()
                .post(ROOT);
    }

    public static Response getOrdersWithAuthorization(UserResponse userResponse) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .header("authorization", userResponse.getToken())
                .when()
                .get(ROOT);
    }

    public static Response getOrdersWithoutAuthorization() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .when()
                .get(ROOT);
    }
}
