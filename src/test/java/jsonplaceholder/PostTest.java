package jsonplaceholder;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class PostTest {

    @Test
    public void createPost() {
        JSONObject post = new JSONObject();
        post.put("userId", 1);
        post.put("title", "this is title");
        post.put("body", "this is body");

        given()
                .body(post.toString())
                .when()
                .post("https://jsonplaceholder.typicode.com/posts")
                .then()
                .statusCode(201);
    }

    @Test
    public void createPost1() {
        // Serializacja
        // POJO - Plain Old Java Object - najzwyklejszy object w Javie
        Post post = new Post();
        post.setUserId(1);
        post.setTitle("This is title");
        post.setBody("This is body");

        // POJO -> JSON
        Response response = given()
                .contentType(ContentType.JSON)
                .body(post)
                .when()
                .post("https://jsonplaceholder.typicode.com/posts")
                .then()
                .statusCode(201)
                .extract()
                .response();
        JsonPath json = response.jsonPath();
        Assertions.assertThat(json.getString("title")).isEqualTo(post.getTitle());
    }

    @Test
    public void createPost2() {
        // Deserializacja
        // JSON -> POJO

        Post post = given()
                .contentType(ContentType.JSON)
                .when()
                .get("https://jsonplaceholder.typicode.com/posts/1")
                .as(Post.class);
        Assertions.assertThat(post.getTitle()).isEqualTo("sunt aut facere repellat provident occaecati excepturi optio reprehenderit");

    }
}
