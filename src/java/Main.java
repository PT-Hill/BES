import app.api.API;
import app.api.loginUser;
import app.api.registerUser;
import app.api.sql.AssignmentTable;
import app.api.sql.Database;
import app.api.sql.ScheduleTable;
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


        /*
        Note: We did not have time to implement HTTPS security in the server, so there are a few known security flaws.
        Mainly, login credentials can be intercepted by others on the network. The login is hashed client side so actual passwords cannot be compromised,
        but one could just repeat the login hash and gain account access. Effectively, just prevents your password from being compromised on other sites.
        Fortunately, this is not an issue as this is just a proof of concept build.
        */
        //Fetches credentials for a MySQL Server
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
        ScheduleTable.Initialize(db.conn);
        AssignmentTable.Initialize(db.conn);

        //Initialize and Run Server
        Server s = new Server(new InetSocketAddress(InetAddress.getLoopbackAddress(), 8000), 200);
        //Loads normal uris to be served by the HTML server, after custom uris are handled
        s.loadNormalContexts();
        s.loadApiContexts(new API[] {
            new loginUser("loginUser", "loginUser"),
            new registerUser("registerUser", "registerUser")
        });

        /*try {
            //UserTable.Insert(00220204, "John", "Doe", "johndoe@email.com", "johnnyboy");
            //UserTable.Insert(0044520204, "John", "Doe", "johndoe@email.com", "johnnyboy");
            //UserTable.Insert(10020204, "John", "Doe", "johndoe@email.com", "johnnyboy");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/
    }
}