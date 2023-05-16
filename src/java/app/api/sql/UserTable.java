package app.api.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserTable {

    public static String userTableInit = "CREATE TABLE IF NOT EXISTS users (`user_id` INT(15) NOT NULL,`first_name` VARCHAR(25) NOT NULL,`last_name` VARCHAR(50) NOT NULL,`email` VARCHAR(320) NOT NULL,`password` VARCHAR(50) NOT NULL,`time_registered` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,`time_since_login` TIMESTAMP NULL,PRIMARY KEY (`user_id`))";

    public static void Initialize(Connection conn) {
        try(PreparedStatement statement = conn.prepareStatement(userTableInit)) {
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
