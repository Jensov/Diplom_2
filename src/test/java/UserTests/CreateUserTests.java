package UserTests;

import Base.BaseTest;
import User.*;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;


public class CreateUserTests extends BaseTest {
    private final UserGenerator generator = new UserGenerator();
    private final UserAssertions check = new UserAssertions();

    @Test
    @DisplayName("Create New User Test")
    @Description("Проверяется создание нового пользователя")
    public void createNewUserTest() {
        User user = generator.random();
        Response response = UserClient.createUser(user);
        check.createdSuccessfully(response);
        userResponse = response.as(UserResponse.class);
    }

    @Test
    @DisplayName("Create User Who is Already Registered")
    @Description("Проверяется повторное создание пользователя")
    public void createUserWhoIsAlreadyRegistered() {
        User user = generator.random();
        Response response = UserClient.createUser(user);
        check.createdSuccessfully(response);
        userResponse = response.as(UserResponse.class);

        response = UserClient.createUser(user);
        check.userAlreadyExists(response);
    }

    @Test
    @DisplayName("Create User Without Email")
    @Description("Проверяется создание пользователя без почты")
    public void createUserWithoutEmail() {
        User user = generator.random();
        user.setEmail(null);
        Response response = UserClient.createUser(user);
        check.emailPasswordAndNameAreRequiredFields(response);
    }

    @Test
    @DisplayName("Create User Without Password")
    @Description("Проверяется создание пользователя без пароля")
    public void createUserWithoutPassword() {
        User user = generator.random();
        user.setPassword(null);
        Response response = UserClient.createUser(user);
        check.emailPasswordAndNameAreRequiredFields(response);
    }

    @Test
    @DisplayName("Create User Without Name")
    @Description("Проверяется создание пользователя без имени")
    public void createUserWithoutName() {
        User user = generator.random();
        user.setName(null);
        Response response = UserClient.createUser(user);
        check.emailPasswordAndNameAreRequiredFields(response);
    }
}
