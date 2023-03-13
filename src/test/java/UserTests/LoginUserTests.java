package UserTests;

import Base.BaseTest;
import User.*;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

public class LoginUserTests extends BaseTest {
    private final UserGenerator generator = new UserGenerator();
    private final UserAssertions check = new UserAssertions();

    @Test
    @DisplayName("User May Be Logged In")
    @Description("Проверяется авторизация с валидными данными")
    public void userMayBeLoggedIn() {
        User user = generator.random();
        Response response = UserClient.createUser(user);
        check.createdSuccessfully(response);
        userResponse = response.as(UserResponse.class);

        response = UserClient.loginUser(user);
        check.loggedInSuccessfully(response);
    }

    @Test
    @DisplayName("Sign In With Incorrect Email")
    @Description("Проверяется авторизация с некорректной почтой")
    public void signInWithIncorrectEmail() {
        User user = generator.random();
        Response response = UserClient.createUser(user);
        check.createdSuccessfully(response);
        userResponse = response.as(UserResponse.class);

        user.setEmail(UserGenerator.generateEmail());
        response = UserClient.loginUser(user);
        check.emailOrPasswordAreIncorrect(response);
    }

    @Test
    @DisplayName("Sign In With Incorrect Password")
    @Description("Проверяется авторизация с некорректным паролем")
    public void signInWithIncorrectPassword() {
        User user = generator.random();
        Response response = UserClient.createUser(user);
        check.createdSuccessfully(response);
        userResponse = response.as(UserResponse.class);

        user.setPassword(UserGenerator.generatePassword());
        response = UserClient.loginUser(user);
        check.emailOrPasswordAreIncorrect(response);
    }
}
