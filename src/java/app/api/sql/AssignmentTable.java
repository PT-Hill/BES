package app.api.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AssignmentTable {
    public static String assignTableInit = "CREATE TABLE IF NOT EXISTS assignments (user_id INT NOT NULL, schedule_id INT NOT NULL, PRIMARY KEY (user_id, schedule_id), FOREIGN KEY (user_id) REFERENCES users(user_id), FOREIGN KEY (schedule_id) REFERENCES schedules(id))";
    public static void Initialize(Connection conn) {
        try(PreparedStatement statement = conn.prepareStatement(assignTableInit)) {
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
