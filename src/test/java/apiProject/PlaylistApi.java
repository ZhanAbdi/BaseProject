package apiProject;

import com.github.javafaker.Faker;
import helper.Token;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.PlaylistRequest;
import models.PlaylistResponse;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class PlaylistApi {
    private String token;
    private Faker faker;
    private String playlistName;
    private int playlistId;

    @BeforeClass
    public void runOnce(){
        token = Token.get("email@email.com","password");
        faker = new Faker();
    }
    @BeforeMethod
    public void createPlaylist(){
        playlistName = faker.currency().name();
        System.out.println(playlistName);
        PlaylistRequest body = new PlaylistRequest(playlistName);
        Response response = given()
                .baseUri("url")
                .basePath("api/playlist")
                .header("accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization","Bearer "+token)
                .body(body)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .response();
        JsonPath json = response.jsonPath();
        PlaylistResponse playlistResponse = json.getObject("$",PlaylistResponse.class);
        playlistId = playlistResponse.getId();
        System.out.println(playlistId);
    }
    @AfterMethod
    public void deletePlayList(){
        given()
                .baseUri("url"+playlistId)
                .header("Authorization","Bearer "+token)
                .when()
                .delete();
    }
    @Test
    public void loginToApp(){
        String newName = faker.rickAndMorty().character();
        PlaylistRequest body = new PlaylistRequest(newName);
        Response response = given()
                .baseUri("url")
                .basePath("api/playlist/"+playlistId)
                .header("accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization","Bearer "+token)
                .body(body)
                .when()
                .put()
                .then()
                .statusCode(200)
                .extract()
                .response();
        JsonPath json = response.jsonPath();
        PlaylistResponse playlistResponse = json.getObject("$",PlaylistResponse.class);
        Assert.assertEquals(playlistResponse.getName(),newName);
    }
    @Test
    public void getPlaylists(){
        Response response = given()
                .baseUri("url")
                .basePath("api/playlist/")
                .header("accept","application/json")
                .header("Authorization","Bearer "+token)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .response();
        JsonPath json = response.jsonPath();
        PlaylistResponse[] playlists = json.getObject("$",PlaylistResponse[].class);

        boolean exist = false;
        for (PlaylistResponse pl :playlists){
            if(pl.getId()==playlistId){
                exist = true;
                Assert.assertEquals(pl.getName(),playlistName);
            }
        }
        Assert.assertTrue(exist);

        for (PlaylistResponse pl :playlists){
            given()
                    .baseUri("url"+pl.getId())
                    .header("Authorization","Bearer "+token)
                    .when()
                    .delete();
        }


    }

}
