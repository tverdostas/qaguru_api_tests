import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;

public class ReqresApiTests extends BaseTest {
    @Test
    void checkTotal() {
        given()
                .when()
                .get("/users")
                .then()
                .body("total", is(12));
    }

    @Test
    void checkPages() {
        given()
                .header("x-api-key", apiKey)
                .when()
                .get("/users")
                .then()
                .body("total_pages", is(2));
    }

    @Test
    void getUserToken() {
        given()
                .header("x-api-key", apiKey)
                .when()
                .post("/login")
                .then()
                .statusCode(200)
                .body("token", notNullValue());
    }

    @Test
    void getNonExistentUser() {
        given()
                .header("x-api-key", apiKey)
                .when()
                .get("/users/200")
                .then()
                .statusCode(404);
    }

    @Test
    void getClientError() {
        String jsonBodyString = "{\"email\": \"tracey.ramo@reqres.in\", \"password\": \"123456\"}";

        given()
                .header("x-api-key", apiKey)
                .body(jsonBodyString)
                .when()
                .post("/login")
                .then()
                .statusCode(400);
    }
}
