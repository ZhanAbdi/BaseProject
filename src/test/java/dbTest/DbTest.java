package dbTest;

import adapter.DbAdapter;
import models.Playlist;
import org.testng.annotations.Test;

import java.util.List;

public class DbTest {
    @Test
    public void getUserPlaylists(){
        List<Playlist> playlists = DbAdapter.getUsersPlaylists(35);

        for (Playlist pl : playlists){
            System.out.println(pl.getName());
        }
    }
}
