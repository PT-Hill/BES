package app.api.sql;

import java.sql.Connection;
import server.MySQLCredentials;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private MySQLCredentials creds;
    public Connection conn = null;

    public Database() {
        try {
            conn = DriverManager.getConnection(creds.getURL(), creds.getUSER(), creds.getPASS());
            System.out.println("Connected to the database");
        } catch (SQLException e) {
            System.out.println("Error connecting to the database");
            e.printStackTrace();
        } /**finally {
            try {
                if (conn != null) {
                    conn.close();
                    System.out.println("Disconnected from database");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }*/
    }

    public Database(MySQLCredentials creds) {
        this.creds = creds;
        try {
            conn = DriverManager.getConnection(creds.getURL(), creds.getUSER(), creds.getPASS());
            System.out.println("Connected to the database");
        } catch (SQLException e) {
            System.out.println("Error connecting to the database");
            e.printStackTrace();
        } /**finally {
         try {
         if (conn != null) {
         conn.close();
         System.out.println("Disconnected from database");
         }
         } catch (SQLException e) {
         e.printStackTrace();
         }
         }*/
    }

    public void fetchDatabase() {

    }

}
