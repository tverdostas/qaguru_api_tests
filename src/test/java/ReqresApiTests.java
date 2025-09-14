import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;

public class ReqresApiTests extends BaseTest {
    @Test
    void checkTotal() {
        given()
                .when()
                .get("https://reqres.in/api/users")
                .then()
                .body("total", is(12));
    }

    @Test
    void checkPages() {
        given()
                .header("x-api-key", "reqres-free-v1")
                .when()
                .get("https://reqres.in/api/users")
                .then()
                .body("total_pages", is(2));
    }

    @Test
    void getUserToken() {
        given()
                .header("x-api-key", "reqres-free-v1")
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .statusCode(200)
                .body("token", notNullValue());
    }

    @Test
    void getNonExistentUser() {
        given()
                .header("x-api-key", "reqres-free-v1")
                .when()
                .get("https://reqres.in/api/users/200")
                .then()
                .statusCode(404);
    }

    @Test
    void getClientError() {
        String jsonBodyString = "{\"email\": \"tracey.ramo@reqres.in\", \"password\": \"123456\"}";

        given()
                .header("x-api-key", "reqres-free-v1")
                .body(jsonBodyString)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .statusCode(400);
    }
}
