package UserTests;

import Base.BaseTest;
import User.*;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.equalTo;

public class EditUserDataTests extends BaseTest {
    private final UserGenerator generator = new UserGenerator();
    private final UserAssertions check = new UserAssertions();

    @Test
    @DisplayName("Patch User Data With Authorization")
    @Description("Проверяется внесение изменений в данные авторизованного пользователя")
    public void patchUserDataWithAuthorizationTest() {
        User user = generator.random();
        Response response = UserClient.createUser(user);
        UserResponse userResponse = response.as(UserResponse.class);
        this.userResponse = userResponse;

        user.setEmail(UserGenerator.generateEmail());
        UserClient.patchUserWithToken(userResponse, user)
                .then()
                .statusCode(SC_OK)
                .body("success", equalTo(true))
                .body("user.email", equalTo(user.getEmail()));
    }

    @Test
    @DisplayName("Patch User Data Not Authorization")
    @Description("Проверяется внесение изменений в данные неавторизованного пользователя")
    public void patchUserDataNotAuthorizationTest() {
        User user = generator.random();
        userResponse = UserClient.createUser(user).as(UserResponse.class);

        Response response = UserClient.patchUserDataWithoutToken(user);
        check.youShouldBeAuthorised(response);
    }
}
