package jsonplaceholder;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class UserTest {

    @Test
    public void createNewUser() {
        User user = new User();
        user.setName("Beata Testowa");
        user.setUsername("Tester");
        user.setEmail("beata@testowa.pl");
        user.setPhone("123-456-789");
        user.setWebsite("www.testowo.pl");

        Geo geo = new Geo();
        geo.setLat("-37.3159");
        geo.setLng("81.1496");

        Address address = new Address();
        address.setStreet("Sazamkowa");
        address.setSuite("159");
        address.setCity("Sezamkowo");
        address.setZipcode("12-345");
        address.setGeo(geo);

        user.setAddress(address);

        Company company = new Company();
        company.setName("Firma Sezamkowa");
        company.setCatchPhrase("Najlepsza firma w miescie");
        company.setBs("the best");

        user.setCompany(company);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(user)
                .post("https://jsonplaceholder.typicode.com/users")
                .then()
                .statusCode(201)
                .extract()
                .response();
        JsonPath json = response.jsonPath();
        Assertions.assertThat(json.getString("name")).isEqualTo(user.getName());
    }
}
