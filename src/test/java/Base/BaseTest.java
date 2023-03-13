package Base;

import User.UserClient;
import User.UserResponse;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;

import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.hamcrest.core.IsEqual.equalTo;

public class BaseTest {

    public UserResponse userResponse;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }

    @After
    public void afterTest() {
        if (null != userResponse) {
            UserClient.deleteUser(userResponse)
                    .then().statusCode(SC_ACCEPTED)
                    .body("success", equalTo(true))
                    .body("message", equalTo("User successfully removed"));
        }
    }
}
