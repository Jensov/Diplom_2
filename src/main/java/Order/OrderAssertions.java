package Order;

import io.restassured.response.Response;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.core.IsEqual.equalTo;

public class OrderAssertions {
    public void createdOrderSuccessfully(Response response) {
        response.then().assertThat()
                .statusCode(SC_OK)
                .body("success", equalTo(true));
    }
}
