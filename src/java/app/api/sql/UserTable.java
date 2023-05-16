package app.api.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class UserTable {

    public static Connection conn;
    public static String userTableInit = "CREATE TABLE IF NOT EXISTS users (`user_id` INT(15) NOT NULL,`first_name` VARCHAR(25) NOT NULL,`last_name` VARCHAR(50) NOT NULL,`email` VARCHAR(320) NOT NULL,`password` VARCHAR(50) NOT NULL,`time_registered` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,`time_since_login` TIMESTAMP NULL,PRIMARY KEY (`user_id`))";

    public static void Initialize(Connection initConn) {
        conn = initConn;
        try(PreparedStatement statement = conn.prepareStatement(userTableInit)) {
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void Insert(int userID, String firstName, String lastName, String email, String password) throws SQLException {
        String sql = "INSERT INTO users (user_id, first_name, last_name, email, password) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        statement.setInt(1, userID);
        statement.setString(2, firstName);
        statement.setString(3, lastName);
        statement.setString(4, email);
        statement.setString(5, password);
        int rowsInserted = statement.executeUpdate();
    }
}
