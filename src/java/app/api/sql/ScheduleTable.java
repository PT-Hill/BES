package app.api.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ScheduleTable {

    public static String scheduleTableInit = "CREATE TABLE IF NOT EXISTS schedules (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255) NOT NULL, start_date DATE NOT NULL, end_date DATE NOT NULL, start_time TIME NOT NULL, end_time TIME NOT NULL, eventholder_name VARCHAR(50) NULL, description VARCHAR(300) NULL)";
    public static void Initialize(Connection conn) {
        try(PreparedStatement statement = conn.prepareStatement(scheduleTableInit)) {
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
