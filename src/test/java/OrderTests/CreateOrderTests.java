package OrderTests;

import Base.BaseTest;
import Ingredient.*;
import Order.*;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import User.*;

import java.util.ArrayList;
import java.util.List;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.core.IsEqual.equalTo;

public class CreateOrderTests extends BaseTest {
    private final UserGenerator generator = new UserGenerator();
    private final OrderAssertions check = new OrderAssertions();
    private final IngredientAssertions checkI = new IngredientAssertions();

    @Test
    @DisplayName("Create New Order With Authorization And With Ingredients")
    @Description("Проверяется создание заказа авторизованным пользователем с ингредиентами")
    public void createNewOrderWithAuthorizationAndWithIngredients() {
        User user = generator.random();
        Response response = UserClient.createUser(user);
        UserResponse userResponse = response.as(UserResponse.class);
        this.userResponse = userResponse;

        List<String> ingredients = new ArrayList<>();
        response = IngredientClient.getIngredientsList();
        checkI.ingredientListSuccessfully(response);

        Ingredients dtoIngredients = response.as(Ingredients.class);
        Ingredient ingredient = dtoIngredients.getIngredientWithName("Краторная булка N-200i");
        ingredients.add(ingredient.get_id());
        ingredients.add(ingredient.get_id());

        Order order = new Order();
        order.setIngredients(ingredients);

        OrderClient.createOrderWithAuthorization(userResponse, order);

        check.createdOrderSuccessfully(response);
    }

    @Test
    @DisplayName("Create New Order With Authorization And Without Ingredients")
    @Description("Проверяется создание заказа авторизованным пользователем без ингредиентов")
    public void createNewOrderWithAuthorizationAndWithoutIngredients() {
        User user = generator.random();
        Response response = UserClient.createUser(user);
        UserResponse userResponse = response.as(UserResponse.class);
        this.userResponse = userResponse;

        OrderClient.createOrderWithAuthorization(userResponse, null)
                .then().assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("success", equalTo(false))
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Create New Order Without Authorization And With Ingredients")
    @Description("Проверяется создание заказа без авторизации с ингредиентами")
    public void createNewOrderWithoutAuthorizationAndWithIngredients() {
        List<String> ingredients = new ArrayList<>();
        Response response = IngredientClient.getIngredientsList();
        checkI.ingredientListSuccessfully(response);

        Ingredients dtoIngredients = response.as(Ingredients.class);
        Ingredient ingredient = dtoIngredients.getIngredientWithName("Краторная булка N-200i");
        ingredients.add(ingredient.get_id());
        ingredients.add(ingredient.get_id());

        Order order = new Order();
        order.setIngredients(ingredients);

        OrderClient.createOrderWithoutAuthorization(order);

        check.createdOrderSuccessfully(response);
    }

    @Test
    @DisplayName("Create New Order Without Authorization And Without Ingredients")
    @Description("Проверяется создание заказа без авторизации и без ингредиентов")
    public void createNewOrderWithoutAuthorizationAndWithoutIngredients() {
        OrderClient.createOrderWithoutAuthorization(null)
                .then().assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("success", equalTo(false))
                .body("message", equalTo("Ingredient ids must be provided"));
    }


    @Test
    @DisplayName("Create New Order With Authorization And With Invalid Ingredients")
    @Description("Проверяется создание заказа авторизованным пользователем и с неверным хешем ингредиентов")
    public void createNewOrderWithAuthorizationAndWithInvalidIngredients() {
        User user = generator.random();
        Response response = UserClient.createUser(user);
        UserResponse userResponse = response.as(UserResponse.class);
        this.userResponse = userResponse;
        List<String> ingredients = new ArrayList<>();
        ingredients.add("ketchup");

        Order order = new Order();
        order.setIngredients(ingredients);

        OrderClient.createOrderWithAuthorization(userResponse, order)
                .then().assertThat()
                .statusCode(SC_INTERNAL_SERVER_ERROR);
    }
}
