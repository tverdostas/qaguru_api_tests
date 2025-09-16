import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;

public class ReqresApiTests extends BaseTest {
    @Test
    void checkTotalTest() {
        given()
                .log().uri()
                .when()
                .get("/users")
                .then()
                .log().status()
                .log().body()
                .body("total", is(12));
    }

    @Test
    void checkPagesTest() {
        given()
                .header("x-api-key", apiKey)
                .log().uri()
                .when()
                .get("/users")
                .then()
                .log().status()
                .log().body()
                .body("total_pages", is(2));
    }

    @Test
    void getUserTokenTest() {
        given()
                .header("x-api-key", apiKey)
                .when()
                .log().uri()
                .post("/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", notNullValue());
    }

    @Test
    void getNonExistentUserTest() {
        given()
                .header("x-api-key", apiKey)
                .when()
                .log().uri()
                .get("/users/200")
                .then()
                .log().status()
                .log().body()
                .statusCode(404);
    }

    @Test
    void getClientErrorTest() {
        String jsonBodyString = "{\"email\": \"tracey.ramo@reqres.in\", \"password\": \"123456\"}";

        given()
                .header("x-api-key", apiKey)
                .body(jsonBodyString)
                .when()
                .log().uri()
                .post("/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(400);
    }
}
