package trello;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;

public class OrganizationTest {

    // trello api key:
    protected static final String KEY = "YOUR KEY";
    // trello token:
    protected static final String TOKEN = "YOUR TOKEN";

    @DisplayName("Create organization with invalid data")
    @ParameterizedTest(name = "Display name: {0}, desc: {1}, name: {2}, website: {3}")
    @MethodSource("createOrganizationInvalidData")
    public void createOrganizationInvalidData(String displayName, String desc, String name, String website) {

        Response response = given()
                .contentType(ContentType.JSON)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .queryParam("displayName", displayName)
                .queryParam("desc", desc)
                .queryParam("name", name)
                .queryParam("website", website)
                .when()
                .post("https://api.trello.com/1/organizations")
                .then()
                .statusCode(400)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        Assertions.assertThat(json.getString("displayName")).isEqualTo(displayName);

        final String orgamizationId = json.getString("id");

        given()
                .contentType(ContentType.JSON)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .when()
                .delete("https://api.trello.com/1/organizations" + "/" + orgamizationId)
                .then()
                .statusCode(200);
    }

    @DisplayName("Create organization with valid data")
    @ParameterizedTest(name = "Display name: {0}, desc: {1}, name: {2}, website: {3}")
    @MethodSource("createOrganizationData")
    public void createOrganizationParametrized(String displayName, String desc, String name, String website) {
        Organization organization = new Organization();
        organization.setDisplayName(displayName);
        organization.setDesc(desc);
        organization.setName(name);
        organization.setWebsite(website);

        Response response = given()
                .contentType(ContentType.JSON)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .queryParam("displayName", organization.getDisplayName())
                .queryParam("desc", organization.getDesc())
                .queryParam("name", organization.getName())
                .queryParam("website", organization.getWebsite())
                .when()
                .post("https://api.trello.com/1/organizations")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        Assertions.assertThat(json.getString("displayName")).isEqualTo(organization.getDisplayName());

        final String orgamizationId = json.getString("id");

        given()
                .contentType(ContentType.JSON)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .when()
                .delete("https://api.trello.com/1/organizations" + "/" + orgamizationId)
                .then()
                .statusCode(200);
    }

    @Test
    public void createOrganizationWithHttps() {
        Organization organization = new Organization();
        organization.setDisplayName("This is display name");
        organization.setDesc("This is description");
        organization.setName("this is name");
        organization.setWebsite("https://thisiswebsite.com");

        Response response = given()
                .contentType(ContentType.JSON)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .queryParam("displayName", organization.getDisplayName())
                .queryParam("desc", organization.getDesc())
                .queryParam("name", organization.getName())
                .queryParam("website", organization.getWebsite())
                .when()
                .post("https://api.trello.com/1/organizations")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        Assertions.assertThat(json.getString("displayName")).isEqualTo(organization.getDisplayName());

        final String orgamizationId = json.getString("id");

        given()
                .contentType(ContentType.JSON)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .when()
                .delete("https://api.trello.com/1/organizations" + "/" + orgamizationId)
                .then()
                .statusCode(200);
    }

    @Test
    public void createOrganizationWithHttp() {
        Organization organization = new Organization();
        organization.setDisplayName("This is display name");
        organization.setDesc("This is description");
        organization.setName("this is name");
        organization.setWebsite("http://thisiswebsite.com");

        Response response = given()
                .contentType(ContentType.JSON)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .queryParam("displayName", organization.getDisplayName())
                .queryParam("desc", organization.getDesc())
                .queryParam("name", organization.getName())
                .queryParam("website", organization.getWebsite())
                .when()
                .post("https://api.trello.com/1/organizations")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        Assertions.assertThat(json.getString("displayName")).isEqualTo(organization.getDisplayName());

        final String orgamizationId = json.getString("id");

        given()
                .contentType(ContentType.JSON)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .when()
                .delete("https://api.trello.com/1/organizations" + "/" + orgamizationId)
                .then()
                .statusCode(200);
    }

    private static Stream<Arguments> createOrganizationData() {
        return Stream.of(
                Arguments.of("This is display name", "This is description", "this is name", "https://thisiswebsite.com"),
                Arguments.of("This is display name", "This is description", "this is name", "http://thisiswebsite.com"),
                Arguments.of("This is display name", "This is description", "aqa", "http://thisiswebsite.com"),
                Arguments.of("This is display name", "This is description", "this_is_name", "http://thisiswebsite.com"),
                Arguments.of("This is display name", "This is description", "this1 is2 name3", "http://thisiswebsite.com"),
                Arguments.of("This is display name", "This is description", "this1 is2 name3", "thisiswebsite.com"));
    }

    private static Stream<Arguments> createOrganizationInvalidData() {
        return Stream.of(
                Arguments.of("This is display name", "This is description", "xz", "https://thisiswebsite.com"),
                Arguments.of("This is display name", "This is description", "THIS is NAME", "http://thisiswebsite.com"),
                Arguments.of("This is display name", "This is description", "is this name?!", "http://thisiswebsite.com"),
                Arguments.of("This is display name", "This is description", "@#$%^&*", "http://thisiswebsite.com"));
    }
}
