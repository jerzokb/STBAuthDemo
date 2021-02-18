package github;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class AuthTest {
    private static final String TOKEN = "GITHUB TOKEN";

    @Test
    public void basicAuth() {
        given()
                .auth()
                .preemptive()
                .basic("username", "pasword")
                .get("https://api.github.com/user")
                .then()
                .statusCode(200);
    }

    @Test
    public void bearerToken() {
        given()
                .headers("Authorization", "Bearer " + TOKEN)
                .get("https://api.github.com/user")
                .then()
                .statusCode(200);
    }

    @Test
    public void oAuth2() {
        given()
                .auth()
                .oauth2(TOKEN)
                .get("https://api.github.com/user")
                .then()
                .statusCode(200);
    }

}
