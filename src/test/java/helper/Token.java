package helper;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.Credentials;

import static io.restassured.RestAssured.given;

public class Token {
    public static String get(String username, String password){
        Credentials creds = new Credentials(username,password);
        Response response = given()
                .baseUri("url")
                .basePath("api/me")
                .header("accept","application/json")
                .header("Content-Type","application/json")
                .body(creds)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .response();
        JsonPath json = response.jsonPath();
        return json.getString("token");
    }
}
