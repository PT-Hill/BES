import app.api.sql.Database;
import app.api.sql.UserTable;
import server.MySQLCredentials;
import server.Server;
import utils.ContextUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {


        /*try (Connection conn = db.conn) {
            String sql = "INSERT INTO test_table (id, name, age) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, 1);
            stmt.setString(2, "John");
            stmt.setInt(3, 30);
            stmt.executeUpdate();
            System.out.println("Data inserted successfully.");
        } catch (SQLException e) {
            System.out.println("Error inserting data: " + e.getMessage());
        }*/


        //Fetches credentials
        List<String> creds = new ArrayList<String>();
        try {
            Stream<String> credentialsFile = Files.lines(Path.of("creds.txt"), StandardCharsets.UTF_8);
             creds = credentialsFile.toList();
        } catch (IOException e) {
            System.err.println("Unable to fetch credentials");
            throw new RuntimeException(e);
        }
        //Initialize a MySQL Database and Tables for the app if not present

        Database db = new Database(new MySQLCredentials("jdbc:mysql://localhost:3306/betterenrichingstudents", creds.get(0), creds.get(1)));
        UserTable.Initialize(db.conn);

        //Initialize and Run Server
        Server s = new Server(new InetSocketAddress(InetAddress.getLoopbackAddress(), 8000), 200);
        System.out.println("Hello world!");

        //Loads normal uris to be served by the HTML server, after custom uris are handled
        s.loadNormalContexts();
    }
}