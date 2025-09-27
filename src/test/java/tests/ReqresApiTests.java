package tests;

import models.UserLoginRequest;
import models.UserTokenResponse;
import models.UsersListResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.RequestSpec.request;
import static specs.ResponseSpec.responseSpec;

public class ReqresApiTests extends BaseTest {
    @Test
    @Tag("api")
    @DisplayName("Успешное получение списка пользователей")
    void checkTotalTest() {
        UsersListResponse response = step("Отправить запрос на получение списка пользователей", () ->
        given()
                .spec(request)
                .when()
                .queryParam("page", "2")
                .get("/users")
                .then()
                .spec(responseSpec(200))
                .extract().as(UsersListResponse.class));

        step("Проверить параметры", () -> {
            assertThat(response.getPage()).isEqualTo(2);
            assertThat(response.getPerPage()).isEqualTo(6);
            assertThat(response.getTotal()).isEqualTo(12);
            assertThat(response.getTotalPages()).isEqualTo(2);
        });
    }

    @Test
    @Tag("api")
    @DisplayName("При авторизации ответ с токеном не пустой")
    void getUserTokenTest() {
        UserLoginRequest body = new UserLoginRequest();
        body.setEmail("eve.holt@reqres.in");
        body.setPassword("123456");

        UserTokenResponse response = step("Отправить запрос на авторизацию", () ->
        given()
                .spec(request)
                .body(body)
                .when()
                .post("/login")
                .then()
                .spec(responseSpec(200))
                .extract().as(UserTokenResponse.class));

        step("Проверить параметры", () -> {
            assertThat(response.getToken()).isNotNull();
        });
    }

    @Test
    @Tag("api")
    @DisplayName("Получение ошибки 404 при поиске несуществующего пользователя")
    void getNonExistentUserTest() {
        step("Отправить запрос на получение данных несуществующего пользователя", () ->
                    given()
                    .spec(request)
                    .when()
                    .get("/users/200")
                    .then()
                    .spec(responseSpec(404)));
    }

    @Test
    @Tag("api")
    @DisplayName("Получение ошибки 400 при запросе токена несуществующим пользователем")
    void getClientErrorTest() {
        UserLoginRequest body = new UserLoginRequest();
        body.setEmail("tracey.ramo@reqres.in");
        body.setPassword("123456");

        step("Отправить запрос на логин несуществующим пользователем", () ->
        given()
                .spec(request)
                .body(body)
                .when()
                .post("/login")
                .then()
                .spec(responseSpec(400)));
    }
}
