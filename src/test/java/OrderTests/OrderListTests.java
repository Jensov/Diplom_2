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

public class OrderListTests extends BaseTest {
    private final UserGenerator generator = new UserGenerator();
    private final UserAssertions check = new UserAssertions();
    private final IngredientAssertions checkI = new IngredientAssertions();
    private final OrderAssertions checkO = new OrderAssertions();

    @Test
    @DisplayName("Get Order List With Authorization")
    @Description("Проверяется получение списка заказов с предварительной авторизацией")
    public void getOrderListWithAuthorization() {
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

        checkO.createdOrderSuccessfully(response);

        response = OrderClient.getOrdersWithAuthorization(userResponse);
        checkO.createdOrderSuccessfully(response);

    }

    @Test
    @DisplayName("Get Order List Without Authorization")
    @Description("Проверяется получение списка заказов без авторизации")
    public void getOrderListWithoutAuthorization() {
        Response response = OrderClient.getOrdersWithoutAuthorization();
        check.youShouldBeAuthorised(response);
    }
}
