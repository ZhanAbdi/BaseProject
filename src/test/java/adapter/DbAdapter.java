package adapter;
import models.Playlist;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbAdapter {

        private static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
        private static final String DB_URL = "jdbc:mariadb://104.237.13.60/databaseName";
        private static final String USER = "dbuser01";
        private static final String PASS = "pa$$01";
        private static Connection conn = null;
        private static Statement stmt = null;

        public static List<Playlist> getUsersPlaylists(int userId){
            List<Playlist> playlists = new ArrayList<>();
            String query = "select * from playlists p where user_id ="+userId;

            try {
                Class.forName("org.mariadb.jdbc.Driver");
                conn = DriverManager.getConnection(DB_URL,USER,PASS);
                stmt = conn.createStatement();

                ResultSet resultSet = stmt.executeQuery(query);
                while (resultSet.next()){
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    Playlist pl = new Playlist(id,name);
                    playlists.add(pl);
                }

            } catch (SQLException | ClassNotFoundException se) {
                se.printStackTrace();
            } finally {
                try {
                    if (stmt != null) {
                        conn.close();
                    }
                } catch (SQLException se) {
                }// do nothing
                try {
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException se) {
                    se.printStackTrace();
                }//end finally try
            }//end try
            return playlists;
        }

        public static String getPlaylistNameById(int playlistId){
            String query = "select name from playlists where id="+playlistId;
            String name = null;
            try {
                Class.forName("org.mariadb.jdbc.Driver");
                conn = DriverManager.getConnection(DB_URL,USER,PASS);
                stmt = conn.createStatement();

                ResultSet resultSet = stmt.executeQuery(query);
                while (resultSet.next()){
                    name = resultSet.getString("name");
                }

            } catch (SQLException | ClassNotFoundException se) {
                se.printStackTrace();
            } finally {
                try {
                    if (stmt != null) {
                        conn.close();
                    }
                } catch (SQLException se) {
                }// do nothing
                try {
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException se) {
                    se.printStackTrace();
                }//end finally try
            }//end try
            return name;
        }
}
