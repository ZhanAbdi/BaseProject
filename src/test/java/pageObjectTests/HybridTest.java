package pageObjectTests;

import adapter.DbAdapter;
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
import pageObjects.LoginPage;
import pageObjects.MainPage;

import static io.restassured.RestAssured.given;

public class HybridTest extends BaseTest{
    private String playlistName;
    private int playlistId;

    @BeforeMethod
    public void createPlaylist(){
        playlistName = faker.zelda().character();

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
    public void hybrid_renamePlaylist(){
        MainPage mainPage = login();

        String newName = faker.book().title();
        mainPage.renamePlaylist(playlistId+"",newName);

        String nameFromDb = DbAdapter.getPlaylistNameById(playlistId);
        Assert.assertEquals(nameFromDb,newName);
    }
}
