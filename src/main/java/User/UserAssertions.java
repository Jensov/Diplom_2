package User;

import io.restassured.response.Response;
import org.hamcrest.CoreMatchers;
import org.hamcrest.core.IsEqual;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.core.IsEqual.equalTo;

public class UserAssertions {

    public void createdSuccessfully(Response response) {
        response.then().assertThat()
                .statusCode(SC_OK)
                .body("success", IsEqual.equalTo(true));
    }

    public void userAlreadyExists(Response response) {
        response.then().assertThat()
                .statusCode(SC_FORBIDDEN)
                .and()
                .body("success", IsEqual.equalTo(false))
                .body(" message", IsEqual.equalTo("User already exists"));
    }

    public void emailPasswordAndNameAreRequiredFields(Response response) {
        response.then().assertThat()
                .statusCode(SC_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }

    public void youShouldBeAuthorised(Response response) {
        response.then().assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }

    public void loggedInSuccessfully(Response response) {
        response.then().assertThat()
                .statusCode(SC_OK)
                .body("success", equalTo(true));
    }

    public void emailOrPasswordAreIncorrect(Response response) {
        response.then().assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
    }
}
